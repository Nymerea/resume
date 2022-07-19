package org.qualihub.resume.pdf;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.buildobjects.process.ProcBuilder;
import org.buildobjects.process.TimeoutException;
import org.qualihub.resume.domain.service.ResumeService;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class PdfConvertor {
    public static final String PREFIX = "openresume_";
    public static final String STORING_FOLDER = "/tmp/resume/";
    private final ResumeService resumeService;

    public File generatePdf() throws TimeoutException {
        File pdf = new File(STORING_FOLDER + PREFIX + UUID.randomUUID() + ".pdf");
        log.debug("pdf will be generated at {}", pdf);
        ProcBuilder builder = new ProcBuilder("electron-pdf")
                .withArg("http://localhost:8080/cv")
                .withArg(pdf.getAbsolutePath())
                .withTimeoutMillis(10000);

        builder.run();
        return pdf;

    }
}
