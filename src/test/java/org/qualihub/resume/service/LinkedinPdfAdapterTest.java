package org.qualihub.resume.service;

import org.junit.jupiter.api.Test;
import org.qualihub.resume.domain.adapter.linkedin.LinkedinPdfAdapter;
import org.qualihub.resume.domain.dto.jsonresume.EducationJsonResumeDto;
import org.qualihub.resume.domain.dto.jsonresume.JsonResumeDto;
import org.qualihub.resume.domain.dto.jsonresume.WorkJsonResumeDto;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LinkedinPdfAdapterTest {
    LinkedinPdfAdapter instance = new LinkedinPdfAdapter();

    @Test
    void import_french_resume() throws IOException {
        File file = new File("src/test/resources/Profile.pdf");
        JsonResumeDto jsonResumeDto = instance.fromFile(file);

        List<WorkJsonResumeDto> workJsonResumeDtos = jsonResumeDto.getWork();
        assertThat(workJsonResumeDtos).isNotEmpty()
                .extracting(WorkJsonResumeDto::getName)
                .containsExactlyInAnyOrder("abbeal",
                        "Banque Nationale du Canada",
                        "Webhelp Payment Services",
                        "Knowings (Groupe Everial)",
                        "Arcad Software",
                        "gnúbila France",
                        "Infomaniak Network SA",
                        "CISM");


        assertThat(workJsonResumeDtos)
                .extracting(WorkJsonResumeDto::getPosition)
                .containsExactlyInAnyOrder("Senior Software Development Engineer",
                        "Senior developper",
                        "Ingénieur en recherche et développement logiciels",
                        "Ingénieur Recherche et Developpement",
                        "Alternant R&D JAVA",
                        "Stage Developpeur Java",
                        "Web Developer",
                        "Web Developer");
        assertThat(workJsonResumeDtos)
                .extracting(WorkJsonResumeDto::getLocation)
                .containsExactlyInAnyOrder("Montréal, Québec, Canada",
                        "Montréal, Québec, Canada",
                        "Région de Chambéry, France",
                        "Bourget du lac",
                        "Annecy",
                        "Argonay",
                        // it's a mistake because there is no location set
                        "- Maîtrise de Git et du workflow git-flow",
                        "Annecy");

        assertThat(workJsonResumeDtos.get(0).getSummary()).isEqualTo("");
        assertThat(workJsonResumeDtos.get(1).getSummary()).isEqualTo("");
        assertThat(workJsonResumeDtos.get(2).getSummary()).isEqualTo("Backend technologie : Springboot 2+, kafka, Jasper, mirakl\n" +
                "Front end : Angular 8");
        assertThat(workJsonResumeDtos.get(3).getSummary()).isEqualTo("Backend developpement : Spring, alfresco, solR\n" +
                "Automation : Ansible, docker");
        assertThat(workJsonResumeDtos.get(4).getSummary()).isEqualTo("Création d'une solution de contrôle qualité grâce aux outils tels que sonarQube\n" +
                "et jenkins");
        assertThat(workJsonResumeDtos.get(5).getSummary()).isEqualTo("Réalisation d'un portlet \"patient cart\". Le principe du portlet était de télécharger\n" +
                "les données médicales d'un patient grâce à FedEHR.\n" +
                "J'ai d'abord dû m'habituer à l’environnement de travail de l'entreprise avec le\n" +
                "CMS JEE : Liferay\n" +
                "Pour la RIA j'ai utiliser le framework front end vaadin (GWT based)\n" +
                "Pour écrire un fichier xml qui représentais ces données médicale, j'ai utilisé\n" +
                "JAXB\n" +
                "Pour la gestion de build, je me suis familiarisé avec maven");
        assertThat(workJsonResumeDtos.get(6).getSummary()).isEqualTo("- Création d'un système de gestion des sons (acapela) pour le standard\n" +
                "téléphonique (asterisk)\n" +
                "- Création d'une application C++/Qt pour exporter une page Html/JavaScript\n" +
                "en PDF\n" +
                "- Utilisation de SonarCube pour le contrôle qualité du code source");
        assertThat(workJsonResumeDtos.get(7).getSummary()).isEqualTo("Développement du site web pour le Conseil International Du Sport Militaire\n" +
                "(CISM) pour les JMMH (Jeux Mondiaux Militaire d'Hivers 2013) http://\n" +
                "annecy2013.com\n" +
                "Utilisation du framwork : YiiFramework\n" +
                "conception de la base de données : Mysql");

        assertThat(workJsonResumeDtos)
                .extracting(WorkJsonResumeDto::getStartDate)
                .containsExactlyInAnyOrder(LocalDate.of(2022,1,1),
                        LocalDate.of(2022,1,1),
                        LocalDate.of(2018,8,1),
                        LocalDate.of(2016,10,1),
                        LocalDate.of(2015,9,1),
                        LocalDate.of(2015,6,1),
                        LocalDate.of(2014,6,1),
                        LocalDate.of(2012,4,1));
        assertThat(workJsonResumeDtos)
                .extracting(WorkJsonResumeDto::getEndDate)
                .containsExactlyInAnyOrder(LocalDate.of(2022,1,1),
                        LocalDate.of(2022,1,1),
                        LocalDate.of(2022,1,1),
                        LocalDate.of(2018,8,1),
                        LocalDate.of(2016,9,1),
                        LocalDate.of(2014,8,1),
                        LocalDate.of(2013,3,1));

        var educations = jsonResumeDto.getEducation();
        assertThat(educations).isNotEmpty();
        assertThat(educations)
                .extracting(EducationJsonResumeDto::getArea)
                .containsExactlyInAnyOrder("Commercial computing", "STIC", "IRIS");

        assertThat(educations)
                .extracting(EducationJsonResumeDto::getInstitution)
                .containsExactlyInAnyOrder("Dundalk Institute of Technology", "Université de Savoie", "Saint michel");

        assertThat(educations)
                .extracting(EducationJsonResumeDto::getStartDate)
                .containsExactlyInAnyOrder(LocalDate.of(2014,01,01),
                        LocalDate.of(2013,01,01),
                        LocalDate.of(2011,01,01));
        assertThat(educations)
                .extracting(EducationJsonResumeDto::getEndDate)
                .containsExactlyInAnyOrder(LocalDate.of(2015,01,01),
                        LocalDate.of(2014,01,01),
                        LocalDate.of(2013,01,01));

    }
}