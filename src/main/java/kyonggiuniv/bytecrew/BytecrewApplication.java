package kyonggiuniv.bytecrew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BytecrewApplication {

	public static void main(String[] args) {
		SpringApplication.run(BytecrewApplication.class, args);
	}

}
