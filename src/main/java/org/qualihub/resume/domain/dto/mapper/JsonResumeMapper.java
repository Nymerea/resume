package org.qualihub.resume.domain.dto.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.qualihub.resume.config.DateConfig;
import org.qualihub.resume.domain.dto.jsonresume.EducationJsonResumeDto;
import org.qualihub.resume.domain.dto.jsonresume.JsonResumeDto;
import org.qualihub.resume.domain.dto.jsonresume.WorkJsonResumeDto;
import org.qualihub.resume.domain.model.Education;
import org.qualihub.resume.domain.model.Experience;
import org.qualihub.resume.domain.model.Ged;
import org.qualihub.resume.domain.model.Resume;

import java.time.LocalDate;
import java.time.Period;

@Mapper
public interface JsonResumeMapper {
    JsonResumeMapper INSTANCE = Mappers.getMapper(JsonResumeMapper.class);

    @Mapping(source = "avatar", target = "basics.image", qualifiedByName = "imageB64")
    @Mapping(source = "birthDate", target = "basics.age", qualifiedByName = "ageConvertor")
    @Mapping(source = "email", target = "basics.email")
    @Mapping(source = "phoneNumber", target = "basics.phone")
    @Mapping(source = "aboutMe", target = "basics.summary")
    @Mapping(source = "name", target = "basics.name")
    @Mapping(source = "label", target = "basics.label")
    @Mapping(source = "experiences", target = "work")
    @Mapping(source = "educations", target = "education")
    @Mapping(source = "resumeLocale", target = "locale")
    JsonResumeDto resumeToDto(Resume resume);

    @InheritInverseConfiguration
    @Mapping(source = "basics.image", target = "avatar", ignore = true)
    @Mapping(source = "basics.age", target = "birthDate", ignore = true)
    Resume dtoToResume(JsonResumeDto dto);


    @Mapping(source = "companyName", target = "name")
    @Mapping(source = "jobLocation", target = "location")
    @Mapping(source = "title", target = "position")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "details", target = "summary")
    WorkJsonResumeDto experienceToDto(Experience experience);

    @InheritInverseConfiguration
    Experience dtoToExperience(WorkJsonResumeDto dto);

    @Mapping(source = "title", target = "area")
    @Mapping(source = "school", target = "institution")
    @Mapping(source = "diploma", target = "studyType")
    @Mapping(source = "description", target = "summary")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "startDate", target = "startDate")
    EducationJsonResumeDto educationToDto(Education education);

    @InheritInverseConfiguration
    Education dtoToEducation(EducationJsonResumeDto dto);

    @Named("ageConvertor")
    default String ageConvertor(LocalDate birthDate) {
        if (birthDate != null) {
            return "" + Period.between(birthDate, LocalDate.now(DateConfig.CLOCK)).getYears();
        } else {
            return "NA";
        }
    }

    @Named("imageB64")
    default String imageB64(Ged document) {
        if (document != null && document.getContent() != null) {
            return Base64.encodeBase64String(document.getContent());
        }
        return StringUtils.EMPTY;
    }
}
