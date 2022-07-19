package org.qualihub.resume.domain.adapter.linkedin;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.qualihub.resume.domain.dto.jsonresume.EducationJsonResumeDto;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class LinkedinEducationExtractorTest {
    LinkedinEducationExtractor instance = new LinkedinEducationExtractor();

    @Test
    void fetch() {
        String content = "Utilisation du framwork : YiiFramework\n" +
        "conception de la base de données : Mysql\n" +
                "Formation\n" +
                "Dundalk Institute of Technology\n" +
                "bachelor of honor, Commercial computing · (2014 - 2015)\n" +
                "Université de Savoie\n" +
                "Licence, STIC · (2013 - 2014)\n" +
                "\n" +
                "Saint michel\n" +
                "BTS, IRIS · (2011 - 2013)\n" +
                "Saint joseph\n";

        var educations = instance.fetch(content);
        assertThat(educations).isNotEmpty();
        assertThat(educations)
                .extracting(EducationJsonResumeDto::getArea)
                .containsExactlyInAnyOrder("Commercial computing", "STIC", "IRIS");

        assertThat(educations)
                .extracting(EducationJsonResumeDto::getInstitution)
                .containsExactlyInAnyOrder("Dundalk Institute of Technology", "Université de Savoie", "Saint michel");

        assertThat(educations)
                .extracting(EducationJsonResumeDto::getStartDate)
                .containsExactlyInAnyOrder(LocalDate.of(2014,01,01),
                        LocalDate.of(2013,01,01),
                        LocalDate.of(2011,01,01));
        assertThat(educations)
                .extracting(EducationJsonResumeDto::getEndDate)
                .containsExactlyInAnyOrder(LocalDate.of(2015,01,01),
                        LocalDate.of(2014,01,01),
                        LocalDate.of(2013,01,01));
    }
}