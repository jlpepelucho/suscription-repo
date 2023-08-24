package ec.com.zurich.suscription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ec.com.zurich.web.start.StartWebApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication(scanBasePackages = "ec.com.zurich")
@EnableFeignClients
public class ZurichApplication extends StartWebApplication{

	public static void main(String[] args) {
		SpringApplication.run(ZurichApplication.class, args);
	}

}