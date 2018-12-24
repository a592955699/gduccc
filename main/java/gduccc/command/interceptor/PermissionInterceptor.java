package gduccc.command.interceptor;

import gduccc.command.ActionInvocation;
import gduccc.command.utils.*;
import gduccc.command.model.Response;

public abstract class PermissionInterceptor extends CommandFilterInterceptor{

	protected abstract Boolean HasPermission(ActionInvocation invocation);

	@Override
	public void intercept(ActionInvocation invocation){
		 if (HasPermission(invocation))
         {
             invocation.invoke();
         }
         else
         { 
 			Response response = MessageUtils.SafeGetResponse(invocation);
 			MessageUtils.SetInsufficientPrivileges(response);
 			invocation.setResponse(response);
         }
	}

}
