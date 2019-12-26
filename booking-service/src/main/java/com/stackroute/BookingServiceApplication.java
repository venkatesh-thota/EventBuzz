package com.stackroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@EnableRedisRepositories
@SpringBootApplication
public class BookingServiceApplication {

	@Bean
	public JedisConnectionFactory jedisConnectionFactory(){
		return new JedisConnectionFactory();
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate(){
		RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
		return redisTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(BookingServiceApplication.class, args);
	}
}
