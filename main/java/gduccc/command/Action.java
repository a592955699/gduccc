package gduccc.command;

public interface Action {
	/**
	 * 	进行业务处理后,返回响应消息
	 * @param request
	 * @return
	 */
    Object DoExecute(Object request)  throws InstantiationException, IllegalAccessException; 
    
    Class<?> getRequestType();
    Class<?> getResponseType();
}
