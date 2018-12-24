package gduccc.command;

public interface Action {
	/**
	 * 	进行业务处理后,返回响应消息
	 * @param request
	 * @return
	 */
    Object doExecute(Object request); 
    
    Class<?> getRequestType();
    Class<?> getResponseType();
}
