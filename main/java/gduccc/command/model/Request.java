package gduccc.command.model;

/**
 * 	请求参数基类
 * @author rober
 *
 */
public class Request {
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
}
