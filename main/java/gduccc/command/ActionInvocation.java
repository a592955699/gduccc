package gduccc.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import gduccc.command.collection.EnumeratorList;
import gduccc.command.interceptor.Interceptor;


/**
 * Action 的调用器, 将 action 的调用封装,完成调用
 * @author rober
 *
 */
public class ActionInvocation {
	/**
	 * 	完成调用
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public void Invoke() throws InstantiationException, IllegalAccessException
    {
		EnumeratorList<Interceptor> iterator = this.getInterceptors();
        if (iterator.moveNext()) {
        	Interceptor interceptor = iterator.getCurrent();
            interceptor.Intercept(this);
        }
        else
        {
        	response = action.DoExecute(request);
        }
    }
	
	private String command;
	private Action action;
	private Object request;
	private Object response;
 	private CommandConfig commandConfig;
 	private EnumeratorList<Interceptor> interceptors;
     
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
	public Object getRequest() {
		return request;
	}
	public void setRequest(Object request) {
		this.request = request;
	}
	public Object getResponse() {
		return response;
	}
	public void setResponse(Object response) {
		this.response = response;
	}
	public CommandConfig getCommandConfig() {
		return commandConfig;
	}
	public void setCommandConfig(CommandConfig commandConfig) {
		this.commandConfig = commandConfig;
	}
	public EnumeratorList<Interceptor> getInterceptors() {
		if(interceptors==null)
		{
			interceptors= new EnumeratorList<Interceptor>();
		}
		return interceptors;
	}
	public void setInterceptors(EnumeratorList<Interceptor> interceptors) {
		this.interceptors = interceptors;
	}
}
