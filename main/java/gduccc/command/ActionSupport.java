package gduccc.command;

import java.lang.reflect.ParameterizedType;

import gduccc.command.model.Request;
import gduccc.command.model.Response;

public abstract class ActionSupport<IN extends Request, OUT extends Response> implements Action {

    /**
     * 	进行业务处理后,返回响应消息
     * @param request
     * @return
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public abstract OUT Execute(IN request) throws InstantiationException, IllegalAccessException;
	
	public Object DoExecute(Object request) throws InstantiationException, IllegalAccessException {
		return Execute((IN)request);
	}
	
	@Override
	public Class<?> getRequestType() {
		ParameterizedType type = (ParameterizedType) this.getClass()
                .getGenericSuperclass();		 
        return (Class<?>) type.getActualTypeArguments()[0];
	}

	@Override
	public Class<?> getResponseType() {		
		ParameterizedType type = (ParameterizedType) this.getClass()
                .getGenericSuperclass();		 
        return (Class<?>) type.getActualTypeArguments()[1];
	}
}
