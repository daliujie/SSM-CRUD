package com.jason.crud.util;

import java.util.HashMap;
import java.util.Map;

public class Msg {
	
	//状态码 100-成功 200-失败
	private int code;
	//错误消息
	private String message;
	//请求返回给浏览器的数据
	private Map<String,Object> result = new HashMap<String, Object>();
	
	public static Msg success() {
		Msg result = new Msg();
		result.setCode(100);
		result.setMessage("处理成功");
		return result;
	}
	
	public static Msg fail() {
		Msg result = new Msg();
		result.setCode(200);
		result.setMessage("处理失败");
		return result;
	}
	
	public Msg add(String key,Object value) {
		this.getResult().put(key,value);
		return this;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, Object> getResult() {
		return result;
	}
	public void setResult(Map<String, Object> result) {
		this.result = result;
	}
	
	
	
}
