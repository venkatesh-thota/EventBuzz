package com.stackroute.ZuulService;

import com.github.mthizo247.cloud.netflix.zuul.web.socket.EnableZuulWebSocket;
import filters.ErrorFilter;
import filters.PostFilter;
import filters.PreFilter;
import filters.RouteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@EnableZuulProxy
@SpringBootApplication
@EnableZuulWebSocket
@EnableWebSocketMessageBroker
public class ZuulServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulServiceApplication.class, args);
			}
	@Bean
        public PreFilter preFilter(){
		return new PreFilter();
	}
	@Bean
	public ErrorFilter errorFilter(){
		return new ErrorFilter();
	}
	@Bean
	public PostFilter postFilter(){
		return new PostFilter();
	}
	@Bean
	public RouteFilter routeFilter(){
		return new RouteFilter();
	}
}
