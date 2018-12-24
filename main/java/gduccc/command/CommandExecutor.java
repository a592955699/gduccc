package gduccc.command;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import gduccc.command.collection.EnumeratorList;
import gduccc.command.interceptor.CommandFilterInterceptor;
import gduccc.command.interceptor.Interceptor;
import gduccc.command.log.LoggerImpl;
import gduccc.command.model.Request;
import gduccc.command.model.Response;
import gduccc.command.utils.ClassFinderUtils;
import gduccc.command.utils.MessageUtils;

/**
 *   	拦截/切入点，命名分发器
 * @author rober
 *
 */
@Component
public class CommandExecutor implements BeanPostProcessor{

	@Autowired
	private LoggerImpl loggerImpl;
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
	/**
	 *   	初始化时，给 commandConfig,actions 加载配置
	 */
  	@Override  
	public Object postProcessAfterInitialization(Object bean, String beanName)  
	{
  		try	{
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
  			                 isApply = ((CommandFilterInterceptor)interceptor).applyInterceptor(commandName);                     
  						}
  			
  						if (isApply)
  			            {
  							CommandConfig config = this.getCommands().get(commandName);
  			                config.addInterceptor(interceptor);
  			            }   
  					}
  				}
  			}
  		}
  		catch(Exception e)
  		{
  			loggerImpl.error("CommandExecutor postProcessAfterInitialization error.",e);
  			throw e;
  		}
		return bean;
	}
	
  	/**
  	 *  Action 命令转发器
  	 * @param command
  	 * @param request
  	 * @return
  	 */
	public <T extends Response> T process(String command, Request request)  {
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
            	 //处理 EnumeratorList 使用后，index 不自动归零。注意：不确定 EnumeratorList 对象是否是瞬态，所以每次重新实例化一次
            	 EnumeratorList<Interceptor> interceptiors= new  EnumeratorList<Interceptor>();
            	 for(Interceptor iterceptor:commandConfig.getInterceptors())
            	 {
            		 interceptiors.add(iterceptor);
            	 }
            	 invocation.setInterceptors(interceptiors);
             }
             
             invocation.invoke();
             Object response = invocation.getResponse();
             
             if (response != null) {
            	T rsp =(T)response;
            	return rsp;
             }
	        
             try
             {
	             T rsp = (T)invocation.getAction().getResponseType().newInstance();
	             return MessageUtils.SetSystemError(rsp);
             } catch (InstantiationException | IllegalAccessException e) {
            	 e.printStackTrace();
				 loggerImpl.error("CommandExecutor Process %s T newInstance error.",command);
				 //throw new RuntimeException("CommandExecutor Process %s T newInstance error.",command);
				 return null;
             }
		}
		catch(Exception ex)
		{ 
			 ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
             Class classT = (Class)type.getActualTypeArguments()[0];//<T>
             
             T rsp;
             try {
				rsp = (T)classT.newInstance();
				rsp.setSerialNo(request.getSerialNo());
				return MessageUtils.SetSystemError(rsp);	
             } catch (InstantiationException | IllegalAccessException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				loggerImpl.error("CommandExecutor Process %s error.%s",command, e);
				//throw new RuntimeException("T newInstance error.");
	            return null;
             }
		}
	}
}
