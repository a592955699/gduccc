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
    public abstract OUT execute(IN request);
	
	public Object doExecute(Object request) {
		IN req =(IN)request;
		OUT resonse = execute(req);
		resonse.setSerialNo(req.getSerialNo());
		return resonse;
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
