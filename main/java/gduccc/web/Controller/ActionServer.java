package gduccc.web.Controller;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import gduccc.command.model.Request;
import gduccc.command.model.Response;
import gduccc.command.proxy.ActionProxy;
import gduccc.command.proxy.DefaultActionProxy;
import gduccc.command.utils.MessageUtils;
import gduccc.web.action.Model.DemoActionRequest;
import gduccc.web.action.Model.DemoActionResponse;

@Component
public class ActionServer {
	@Autowired
	public DefaultActionProxy defaultActionProxy;
	private <OUT extends Response,IN extends Request> OUT DoExecute(String command,IN request) 
	{
		try {
			return defaultActionProxy.DoExecute(command, request);
		} catch (Exception e) {
			
			ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
			Class classT = (Class)type.getActualTypeArguments()[0];//<T>
             
			OUT response;
			try {
				response = (OUT)classT.newInstance();
			} catch (InstantiationException | IllegalAccessException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
				throw new RuntimeException("OUT newInstance error.");
			}
			MessageUtils.SetSystemError(response);
			return response;
		}
	}
	
	public DemoActionResponse DemoAction(DemoActionRequest request) 
	{
		return DoExecute("DemoAction", request);
	}
}
