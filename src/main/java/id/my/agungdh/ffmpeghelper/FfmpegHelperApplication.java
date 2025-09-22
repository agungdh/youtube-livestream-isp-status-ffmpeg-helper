package id.my.agungdh.ffmpeghelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FfmpegHelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(FfmpegHelperApplication.class, args);
    }

}
