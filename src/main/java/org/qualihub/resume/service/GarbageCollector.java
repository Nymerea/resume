package org.qualihub.resume.service;

import lombok.extern.slf4j.Slf4j;
import org.qualihub.resume.pdf.PdfConvertor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
@Slf4j
public class GarbageCollector {

    @Scheduled(fixedRate = 500 * 1000) //500 seconds
    public void clearPdfs() throws IOException {
        log.info("Starting resume pdf cleaner");

        File storingDir = new File(PdfConvertor.STORING_FOLDER);
        storingDir.mkdirs();
        try (Stream<Path> files = Files.walk(Paths.get(PdfConvertor.STORING_FOLDER))) {
            files.filter(f -> f.getFileName().toString().startsWith(PdfConvertor.PREFIX))
                    .forEach(
                            file -> {
                                log.info("deleting {}", file.getFileName());
                                try {
                                    Files.delete(file);
                                } catch (IOException e) {
                                    log.error("cannot delete {}", file, e);
                                }
                            });
        }
    }
}
