/**
 * 
 */
package com.java.techhub.kafka.demo.producer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.java.techhub.kafka.demo.model.UserDetails;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mahes
 *
 */
@Slf4j
@Service
public class MessagePublisher {

	@Autowired
	private KafkaTemplate<String, UserDetails> kafkaTemplate;

	@Value("${spring.kafka.topic}")
	private String topic;

	public Map<String, Object> publishMessage(UserDetails userDetails) {
		log.info("Publishing to kafka topic started...");
		String message = "";
		LocalDateTime publishedAt = null;
		try {
			ListenableFuture<SendResult<String, UserDetails>> result = kafkaTemplate.send(topic,
					userDetails.getUserId(), userDetails);
			SendResult<String, UserDetails> sendResult = result.get();
			Long publishedTs = sendResult.getRecordMetadata().timestamp();
			publishedAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(publishedTs), ZoneId.systemDefault());
			if (result.isCancelled()) {
				log.warn("Message with id: {}, publishing cancelled at:{}", userDetails.getUserId(), publishedAt);
				message = "Message publishing to topic cancelled";
			}
			if (result.isDone()) {
				log.info("Message with id: {}, published to topic: {} at: {}", userDetails.getUserId(), topic, publishedAt);
				message = "Message published to topic successfully";
			}
		} catch (InterruptedException | ExecutionException ex) {
			log.error("Message published failed due to: {}", ex);
			message = "Message publishing failed due to: " + ex.getMessage();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("message", message);
		map.put("published-at", publishedAt);
		return map;
	}
}
