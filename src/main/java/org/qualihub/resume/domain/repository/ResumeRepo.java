package org.qualihub.resume.domain.repository;

import org.qualihub.resume.domain.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepo extends JpaRepository<Resume, Long> {

}
