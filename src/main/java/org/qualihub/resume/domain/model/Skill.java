package org.qualihub.resume.domain.model;

import lombok.Data;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@FieldNameConstants

public class Skill extends AbstractEntity {


    @Column
    String title;

    @Column
    Integer percent;
}
