package gduccc.command.interceptor;

import gduccc.command.ActionInvocation;
import gduccc.command.utils.*;
import gduccc.command.model.Response;

public abstract class AuthorityInterceptor extends CommandFilterInterceptor{

	protected abstract Boolean IsAuthoritied(ActionInvocation invocation);

	@Override
	public void intercept(ActionInvocation invocation){
		 if (IsAuthoritied(invocation))
         {
             invocation.invoke();
         }
         else
         { 
 			Response response = MessageUtils.SafeGetResponse(invocation);
 			MessageUtils.SetSessionExpired(response);
 			invocation.setResponse(response);
         }
	}

}
