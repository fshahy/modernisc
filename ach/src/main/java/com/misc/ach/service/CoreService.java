package com.misc.ach.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.misc.ach.model.Line;

@Service
@PropertySource("classpath:custom.properties")
public class CoreService {
	
	@Value("${misc.core.url}")
	private String coreUrl;
	
	private RestTemplate rest = new RestTemplate();
	
	public Long submitLine(Line line) {
		ResponseEntity<Line> resp = rest.postForEntity(this.coreUrl, line, Line.class);
		
		if(resp.getStatusCode() == HttpStatus.CREATED) {
			return resp.getBody().getId();
		}
		
		return 0L;
	}
}