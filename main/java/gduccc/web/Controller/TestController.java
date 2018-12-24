package gduccc.web.Controller;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import javax.swing.plaf.basic.BasicSliderUI.ActionScroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import gduccc.command.CommandExecutor;
import gduccc.command.log.LoggerImpl;
import gduccc.web.action.DemoAction;
import gduccc.web.action.Model.DemoActionRequest;
import gduccc.web.action.Model.DemoActionResponse;
import gduccc.web.entity.TestEntity;
import gduccc.web.model.TestModel;

@Controller
@RequestMapping("test")
public class TestController {
	@Autowired
	public ActionServer actonServer;
	
	@Autowired
	public LoggerImpl loggerImpl;
	
	@RequestMapping(value = "demo", method = {RequestMethod.POST,RequestMethod.GET})
	public String Demo()
	{	
		DemoActionRequest request = new DemoActionRequest();
		DemoActionResponse response = actonServer.DemoAction(request);
		DemoActionRequest request2 = new DemoActionRequest();
		request2.setSessionId("abc");
		DemoActionResponse response2 = actonServer.DemoAction(request2);
		return "test/demo";
	}
}
