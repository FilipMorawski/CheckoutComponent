package com.filipmorawski.checkoutcomponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;


@SpringBootApplication
public class CheckoutComponentApplication {

	@Bean
	public ModelMapper ModelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(CheckoutComponentApplication.class, args);
	}
}
