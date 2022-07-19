package org.qualihub.resume.startup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qualihub.resume.domain.model.*;
import org.qualihub.resume.domain.service.ResumeService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDate;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class MorganCvRunner implements ApplicationRunner {
    private final ResumeService resumeService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Application started with option names : {}", args.getOptionNames());


        log.info("Initialisation cv");
        if (resumeService.findById(1L).isEmpty()) {
            Education univSavoie = new Education();
            univSavoie.setTitle("STIC");
            univSavoie.setDiploma("Master");
            univSavoie.setDescription("j'ai appris plein de truc super interessants");
            univSavoie.setSchool("Université de savoie");
            univSavoie.setStartDate(LocalDate.of(2018, 01, 1));
            univSavoie.setEndDate(LocalDate.of(2019, 01, 1));

            Experience bnc = new Experience();
            bnc.setTitle("Java developer");
            bnc.setCompanyName("bnc");
            bnc.setDetails("Leverage agile frameworks to provide a robust synopsis for high level overviews. Iterative approaches to corporate strategy foster collaborative thinking to further the overall value proposition. ");
            bnc.setStartDate(LocalDate.of(2022, 01, 1));

            Skill java = new Skill();
            java.setTitle("Java");
            java.setPercent(90);
            Skill spring = new Skill();
            spring.setTitle("Spring boot");
            spring.setPercent(80);
            Resume resume = new Resume();
            resume.setName("Morgan DURAND");
            resume.setLabel("Java backend developer");
            resume.setAboutMe("Hello! I’m Morgan DURAND. I am passionate about Java development and java eco-system. I am a skilled Back-end Developer and proficient with devOps tools such as maven, Jenkins, ansible, and urbanCode");
            resume.setAddress("Montreal, quebec");
            resume.setBirthDate(LocalDate.of(1991, 01, 25));
            resume.setEmail("admin.angels@gmail.com");
            resume.setPhoneNumber("+336 41 00 52 17");
            resume.getEducations().add(univSavoie);
            resume.getExperiences().add(bnc);
            resume.getSkills().add(java);
            resume.getSkills().add(spring);

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
            resume.setUuid(UUID.randomUUID().toString());

            resumeService.save(resume);
            log.info("{} saved", resume);
        } else {
            log.info("resume already present, no need to add new one");
        }


    }
}