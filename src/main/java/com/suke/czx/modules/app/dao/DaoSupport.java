package com.suke.czx.modules.app.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * DAO
 * @author czx
 * @email yzcheng90@qq.com
 * @date 2017-12-21 10:40:37
 */
@Repository("daoSupport")
public class DaoSupport {

	@Autowired
	private SqlSession sqlSession;

	/**
	 * 保存对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object save(String str, Object obj) throws Exception {
		return sqlSession.insert(str, obj);
	}
	
	/**
	 * 批量更新
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public Object batchSave(String str, List objs )throws Exception{
		return sqlSession.insert(str, objs);
	}
	
	/**
	 * 修改对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object update(String str, Object obj) throws Exception {
		return sqlSession.update(str, obj);
	}
	
	/**
	 * 批量更新
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public Object batchDelete(String str, List objs )throws Exception{
		return sqlSession.delete(str, objs);
	}
	
	/**
	 * 删除对象 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public Object delete(String str, Object obj) throws Exception {
		return sqlSession.delete(str, obj);
	}
	 
	/**
	 * 查找对象
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> findForObject(String str, Object obj) throws Exception {
		return sqlSession.selectOne(str, obj);
	}

	/**
	 * 查找对象
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String,Object>> findForList(String str, Object obj) throws Exception {
		return sqlSession.selectList(str, obj);
	}
	
	public Object findForMap(String str, Object obj, String key, String value) throws Exception {
		return sqlSession.selectMap(str, obj, key);
	}
	
}


