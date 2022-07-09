package anpopo.spring.framework.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class SpringFrameworkPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringFrameworkPracticeApplication.class, args);
	}

}
