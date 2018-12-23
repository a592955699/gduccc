package gduccc.web.Controller;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import gduccc.command.model.Request;
import gduccc.command.model.Response;
import gduccc.command.proxy.ActionProxy;
import gduccc.command.proxy.DefaultActionProxy;
import gduccc.command.utils.MessageUtils;
import gduccc.web.action.Model.DemoActionRequest;
import gduccc.web.action.Model.DemoActionResponse;

@Resource
public class ActionServer {
	@Autowired
	public DefaultActionProxy defaultActionProxy;
	private <OUT extends Response,IN extends Request> OUT DoExecute(String command,IN request) throws InstantiationException, IllegalAccessException
	{
		try {
			return defaultActionProxy.DoExecute(command, request);
		} catch (Exception e) {
			
			 ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
             Class classT = (Class)type.getActualTypeArguments()[0];//<T>
             
             OUT response = (OUT)classT.newInstance();
             MessageUtils.SetSystemError(response);
             return response;
		}

	}
	
	public DemoActionResponse DemoAction(DemoActionRequest request) throws InstantiationException, IllegalAccessException
	{
		return DoExecute("DemoAction", request);
	}
}
