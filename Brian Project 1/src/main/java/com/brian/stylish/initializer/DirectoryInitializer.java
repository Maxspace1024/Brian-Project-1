package com.brian.stylish.initializer;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Log4j2
public class DirectoryInitializer implements CommandLineRunner {

    @Value("${directory.image.name}")
    private String DIRECTORY_NAME;

    @Override
    public void run(String... args) throws Exception {
        File dir = new File(DIRECTORY_NAME);
        if (!dir.exists()) {
            boolean isCreated = dir.mkdir();
            if (isCreated) {
                log.info("create stylish_images directory success");
            } else {
                log.info("create stylish_images directory fail");
            }
        }
    }
}
