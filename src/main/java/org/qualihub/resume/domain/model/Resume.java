package org.qualihub.resume.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hibernate.annotations.CascadeType.ALL;

@Entity
@Getter
@Setter
@FieldNameConstants
public class Resume extends AbstractEntity {


    @Column(length = 40)
    String uuid;

    @Column
    String email;

    @Column
    String name;

    @Column
    String phoneNumber;

    @Column
    String address;

    @Column
    LocalDate birthDate;

    @Column
    String aboutMe;

    @Column
    String label;

    @Column
    Locale resumeLocale;

    @OneToMany
    @Cascade(value = ALL)
    @JoinColumn(name = "resume_id")

    List<Education> educations = new ArrayList<>();
    @OneToMany
    @Cascade(value = ALL)
    @JoinColumn(name = "resume_id")
    List<Experience> experiences = new ArrayList<>();

    @OneToMany
    @Cascade(value = ALL)
    @JoinColumn(name = "resume_id")
    List<Skill> skills = new ArrayList<>();

    @OneToOne
    @Cascade(value = ALL)
    Ged avatar;

    @OneToOne
    @Cascade(value = ALL)
    Ged importedFrom;

}
