package cn.suishoucms.weixin.sun.utils;

import java.util.HashMap;
import java.util.Map;

public class Result {
	private String ret;
	private String msg;
	private String code;//nologin
	private Map<String, Object> data;
	private String sessionId;

	public Result(String ret, String msg, String code) {
		super();
		this.ret = ret;
		this.msg = msg;
		this.code = code;
	}

	public String getRet() {
		return ret;
	}

	public void setRet(String ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}




	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public static Result fail(String msg) {
		return new Result("fail", msg, null);
	}
	
	public static Result fail(String msg,String failCode) {
		return new Result("fail", msg, failCode);
	}
	

	public static Result success(String msg) {
		return new Result("success", msg, null);
	}
	
	public void data(String key,Object value){
		if(data==null){
			data=new HashMap<>();
		}
		data.put(key, value);
	}
	
	public Object data(String key){
		if(data==null){
			return null;
		}
		return data.get(key);
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


	
	
	
	

}
