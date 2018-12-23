package gduccc.command.interceptor;

import gduccc.command.ActionInvocation;
import gduccc.command.utils.*;
import gduccc.command.model.Response;

public abstract class LogInterceptor extends CommandFilterInterceptor{

	private static String beforeLogTemplate =
            "command executing...\n"+
               "\t\tcommand = {0}\n "+
               "\t\trequest = {1}";

   private static String afterLogTemplate =
       "command executed\n" +
          "\t\tcommand = {0}\n" +
          "\t\tresponse = {1}";
   
	@Override
	public void Intercept(ActionInvocation invocation) throws InstantiationException, IllegalAccessException {
		try
        {
			//#TODO 记录请求参数日志
            invocation.Invoke();
        }
        catch (Exception e)
        {
        	//#TODO 记录错误日志
            throw e;
        }
		//#TODO 记录响应错误日志
	}
}
