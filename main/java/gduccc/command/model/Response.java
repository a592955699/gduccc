package gduccc.command.model;

import com.alibaba.fastjson.JSONObject;

/**
 * 	响应参数基类
 * @author rober
 *
 */
public class Response {
	private int code;
	private String message;
	private String serialNo;
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
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	public String toString(){
		return JSONObject.toJSONString(this);
    }
}
