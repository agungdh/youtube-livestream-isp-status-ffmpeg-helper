package id.my.agungdh.ffmpeghelper.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {
    @Value("${spring.application.name}")
    private String appName;

    @GetMapping
    public String tehe() {
        return appName;
    }
}
