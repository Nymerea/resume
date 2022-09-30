package org.qualihub.resume.domain.service;

import lombok.RequiredArgsConstructor;
import org.qualihub.resume.domain.dto.jsonresume.JsonResumeDto;
import org.qualihub.resume.domain.dto.mapper.JsonResumeMapper;
import org.qualihub.resume.domain.model.Ged;
import org.qualihub.resume.domain.model.Resume;
import org.qualihub.resume.domain.repository.ResumeRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepo resumeRepo;
    private final JsonResumeMapper mapper = JsonResumeMapper.INSTANCE;

    @Transactional
    public Resume save(Resume resume) {
        return resumeRepo.save(resume);
    }

    @Transactional(readOnly = true)
    public Optional<JsonResumeDto> findById(Long id) {
        return resumeRepo.findById(id).map(mapper::resumeToDto);
    }

    @Transactional(readOnly = true)
    public Optional<Resume> findEntityById(Long id) {
        return resumeRepo.findById(id);
    }

    @Transactional
    public Resume save(JsonResumeDto resumeDto, byte[] pdfContent, String fileName) {
        Resume resume = mapper.dtoToResume(resumeDto);

        //TODO perform zip compression
        if (pdfContent != null && pdfContent.length > 0) {
            Ged doc = new Ged();
            doc.setType(Ged.type.PDF_LINKEDIN);
            doc.setContent(pdfContent);
            doc.setFileName(StringUtils.cleanPath(fileName));
            resume.setImportedFrom(doc);
        }
        return resumeRepo.save(resume);
    }
}
