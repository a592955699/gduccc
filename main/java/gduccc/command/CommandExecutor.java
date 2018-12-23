package gduccc.command;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import gduccc.command.interceptor.CommandFilterInterceptor;
import gduccc.command.interceptor.Interceptor;
import gduccc.command.model.Request;
import gduccc.command.model.Response;
import gduccc.command.utils.ClassFinderUtils;
import gduccc.command.utils.MessageUtils;

/**
 *   	拦截/切入点，命名分发器
 * @author rober
 *
 */
@Resource
public class CommandExecutor implements BeanPostProcessor{

	/**
	 *   	初始化时，给 commandConfig,actions 加载配置
	 */
  	@Override  
	public Object postProcessAfterInitialization(Object bean, String beanName)  
	{
  		//处理 Action 自动加载
  		Set<Class<?>> findActions = ClassFinderUtils.getClasses(componentScan,ActionSupport.class);
  		Iterator<Class<?>> findActionsIt = findActions.iterator();
  		Hashtable<String, Action> tempActions = new Hashtable<String, Action>();
  		while(findActionsIt.hasNext())
  		{
  			Class<?> clzz = findActionsIt.next();
  			try {
  				tempActions.put(clzz.getSimpleName(),(Action)clzz.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
  		}
  		this.setActions(tempActions);
  		
  		//处理 Interceptors 配置
		if(this.getInterceptors()!=null && !this.getInterceptors().isEmpty())
		{
			for(Interceptor interceptor:this.getInterceptors())
			{
				Enumeration<String> keys = this.getCommands().keys();
				while(keys.hasMoreElements()) 
				{
					Boolean isApply = true;
					
					String commandName = keys.nextElement();
					if(interceptor instanceof CommandFilterInterceptor)
					{
		                 isApply = ((CommandFilterInterceptor)interceptor).ApplyInterceptor(commandName);                     
					}
		
					if (isApply)
		            {
						CommandConfig config = this.getCommands().get(commandName);
		                config.AddInterceptor(interceptor);
		            }   
				}
			}
		}
		return bean;
	}
	
	public <T extends Response> T Process(String command, Request request) throws InstantiationException, IllegalAccessException {
		try		
		{
			CommandConfig commandConfig = commands.get(command);
			if(commandConfig==null)
			{
				throw new Exception("command not found! command: " + command);
			}
			
			 ActionInvocation invocation = new ActionInvocation();
             invocation.setAction(commandConfig.getAction());
             invocation.setCommand(command);
             invocation.setCommandConfig(commandConfig);
             invocation.setRequest(request);
             if (commandConfig.getInterceptors() != null)
             {
            	 invocation.setInterceptors(commandConfig.getInterceptors());
             }
             
             invocation.Invoke();
             Object response = invocation.getResponse();
             
             if (response != null) return (T)response;
        
             T rsp = (T)invocation.getAction().getResponseType().newInstance();
             return MessageUtils.SetSystemError(rsp);
		}
		catch(Exception ex)
		{ 
			 ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
             Class classT = (Class)type.getActualTypeArguments()[0];//<T>
             
             T rsp = (T)classT.newInstance();
             return MessageUtils.SetSystemError(rsp);		
		}
	}
	
	
	private Dictionary<String, CommandConfig> commands;
	private Dictionary<String, Action> actions;
	private ArrayList<Interceptor> interceptors;
	private String componentScan; 
	public String getComponentScan() {
		return componentScan;
	}

	public void setComponentScan(String componentScan) {
		this.componentScan = componentScan;
	}

	public Dictionary<String, CommandConfig> getCommands() {
		if(commands==null)
		{
			commands= new Hashtable<String, CommandConfig>();
		}
		return commands;
	}
	public void setCommands(Dictionary<String, CommandConfig> commands) {
		this.commands = commands;
	}
	public Dictionary<String, Action> getActions() {
		if(actions==null)
		{
			actions = new Hashtable<String, Action>();
		}
		return actions;
	}
	public void setActions(Dictionary<String, Action> actions) {
		if(this.commands==null)
		{
			this.commands=new Hashtable<String, CommandConfig>();
		}
		Enumeration<String> keys = actions.keys();
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			
			CommandConfig config = new CommandConfig();
			config.setAction((Action)actions.get(key));
			
			this.commands.put(key, config);
		}
		this.actions = actions;
	}
	public ArrayList<Interceptor> getInterceptors() {
		if(interceptors==null)
		{
			interceptors = new ArrayList<Interceptor>();
		}		
		return interceptors;
	}
	public void setInterceptors(ArrayList<Interceptor> interceptors) {
		this.interceptors = interceptors;
	}
}
