package gduccc.web.action;

import gduccc.command.ActionSupport;
import gduccc.command.utils.*;
import gduccc.web.action.Model.DemoActionRequest;
import gduccc.web.action.Model.DemoActionResponse;
public class DemoAction extends ActionSupport<DemoActionRequest,DemoActionResponse>{

	@Override
	public DemoActionResponse Execute(DemoActionRequest request) throws InstantiationException, IllegalAccessException  {
		DemoActionResponse response = new DemoActionResponse();
		MessageUtils.SetSuccess(response);
		response.setKeyWord("abc");
		response.setMessage("demo success");
		return response;
	}
}
