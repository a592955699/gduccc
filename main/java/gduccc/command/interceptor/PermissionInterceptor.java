package gduccc.command.interceptor;

import gduccc.command.ActionInvocation;
import gduccc.command.utils.*;
import gduccc.command.model.Response;

public abstract class PermissionInterceptor extends CommandFilterInterceptor{

	protected abstract Boolean HasPermission(ActionInvocation invocation);

	@Override
	public void Intercept(ActionInvocation invocation) throws InstantiationException, IllegalAccessException {
		 if (HasPermission(invocation))
         {
             invocation.Invoke();
         }
         else
         { 
 			Response response = MessageUtils.SafeGetResponse(invocation);
 			MessageUtils.SetInsufficientPrivileges(response);
 			invocation.setResponse(response);
         }
	}

}
