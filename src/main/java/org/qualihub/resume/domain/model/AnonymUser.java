package org.qualihub.resume.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class AnonymUser {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Long id;

    @Column
    String email;
}
