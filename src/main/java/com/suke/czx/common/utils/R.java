package com.suke.czx.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suke.zhjg.common.autofull.handler.AutoFullHandler;
import com.suke.zhjg.common.autofull.sequence.AutoSequence;
import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回数据
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2016年10月27日 下午9:59:27
 */
public class R extends HashMap<String, Object> {

	private static final int defaultLevel = 1;

	public R() {
		put("code", 0);
		put("msg", "success");
	}

	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}

	public static R error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg,String data) {
		R r = new R();
		r.put("msg", msg);
		r.put("data", data);
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R ok() {
		return new R();
	}

	public R setPage(Object value) {
		super.put("page", value);
		return this;
	}

	public R setData(Object value) {
		super.put("data", value);
		return this;
	}

	public <T> R autoFullData(IPage<T> iPage){
		String sequence = AutoSequence.init().put(defaultLevel);
		AutoFullHandler.full(iPage,sequence);
		AutoSequence.init().remove(sequence);
		super.put("data", iPage);
		return this;
	}

	public <T> R autoFullData(List<T> list){
		String sequence = AutoSequence.init().put(defaultLevel);
		AutoFullHandler.full(list,sequence);
		AutoSequence.init().remove(sequence);
		super.put("data", list);
		return this;
	}

	public <T> R autoFullData(T entity){
		String sequence = AutoSequence.init().put(defaultLevel);
		AutoFullHandler.full(entity,sequence);
		AutoSequence.init().remove(sequence);
		super.put("data", entity);
		return this;
	}

	public <T> R autoFullData(IPage<T> iPage,int maxLevel){
		String sequence = AutoSequence.init().put(maxLevel);
		AutoFullHandler.full(iPage,sequence,defaultLevel);
		AutoSequence.init().remove(sequence);
		super.put("data", iPage);
		return this;
	}

	public <T> R autoFullData(List<T> list,int maxLevel){
		String sequence = AutoSequence.init().put(maxLevel);
		AutoFullHandler.full(list,sequence,defaultLevel);
		AutoSequence.init().remove(sequence);
		super.put("data", list);
		return this;
	}

	public <T> R autoFullData(T entity,int maxLevel){
		String sequence = AutoSequence.init().put(maxLevel);
		AutoFullHandler.full(entity,sequence,defaultLevel);
		AutoSequence.init().remove(sequence);
		super.put("data", entity);
		return this;
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
