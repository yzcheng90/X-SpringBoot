package com.suke.czx.modules.app.service;


import com.suke.czx.modules.app.dao.DaoSupport;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by czx on 2017/12/21.
 */

public class ServiceSupport {

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    /**
     * 保存对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object insert(String str, Object obj) throws Exception{
        return dao.save(str,obj);
    }

    /**
     * 修改对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object update(String str, Object obj) throws Exception{
        return dao.update(str,obj);
    }

    /**
     * 删除对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object delete(String str, Object obj) throws Exception{
        return dao.delete(str,obj);
    }

    /**
     * 查找对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public HashMap<String,Object> findForObject(String str, Object obj) throws Exception{
        return dao.findForObject(str,obj);
    }

    /**
     * 查找对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public List<HashMap<String,Object>> findForList(String str, Object obj) throws Exception{
        return dao.findForList(str,obj);
    }

}
