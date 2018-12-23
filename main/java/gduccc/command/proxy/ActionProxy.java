package gduccc.command.proxy;

import javax.annotation.Resource;

import gduccc.command.model.Request;
import gduccc.command.model.Response;
@Resource
public interface ActionProxy{
	<OUT extends Response ,IN extends Request> OUT DoExecute(String command,IN request) throws InstantiationException, IllegalAccessException ;
}
