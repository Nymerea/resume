package org.qualihub.resume.domain.adapter.linkedin;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

class LinkedinEmailExtractorTest {

    LinkedinEmailExtractor instance = new LinkedinEmailExtractor();

    @Test
    void import_french_resume() throws IOException {
        String email = instance.findEmail("Coordonnées\n" +
                "morgan-durand@laposte.net\n" +
                "www.linkedin.com/in/morgan-\n" +
                "durand-72530375 (LinkedIn)\n" +
                "Principales compétences\n" +
                "Linux\n" +
                "Java");
        Assertions.assertThat(email).isEqualTo("morgan-durand@laposte.net");

    }

}