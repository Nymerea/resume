package org.qualihub.resume.web.service;

import lombok.RequiredArgsConstructor;
import org.qualihub.resume.domain.adapter.linkedin.LinkedinPdfAdapter;
import org.qualihub.resume.domain.model.Resume;
import org.qualihub.resume.domain.service.ResumeService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ResumeImportService {
    private final ResumeService resumeService;
    private final LinkedinPdfAdapter linkedinPdfAdapter;

    public Resume fromLinkedin(byte[] pdfContent, String fileName) throws IOException {
        var resume = linkedinPdfAdapter.fromBytes(pdfContent);
        return resumeService.save(resume, pdfContent, fileName);
    }
}
