package com.klef.jfsd.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.klef.jfsd.springboot.model.Response;
import com.klef.jfsd.springboot.service.ExternalAPIServiceImpl;

@RestController
public class RestAPIController {

	@Autowired
	private ExternalAPIServiceImpl apiServiceImpl;
	
	@GetMapping("/")
	public String demo() {
		return "<b>Microservice Demo using External API</b>";
	}
	
	@GetMapping("numbers/{numberId}")
    public Response getNumbers(@PathVariable String numberId) {
        System.out.println("Received request for numberId: " + numberId);
        return apiServiceImpl.fetchAndCalculate(numberId);
    }
	
}
