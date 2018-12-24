package gduccc.command.interceptor;

import org.springframework.beans.factory.annotation.Autowired;

import gduccc.command.ActionInvocation;
import gduccc.command.log.LoggerImpl;
import gduccc.command.utils.*;
import gduccc.command.model.Response;

public class LogInterceptor extends CommandFilterInterceptor{

	@Autowired
	private LoggerImpl loggerImpl;
	
	private static String beforeLogTemplate =
            "command executing...\n"+
               "\t\tcommand = {}\n "+
               "\t\trequest = {}";

   private static String afterLogTemplate =
       "command executed\n" +
          "\t\tcommand = {}\n" +
          "\t\tresponse = {}";
   
	@Override
	public void intercept(ActionInvocation invocation) {
		try
        {
			loggerImpl.debug(beforeLogTemplate,invocation.getCommand(),invocation.getRequest().toString());
            invocation.invoke();
        }
        catch (Exception e)
        {
        	loggerImpl.error("LogInterceptor intercept error.",e);
        	throw e;
        }
		loggerImpl.debug(afterLogTemplate,invocation.getCommand(),invocation.getResponse().toString());
	}
}
