package org.qualihub.resume.domain.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@FieldNameConstants
public class Experience extends AbstractEntity {

    @Column
    String title;
    @Column
    String companyName;
    @Column
    String jobLocation;
    @Column(length = 2000)
    String details;

    @Column
    LocalDate startDate;
    @Column
    LocalDate endDate;
}
