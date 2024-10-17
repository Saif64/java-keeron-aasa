package com.keeron.Keeron.AASA;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class KeeronAasaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeeronAasaApplication.class, args);
    }
}


@RestController
@RequestMapping("/.well-known")
class AASAController {

    @GetMapping(value = "/apple-app-site-association", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAASAFile() {
        try {
            // Accessing the file from the classpath
            ClassPathResource resource = new ClassPathResource("static/.well-known/apple-app-site-association");
            String content = new String(Files.readAllBytes(Paths.get(resource.getURI())));

            // Setting headers to display the file inline as JSON in the browser
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline");

            return new ResponseEntity<>(content, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("File not found or could not be read", HttpStatus.NOT_FOUND);
        }
    }
}