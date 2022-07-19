package org.qualihub.resume.domain.dto.jsonresume;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@FieldNameConstants
public class WorkJsonResumeDto {
    String name;
    String position;
    // not standard
    String location;
    String url;
    LocalDate startDate;
    LocalDate endDate;
    String summary;
    List<String> highlights = new ArrayList<>();
}
