package mongo.controller;

import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mongo.service.MongoDemoService;
import mongo.tool.JSONTool;

@RestController
public class MongoDemoController {

	@Autowired
	private MongoDemoService mongoDemoService;

	@RequestMapping(value = "/mongo/demo", method = RequestMethod.GET)
	public String demo() {

		JSONObject jsonObject = mongoDemoService.demo();

		return jsonObject.toString();
	}

	@RequestMapping(value = "/unsupport", method = RequestMethod.GET)
	public Set<String> unsupportClass() {
		Set<String> unsupportSet = JSONTool.getUnsupportClass();
		return unsupportSet;
	}
}
