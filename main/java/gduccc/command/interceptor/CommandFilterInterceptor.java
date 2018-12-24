package gduccc.command.interceptor;

import java.util.Set;

import gduccc.command.ActionInvocation;
import gduccc.command.interceptor.Interceptor;

/**
 * 	支持 command 名过滤的 拦截器
 * @author rober
 *
 */
public abstract class CommandFilterInterceptor implements Interceptor {
	
	private Set<String> incluedCommands;
	private Set<String> excludeCommands;
	public Set<String> getIncluedCommands() {
		return incluedCommands;
	}
	public void setIncluedCommands(Set<String> incluedCommands) {
		this.incluedCommands = incluedCommands;
	}
	public Set<String> getExcludeCommands() {
		return excludeCommands;
	}
	public void setExcludeCommands(Set<String> excludeCommands) {
		this.excludeCommands = excludeCommands;
	}

	/**
	 * 	执行拦截
	 */
	public abstract void intercept(ActionInvocation invocation);
	/**
	 *   	是否应用此拦截器
	 * @param command
	 * @return
	 */
	public Boolean applyInterceptor(String command)
    {
        if (incluedCommands != null)
        {
            return incluedCommands.contains(command);
        }
        if (excludeCommands != null)
        {
            return !excludeCommands.contains(command);
        }
        return true;
    }
}
