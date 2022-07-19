package org.qualihub.resume.domain.dto.jsonresume;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Data
public class JsonResumeDto {
    BasicsJsonResumeDto basics = new BasicsJsonResumeDto();
    List<WorkJsonResumeDto> work = new ArrayList<>();
    List<EducationJsonResumeDto> education = new ArrayList<>();
    Locale locale;

}
