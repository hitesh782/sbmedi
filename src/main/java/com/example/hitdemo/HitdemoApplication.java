package com.example.hitdemo;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.FilterConfig;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableMongoRepositories
@EnableMongoAuditing
@Slf4j
public class HitdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HitdemoApplication.class, args);
	}

	@Bean
	public Filter corsFilter() {

		return new Filter() {
			@Override
			public void init(FilterConfig filterConfig) throws ServletException {
			}

			@Override
			public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
					throws IOException, ServletException {

				HttpServletResponse response = (HttpServletResponse) res;
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setHeader("Access-Control-Allow-Credentials", "true");
				response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT,PATCH");
				response.setHeader("Access-Control-Max-Age", "3600");
				response.setHeader("Access-Control-Allow-Headers",
						"Origin, Content-Type, Accept, X-Requested-With, remember-me, Authorization, Content-Encoding,x_tenant, x_tenant_skip");
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

				chain.doFilter(req, res);
			}

			@Override
			public void destroy() {
			}
		};

	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
