package gduccc.web.interceptor;

import gduccc.command.ActionInvocation;
import gduccc.command.interceptor.AuthorityInterceptor;
import gduccc.command.interceptor.CommandFilterInterceptor;
import gduccc.command.utils.MessageUtils;
import gduccc.command.model.Response;

public class SessionBindInterceptor extends AuthorityInterceptor{

	@Override
	protected Boolean IsAuthoritied(ActionInvocation invocation) {
		//#TODO 添加session 验证机制
		String sessionId = MessageUtils.GetSessionId(invocation.getRequest());
		return sessionId != null && !sessionId.isEmpty();
	}  
}
