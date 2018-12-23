package gduccc.command.utils;

import java.lang.reflect.ParameterizedType;

import gduccc.command.ActionInvocation;
import gduccc.command.CommandResponseCodes;
import gduccc.command.model.Request;
import gduccc.command.model.Response;

public class MessageUtils extends  CommandResponseCodes{
	
	public static Response SafeGetResponse(ActionInvocation invocation) throws InstantiationException, IllegalAccessException
	{
		Response response = (Response)invocation.getResponse() ;
		if (response != null) return response;
		
		Class<?> responseType = invocation.getAction().getResponseType();  
		response = (Response)responseType.newInstance(); 
		return response;
	}
	
	public static <T extends Response> T SafeGetResponse(Class<? extends Response> clzz) throws InstantiationException, IllegalAccessException
	{
		ParameterizedType type = (ParameterizedType)clzz.getGenericSuperclass();
        Class classT = (Class)type.getActualTypeArguments()[0];//<T>
        
        T rsp = (T)classT.newInstance();
        return rsp;
	}
    /**
     *	 获取请求消息的 SessionId
     * @param message
     * @return
     */
    public static String GetSessionId(Object message)
    {
        if (message instanceof Request)
        {
            return ((Request)message).getSessionId();
        }
        return null;
    }

    /**
     * 	检查响应是否为成功
     * @param resonse
     * @return
     */
    public static Boolean IsSuccess(Response resonse)
    {
        if (resonse == null)
        {
            return false;
        }
        return resonse.getCode() == Success;
    }
    /**
     * 	设置响应错误码
     * @param response
     * @param code
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T extends Response> T SetResposneCode(T response, int code) 
    {
        if (response == null)
        {
        	 ParameterizedType type = (ParameterizedType)MessageUtils.class.getGenericSuperclass();
             Class classT = (Class)type.getActualTypeArguments()[0];//<T>
             
             try {
				response = (T)classT.newInstance();
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
        }
        response.setCode(code); 
        return response;
    }
    
    /**
     * 	设置响应成功
     * @param response
     * @return
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public static <T extends Response> T SetSuccess(T response) 
    {
        return SetResposneCode(response, Success);
    }
    /**
     * session 无效
     * @param response
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T extends Response> T SetSessionExpired(T response) 
    {
    	return SetResposneCode(response, SessionExpired);
    }
    /**
     * 	设置响应错误
     * @param response
     * @return
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public static <T extends Response> T SetSystemError(T response)
    {
        return SetResposneCode(response, SystemError);
    }
    /**
     * 	权限不足
     * @param response
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T extends Response> T SetInsufficientPrivileges(T response) 
    {
        return SetResposneCode(response, InsufficientPrivileges);
    }
   
}
