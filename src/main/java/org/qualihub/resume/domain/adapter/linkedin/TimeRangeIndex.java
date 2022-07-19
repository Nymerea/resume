package org.qualihub.resume.domain.adapter.linkedin;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
 class TimeRangeIndex {
    private TimeRangeIndex() {
    }

    static List<Integer> getTimeRangePositions(List<String> lines) {
        List<Integer> timeRangePositions = new ArrayList<Integer>();
        for (int i = 0; i < lines.size(); i++) {
            String line = StringUtils.trim(lines.get(i));
            // look like : juin 2014 - août 2014 (3 mois) or  Licence, STIC · (2013 - 2014)
            if (StringUtils.endsWith(line, ")") && StringUtils.contains(line, "(") && StringUtils.contains(line, " - ")) {
                timeRangePositions.add(i);
            }
        }
        return timeRangePositions;
    }
}