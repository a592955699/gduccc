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
	public abstract void Intercept(ActionInvocation invocation)  throws InstantiationException, IllegalAccessException;
	
	public Boolean ApplyInterceptor(String command)
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
	private Set<String> incluedCommands;
	private Set<String> excludeCommands;
}
