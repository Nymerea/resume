package org.qualihub.resume.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qualihub.resume.web.service.ResumeImportService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/start")
@Slf4j
public class LinkedinResumeUploadController {
    private final ResumeImportService importService;

    @GetMapping("/")
    public String homepage() {
        return "ui/start";
    }

    @PostMapping("/linkedinUpload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws IOException {

        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/start/";
        }

        // TODO perform tika validation ?
        MediaType typeMime = null;
        try {
            typeMime = MediaType.valueOf(file.getContentType());
        } catch (Exception e) {
            log.debug("cannot guess type mime of {} : {}", file.getContentType(), e.getMessage());
        }
        if (!MediaType.APPLICATION_PDF.isCompatibleWith(typeMime)) {
            attributes.addFlashAttribute("message", "Only pdf are accepted");
            return "redirect:/start/";
        }

        var createdResume = importService.fromLinkedin(file.getBytes(), file.getOriginalFilename());

        // return success response
        attributes.addAttribute("id", createdResume.getId());
        return "redirect:/cvForm/";
    }

}