package gduccc.command;

import java.util.ArrayList;

import gduccc.command.collection.EnumeratorList;
import gduccc.command.interceptor.Interceptor;

/**
 * Command 配置
 * @author rober
 *
 */
public class CommandConfig {
	/**
	 * 	增加一个拦截器
	 * @param interceptor
	 */
	public void AddInterceptor(Interceptor interceptor)
    {
        if (interceptors == null)
        {
            interceptors = new EnumeratorList<Interceptor>();
        }
        interceptors.add(interceptor);
    }
	
	private Action action;
    private EnumeratorList<Interceptor> interceptors;
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public EnumeratorList<Interceptor> getInterceptors() {
		return interceptors;
	}
	public void setInterceptors(EnumeratorList<Interceptor> interceptors) {
		this.interceptors = interceptors;
	}      
}
