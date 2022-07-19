package org.qualihub.resume.domain.adapter.linkedin;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LinkedinEmailExtractor {
    private static final String EMAIL_PATTERN =
            "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
                    + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
    private final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    public String findEmail(String content) {
        // email should be between Coordonnées and Expérience
        String afterContact = StringUtils.substringAfter(content, "Coordonnées");
        String beforeExperience = StringUtils.substringBefore(afterContact, "Expérience");
        String[] splited = StringUtils.split(beforeExperience, "\n");
        for (String line : splited) {
            Matcher matcher = emailPattern.matcher(line);

            while (matcher.find()) {
                return matcher.group();
            }
        }


        return "";
    }
}
