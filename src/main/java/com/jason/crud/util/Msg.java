package com.jason.crud.util;

import java.util.HashMap;
import java.util.Map;

public class Msg {
	
	//״̬�� 100-�ɹ� 200-ʧ��
	private int code;
	//������Ϣ
	private String message;
	//���󷵻ظ������������
	private Map<String,Object> result = new HashMap<String, Object>();
	
	public static Msg success() {
		Msg result = new Msg();
		result.setCode(100);
		result.setMessage("����ɹ�");
		return result;
	}
	
	public static Msg fail() {
		Msg result = new Msg();
		result.setCode(200);
		result.setMessage("����ʧ��");
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
