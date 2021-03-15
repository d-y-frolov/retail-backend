package com.retail.backoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.retail.backoffice.config.InitialDemoDbFilling;

@SpringBootApplication
public class BackofficeApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(BackofficeApplication.class, args);
		
		InitialDemoDbFilling demoDbFiller = ctx.getBean(InitialDemoDbFilling.class);
		demoDbFiller.FillDemoDb();
  }
}