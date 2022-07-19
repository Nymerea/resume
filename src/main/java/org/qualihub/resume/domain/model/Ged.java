package org.qualihub.resume.domain.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Ged extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    @Column
    type type;
    @Column
    String fileName;
    @Lob
    byte[] content;

    public enum type {
        PDF_LINKEDIN, AVATAR;
    }
}
