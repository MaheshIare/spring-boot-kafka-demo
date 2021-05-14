/**
 * 
 */
package com.java.techhub.kafka.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.techhub.kafka.demo.model.UserDetails;
import com.java.techhub.kafka.demo.producer.MessagePublisher;

/**
 * @author mahes
 *
 */
@RestController
@RequestMapping("/api/publish")
public class MessagePublishController {
	
	@Autowired
	private MessagePublisher messagePublisher;
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> publishMessage(@RequestBody UserDetails userDetails) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("message-id", userDetails.getUserId());
		map.putAll(messagePublisher.publishMessage(userDetails));
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}
