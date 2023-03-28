package merona.nabdbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NabdBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NabdBackendApplication.class, args);
	}

}
