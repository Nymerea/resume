package org.qualihub.resume.domain.adapter.linkedin;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.qualihub.resume.domain.dto.jsonresume.JsonResumeDto;
import org.qualihub.resume.domain.dto.jsonresume.WorkJsonResumeDto;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class LinkedinPdfAdapter {

    private final LinkedinEmailExtractor emailExtractor = new LinkedinEmailExtractor();
    private final LinkedinExperienceExtractor experienceExtractor = new LinkedinExperienceExtractor();
    private final LinkedinEducationExtractor educationExtractor = new LinkedinEducationExtractor();

    public JsonResumeDto fromFile(File file) throws IOException {
        JsonResumeDto jsonResumeDto = new JsonResumeDto();

        PDFParser parser = new PDFParser(new org.apache.pdfbox.io.RandomAccessFile(file, "r"));
        parser.parse();
        try (COSDocument cosDoc = parser.getDocument()) {
            PDDocument pdDoc = new PDDocument(cosDoc);
            extractResumeFromPdf(jsonResumeDto, pdDoc);
        }

        return jsonResumeDto;
    }

    public JsonResumeDto fromBytes(byte[] documentBytes) throws IOException {
        JsonResumeDto jsonResumeDto = new JsonResumeDto();

        try (PDDocument document = PDDocument.load(documentBytes)) {
            extractResumeFromPdf(jsonResumeDto, document);

        }

        return jsonResumeDto;
    }

    private void extractResumeFromPdf(JsonResumeDto jsonResumeDto, PDDocument pdDoc) throws IOException {
        PDFTextStripper pdfStripper = new PDFTextStripper();
        pdfStripper.setShouldSeparateByBeads(false);
        pdfStripper.setStartPage(1);
        String parsedText = pdfStripper.getText(pdDoc);
        System.out.println(parsedText);
        String unifiedCarriageReturn = RegExUtils.replaceAll(parsedText, "\n\r", "\n");
        // remove unbreakable space
        String withBreakableSpace = RegExUtils.replaceAll(unifiedCarriageReturn, "\\p{Z}", " ");
        String[] rawLines = StringUtils.split(withBreakableSpace, "\n");
        String cleaned = Stream.of(rawLines)
                .map(StringUtils::trim)
                // remove useless page footer
                .filter(e -> !StringUtils.startsWith(e, "Page "))
                .collect(Collectors.joining("\n"));

        jsonResumeDto.getBasics().setEmail(emailExtractor.findEmail(cleaned));
        List<WorkJsonResumeDto> workJsonResumeDtos = experienceExtractor.fetch(cleaned);
        jsonResumeDto.getWork().addAll(workJsonResumeDtos);
        jsonResumeDto.getEducation().addAll(educationExtractor.fetch(cleaned));
    }


}
