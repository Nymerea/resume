package org.qualihub.resume.domain.model;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Data
@FieldNameConstants
public class Education extends AbstractEntity {
    @Column
    String school;
    @Column
    String title;
    @Column
    String diploma;
    @Column(length = 1024)
    String description;
    @Column
    LocalDate startDate;
    @Column
    LocalDate endDate;
}
