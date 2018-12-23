package gduccc.command.interceptor;

import gduccc.command.ActionInvocation;
import gduccc.command.utils.*;
import gduccc.command.model.Response;

public abstract class TransactionInterceptor extends CommandFilterInterceptor{

	
	@Override
	public void Intercept(ActionInvocation invocation) throws InstantiationException, IllegalAccessException {
		try
        {
			//#TODO 开启事物
            invocation.Invoke();
            //#TODO 提交事物
        }
        catch (Exception e)
        {
        	//#TODO 回滚事物
            throw e;
        }
	}
}
