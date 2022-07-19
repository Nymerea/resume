package org.qualihub.resume.domain.adapter.linkedin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.qualihub.resume.domain.dto.jsonresume.EducationJsonResumeDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
class LinkedinEducationExtractor {
    public List<EducationJsonResumeDto> fetch(String content) {
        List<EducationJsonResumeDto> retval = new ArrayList<>();
        ;
        String extracted = StringUtils.substringAfterLast(content, "Formation");
        List<String> lines = Arrays.asList(StringUtils.split(extracted, "\n"));

        List<Integer> timeRangePositions = TimeRangeIndex.getTimeRangePositions(lines);
        for (int i = 0; i < timeRangePositions.size(); i++) {
            EducationJsonResumeDto dto = new EducationJsonResumeDto();
            Integer educationPosition = timeRangePositions.get(i);

            if ((educationPosition - 1) >= 0) {
                dto.setInstitution(lines.get(educationPosition - 1));
            }

            // look like Licence, STIC · (2013 - 2014)
            String line = lines.get(educationPosition);

            dto.setStudyType(StringUtils.substringBefore(line, ", "));
            dto.setArea(StringUtils.substringBetween(line, ", ", " · ("));

            try {
                String startYearStr = StringUtils.substringBetween(line, "(", " - ");
                dto.setStartDate(LocalDate.of(Integer.valueOf(startYearStr), 01, 01));
            } catch (Exception e) {
                log.trace("cannot retrieve startYear from {} : {}", line, e.getMessage());
            }
            try {
                String endYearStr = StringUtils.substringBetween(line, " - ", ")");
                dto.setEndDate(LocalDate.of(Integer.valueOf(endYearStr), 01, 01));
            } catch (Exception e) {
                log.trace("cannot retrieve endYear from {} : {}", line, e.getMessage());
            }
            retval.add(dto);

        }

        return retval;
    }
}
