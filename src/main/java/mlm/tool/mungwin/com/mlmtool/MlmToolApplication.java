package mlm.tool.mungwin.com.mlmtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MlmToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(MlmToolApplication.class, args);
    }

}
