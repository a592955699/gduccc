package gduccc.command.model;

import java.util.UUID;

import com.alibaba.fastjson.JSONObject;

/**
 * 	请求参数基类
 * @author rober
 *
 */
public class Request {
	public Request()
	{
		setSerialNo(UUID.randomUUID().toString());
	}
	private String sessionId;
	private String serialNo;
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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
