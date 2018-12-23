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
import gduccc.web.action.DemoAction;
import gduccc.web.action.Model.DemoActionRequest;
import gduccc.web.action.Model.DemoActionResponse;
import gduccc.web.entity.TestEntity;
import gduccc.web.model.TestModel;

@Controller
@RequestMapping("test")
public class TestController {
	
	@Autowired
	public CommandExecutor commandExecutor;
	@Autowired
	public ActionServer actonServer;
	
	@RequestMapping(value = "demo", method = {RequestMethod.POST,RequestMethod.GET})
	public String Demo()
	{	
		DemoActionRequest request = new DemoActionRequest();
		try {
			DemoActionResponse response = actonServer.DemoAction(request);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		DemoActionRequest request2 = new DemoActionRequest();
		try {
			DemoActionResponse response2 = commandExecutor.Process("DemoAction", request2);
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "test/demo";
	}
}
