package com.klef.jfsd.springboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klef.jfsd.springboot.model.Response;

import java.util.Map;
import java.util.List;
import java.util.Arrays;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;




@Service
public class ExternalAPIServiceImpl {

    private final Set<Integer> window = new LinkedHashSet<>();
    private final int windowSize = 10;
    private final String testServerUrl = "http://20.244.56.144/test/{numberid}";
    private final String bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJNYXBDbGFpbXMiOnsiZXhwIjoxNzE4MjYzODU1LCJpYXQiOjE3MTgyNjM1NTUsImlzcyI6IkFmZm9yZG1lZCIsImp0aSI6ImJlMDYzNThhLTI2NjEtNGY4YS1iNWU1LTFhZDNiZmJkMDBiOCIsInN1YiI6IjIxMDAwMzA4NzFjc2VoQGdtYWlsLmNvbSJ9LCJjb21wYW55TmFtZSI6IktMIFVuaXZlcnNpdHkiLCJjbGllbnRJRCI6ImJlMDYzNThhLTI2NjEtNGY4YS1iNWU1LTFhZDNiZmJkMDBiOCIsImNsaWVudFNlY3JldCI6IlN0bmpKY2NnVXhTc0JnRnAiLCJvd25lck5hbWUiOiJLLkFrc2hheSIsIm93bmVyRW1haWwiOiIyMTAwMDMwODcxY3NlaEBnbWFpbC5jb20iLCJyb2xsTm8iOiIyMTAwMDMwODcxIn0.G3pKh-LSL9Dv6d-EXH62F_OOa-YkLVIuDNeRY3CqfYY";

    @Autowired
    private RestTemplate template;

    public Response fetchAndCalculate(String numberId) {
        String url = UriComponentsBuilder.fromUriString(testServerUrl)
                .buildAndExpand(numberId)
                .toUriString();

        List<Integer> numbers = fetchNumbers(url);
        // Capture previous window state into the array
        List<Integer> prevWindow = new ArrayList<>(window); 

        // Update current window state
        synchronized (window) {
            numbers.forEach(num -> {
                if (window.size() >= windowSize) {
                    Iterator<Integer> iterator = window.iterator();
                    iterator.next(); 
                    iterator.remove();
                }
                window.add(num);
            });
        }

        Response response = new Response();
        response.setNumbers(numbers);
        response.setWindowPrevState(prevWindow);
        response.setWindowCurrState(new ArrayList<>(window));
        response.setAvg(calculateAverage(window));

        return response;
    }

    private List<Integer> fetchNumbers(String url) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + bearerToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Object> responseEntity = template.exchange(url, HttpMethod.GET, entity, Object.class);
            Object responseBody = responseEntity.getBody();

            if (responseBody instanceof Integer[]) {
                return Arrays.asList((Integer[]) responseBody);
            } else if (responseBody instanceof Map) {
                ObjectMapper mapper = new ObjectMapper();
                Integer[] numbersArray = mapper.convertValue(((Map<?, ?>) responseBody).get("numbers"), Integer[].class);
                if (numbersArray != null) {
                    return Arrays.asList(numbersArray);
                }
            }

            System.out.println("Received unexpected response format: " + responseBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private double calculateAverage(Set<Integer> numbers) {
        return numbers.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

}
