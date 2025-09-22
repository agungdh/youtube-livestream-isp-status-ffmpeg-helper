package com.example.demo.controller;

import com.example.demo.service.StatusImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MainController {
    @Value("${spring.application.name}")
    private String appName;

    private final StatusImageService statusImageService;

    @GetMapping
    public String tehe() {
        return appName;
    }

    @GetMapping("/generate")
    public void generateImage() throws IOException {
        statusImageService.generate();
    }
}
