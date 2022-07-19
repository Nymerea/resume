package org.qualihub.resume.web.mapper;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.qualihub.resume.domain.dto.jsonresume.JsonResumeDto;
import org.qualihub.resume.domain.dto.jsonresume.WorkJsonResumeDto;
import org.qualihub.resume.domain.dto.mapper.JsonResumeMapper;
import org.qualihub.resume.domain.model.Experience;
import org.qualihub.resume.domain.model.Ged;
import org.qualihub.resume.domain.model.Resume;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
class JsonResumeMapperTest {

    JsonResumeMapper instance = JsonResumeMapper.INSTANCE;

    @Test
    void resumeToDto_withImage_shouldMapToBase64() throws IOException {
        //GIVEN
        Resume resume = new Resume();
        File avatar = new File("src/main/resources/avatar.jpg");
        if (!avatar.exists()) {
            log.warn("cannot find avatar at {}", avatar.getAbsoluteFile());
        } else {
            BufferedImage read = ImageIO.read(avatar);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(read, "jpg", baos);
            Ged ged = new Ged();
            ged.setContent(baos.toByteArray());
            ged.setType(Ged.type.AVATAR);
            resume.setAvatar(ged);
        }
        //WHEN
        JsonResumeDto dto = instance.resumeToDto(resume);
        //THEN
        assertThat(dto.getBasics().getImage())
                .isNotEmpty()
                .startsWith("/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQg")
                .endsWith("jUrQNy8XzxY5x1X6jvVq1jW6i+zSDJbhSOuai+ptbQ//9k=");
    }

    @Test
    void resumeToDto_withExperience_shouldMapToWork() throws IOException {
        //GIVEN
        Resume resume = new Resume();
        Experience bnc = new Experience();
        bnc.setTitle("Java developper");
        bnc.setCompanyName("bnc");
        bnc.setDetails("J'ai integrer l'équipe comme expert java");
        bnc.setStartDate(LocalDate.of(2022, 01, 1));
        bnc.setEndDate(LocalDate.of(2025, 01, 1));
        resume.getExperiences().add(bnc);

        //WHEN
        JsonResumeDto dto = instance.resumeToDto(resume);
        //THEN
        assertThat(dto.getWork()).isNotEmpty()
                .first()
                .returns("Java developper", WorkJsonResumeDto::getPosition)
                .returns("bnc", WorkJsonResumeDto::getName)
                .returns("J'ai integrer l'équipe comme expert java", WorkJsonResumeDto::getSummary)
                .returns(LocalDate.of(2022, 01, 1), WorkJsonResumeDto::getStartDate)
                .returns(LocalDate.of(2025, 01, 1), WorkJsonResumeDto::getEndDate);

    }

}