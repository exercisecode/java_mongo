package mongo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerifyController {
	
	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public String verify(@RequestParam( name = "name", required  = false) String name ){
		if(name == null || name.trim().isEmpty()){
			name = "none body";
		}
		
		return "Hello,  [" + name + "]";
	}

}
