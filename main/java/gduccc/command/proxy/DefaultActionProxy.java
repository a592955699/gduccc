package gduccc.command.proxy;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import gduccc.command.CommandExecutor;
import gduccc.command.model.Request;
import gduccc.command.model.Response;

@Component
public class DefaultActionProxy implements ActionProxy{
	@Autowired
	private CommandExecutor commandExecutor;
	@Override
	public <OUT extends Response, IN extends Request> OUT DoExecute(String command,IN request) throws InstantiationException, IllegalAccessException {
		return commandExecutor.process(command, request);
	}
}
