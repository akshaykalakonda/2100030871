package com.klef.jfsd.springboot.service;

import com.klef.jfsd.springboot.model.Response;

public interface ExternalAPIService {
	
	public Response fetchAndCalculate(String numberId);
	
}
