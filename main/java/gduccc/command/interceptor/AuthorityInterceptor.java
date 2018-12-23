package gduccc.command.interceptor;

import gduccc.command.ActionInvocation;
import gduccc.command.utils.*;
import gduccc.command.model.Response;

public abstract class AuthorityInterceptor extends CommandFilterInterceptor{

	protected abstract Boolean IsAuthoritied(ActionInvocation invocation);

	@Override
	public void Intercept(ActionInvocation invocation) throws InstantiationException, IllegalAccessException {
		 if (IsAuthoritied(invocation))
         {
             invocation.Invoke();
         }
         else
         { 
 			Response response = MessageUtils.SafeGetResponse(invocation);
 			MessageUtils.SetSessionExpired(response);
 			invocation.setResponse(response);
         }
	}

}
