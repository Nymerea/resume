package org.qualihub.resume.domain.adapter.linkedin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.qualihub.resume.domain.dto.jsonresume.WorkJsonResumeDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
class LinkedinExperienceExtractor {

    public List<WorkJsonResumeDto> fetch(String content) {
        List<WorkJsonResumeDto> retval = new ArrayList<>();

        // experiences is between Experiences and Formation
        String afterExp = StringUtils.substringAfter(content, "Expérience");
        String experiences = StringUtils.substringBeforeLast(afterExp, "Formation");


        String[] rawLines = StringUtils.split(experiences, "\n");
        List<String> lines = Stream.of(rawLines)
                .map(StringUtils::trim)
                .filter(e -> !StringUtils.startsWith(e, "Page "))
                .collect(Collectors.toList());

        // the only line we can recognize is time range
        List<Integer> timeRangePositions = TimeRangeIndex.getTimeRangePositions(lines);


        for (int i = 0; i < timeRangePositions.size(); i++) {
            Integer timeRangePosition = timeRangePositions.get(i);
            WorkJsonResumeDto work = new WorkJsonResumeDto();

            boolean isLast = i == (timeRangePositions.size() - 1);

            if (timeRangePosition >= 2 && (timeRangePosition + 1) < lines.size()) {
                fillCompanyName(lines, timeRangePosition, work);
                fillJobTitle(lines, timeRangePosition, work);
                fillTimeRange(lines, timeRangePosition, work);
                // this one is optional and error-prone
                fillLocation(lines, timeRangePosition, work);
                fillSummary(lines, timeRangePositions, i, timeRangePosition, work, isLast);
                retval.add(work);
            }
        }

        return retval;
    }

    private void fillSummary(List<String> lines, List<Integer> timeRangePositions, int i, Integer timeRangePosition, WorkJsonResumeDto work, boolean isLast) {
        // then it's description
        if (((timeRangePosition + 2) < lines.size())) {
            String description = StringUtils.EMPTY;
            int endDescriptionPosition = getEndDescriptionPosition(lines, timeRangePositions, i, isLast);
            for (int j = timeRangePosition + 2; j < endDescriptionPosition; j++) {
                String currentLine = StringUtils.strip(lines.get(j));
                description += currentLine + "\n";
            }
            work.setSummary(StringUtils.substringBeforeLast(description, "\n"));
        }
    }

    private void fillLocation(List<String> lines, Integer timeRangePosition, WorkJsonResumeDto work) {
        // fourth line is location
        if ((timeRangePosition + 1) < lines.size()) {
            work.setLocation(lines.get(timeRangePosition + 1));
        }
    }

     void fillTimeRange(List<String> lines, Integer timeRangePosition, WorkJsonResumeDto work) {
        // third line is time range
        String timeRangeStr = lines.get(timeRangePosition);
        String startDateStr = StringUtils.substringBeforeLast(timeRangeStr, " -").trim() + " 01";
        String endDateStr = StringUtils.substringBetween(timeRangeStr, "- ", "(").trim() + " 01";

        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM u dd", locale);
                // throw un exception if fail
                extractDates(work, startDateStr, endDateStr, dateFormatter);
                break;
            } catch (Exception e) {
                log.trace("cannot parse date {} with locale {} : ", timeRangeStr, locale, e.getMessage());
            }
        }
    }

    private void fillJobTitle(List<String> lines, Integer timeRangePosition, WorkJsonResumeDto work) {
        // second line is job title
        if ((timeRangePosition - 1) >= 0) {
            work.setPosition(lines.get(timeRangePosition - 1));
        }
    }

    private void fillCompanyName(List<String> lines, Integer timeRangePosition, WorkJsonResumeDto work) {
        if ((timeRangePosition - 2) >= 0) {
            // first line is company name
            work.setName(lines.get(timeRangePosition - 2));
        }
    }

    private void extractDates(WorkJsonResumeDto work, String startDateStr, String endDateStr, DateTimeFormatter dateFormatter) {
        LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
        if(work.getStartDate() == null && startDate != null) {
            work.setStartDate(startDate);
        }
        if (!"present".equalsIgnoreCase(endDateStr) && StringUtils.isNotEmpty(endDateStr)) {
            LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
            if(work.getEndDate() == null && endDate != null) {
                work.setEndDate(endDate);
            }
        }
    }

    private int getEndDescriptionPosition(List<String> split, List<Integer> timeRangePositions, int i, boolean isLast) {
        int endDescriptionPosition;
        if (isLast) {
            endDescriptionPosition = split.size();
        } else {
            Integer nextTimeRange = timeRangePositions.get(i + 1);
            endDescriptionPosition = nextTimeRange - 2;
        }
        return endDescriptionPosition;
    }
}
