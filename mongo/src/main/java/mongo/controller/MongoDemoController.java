package mongo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mongo.service.MongoDemoService;

@RestController
public class MongoDemoController {
	
	@Autowired
	private MongoDemoService mongoDemoService;
	
	@RequestMapping(value = "/mongo/demo", method = RequestMethod.GET)
	public Object demo(){
		
		Object object = mongoDemoService.demo();
		
		return object;
	}

}
