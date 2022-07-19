package org.qualihub.resume.domain.dto.jsonresume;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EducationJsonResumeDto {
    String institution;
    String url;
    String area;
    String studyType;
    String summary;
    LocalDate startDate;
    LocalDate endDate;
    Double score;
    List<String> courses;
}
