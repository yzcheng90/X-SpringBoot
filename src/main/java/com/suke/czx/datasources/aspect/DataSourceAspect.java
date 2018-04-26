package com.suke.czx.datasources.aspect;

import com.suke.czx.datasources.DataSourceNames;
import com.suke.czx.datasources.DynamicDataSource;
import com.suke.czx.datasources.annotation.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源，切面处理类
 * @author czx
 * @email object_czx@163.com
 * @date 2017/9/16 22:20
 */
@Aspect
@Component
public class DataSourceAspect implements Ordered {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.suke.czx.datasources.annotation.DataSource)")
    public void dataSourcePointCut() {

    }

/*    @Before("dataSourcePointCut()")
    public void around(JoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        DataSource ds = method.getAnnotation(DataSource.class);
        if(ds == null){
            DynamicDataSource.setDataSource(DataSourceNames.FIRST);
            logger.debug("set datasource is " + DataSourceNames.FIRST);
        }else {
            DynamicDataSource.setDataSource(ds.name());
            logger.debug("set datasource is " + ds.name());
        }
    }*/


    /**
     * 拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
     *
     * @param point
     * @throws Exception
     */
    @Before("dataSourcePointCut()")
    public void intercept(JoinPoint point) throws Exception {
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());
    }

    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     *
     * @param clazz
     * @param method
     */
    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                DataSource source = clazz.getAnnotation(DataSource.class);
                DynamicDataSource.setDataSource(source.name());
                logger.debug("set datasource is " + source.name());
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null) {
                if (m.isAnnotationPresent(DataSource.class)) {
                    DataSource source = m.getAnnotation(DataSource.class);
                    DynamicDataSource.setDataSource(source.name());
                    logger.debug("set datasource is " + source.name());
                }
            } else {
                DynamicDataSource.setDataSource(DataSourceNames.FIRST);
                logger.debug("set datasource is " + DataSourceNames.FIRST);

            }
        } catch (Exception e) {
            logger.debug(clazz + ":" + e.getMessage());
        }
    }


    @After("dataSourcePointCut()")
    public void after(){
        DynamicDataSource.clearDataSource();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
