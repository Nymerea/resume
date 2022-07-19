package org.qualihub.resume.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qualihub.resume.domain.dto.jsonresume.JsonResumeDto;
import org.qualihub.resume.domain.model.Resume;
import org.qualihub.resume.domain.service.ResumeService;
import org.qualihub.resume.pdf.PdfConvertor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CvController {
    private final ResumeService resumeService;
    private final PdfConvertor pdfConvertor;

    @GetMapping("/cv/")
    public String displayResume(@RequestParam(name = "name", required = false, defaultValue = "resume1") String name,
                                @RequestParam(name = "id", required = false, defaultValue = "1") Long id,
                                Model model) {
        JsonResumeDto dto = resumeService.findById(id).orElseThrow();
        model.addAttribute("jsonResume", dto);
        log.info("loading template {}", name);
        log.info("{}", dto);
        return name;
    }

    @GetMapping("/cvForm")
    public String cvFormStep(@RequestParam(name = "id", required = false) Long id, Model model) {

        Resume dto = resumeService.findEntityById(id)
                .orElse(new Resume());
        model.addAttribute("resume", dto);
        log.info("{}", dto);
        return "ui/form";
    }

    @GetMapping("/pdf")
    public ResponseEntity<String> generatePdf(@RequestParam(name = "name", required = false, defaultValue = "resume1") String name, Model model) {
        File generatePdf = pdfConvertor.generatePdf();
        return ResponseEntity.ok(generatePdf.getAbsolutePath());
    }
}
