package gduccc.command.interceptor;

import gduccc.command.ActionInvocation;

/**
 * 	拦截器
 * @author rober
 *
 */
public interface Interceptor {
	/**
	 * 	拦截
	 * @param invocation
	 */
	void intercept(ActionInvocation invocation);
}
