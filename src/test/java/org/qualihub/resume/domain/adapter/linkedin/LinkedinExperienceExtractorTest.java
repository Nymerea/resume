package org.qualihub.resume.domain.adapter.linkedin;

import org.junit.jupiter.api.Test;
import org.qualihub.resume.domain.dto.jsonresume.WorkJsonResumeDto;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LinkedinExperienceExtractorTest {
    LinkedinExperienceExtractor instance = new LinkedinExperienceExtractor();


    @Test
    void extract_time_range() {
        WorkJsonResumeDto actual = new WorkJsonResumeDto();
        instance.fillTimeRange(List.of("août 2018 - janvier 2022 (3 ans 6 mois)"),0, actual);

        assertThat(actual)
                .returns(LocalDate.of(2018,8,1), WorkJsonResumeDto::getStartDate)
                .returns(LocalDate.of(2022,1,1), WorkJsonResumeDto::getEndDate);
    }
    @Test
    void can_extract_experiences() {
        String content =
                "Coordonnées\n" +
                        "morgan-durand@laposte.net\n" +
                        "www.linkedin.com/in/morgan-\n" +
                        "durand-72530375 (LinkedIn)\n" +
                        "Principales compétences\n" +
                        "Linux\n" +
                        "Java\n" +
                        "Jenkins\n" +
                        "Languages\n" +
                        "Français (Native or Bilingual)\n" +
                        "Anglais (Professional Working)\n" +
                        "Honors-Awards\n" +
                        "Meilleurs participation en équipe\n" +
                        "Morgan DURAND\n" +
                        "Ingénieur en recherche et développement logiciels chez Webhelp\n" +
                        "Payment Services\n" +
                        "Montréal\n" +
                        "Expérience\n" +
                        "abbeal\n" +
                        "Software Development Engineer\n" +
                        "janvier 2022 - Present (6 mois)\n" +
                        "Montréal, Québec, Canada\n" +
                        "Webhelp Payment Services\n" +
                        "Ingénieur en recherche et développement logiciels\n" +
                        "août 2018 - janvier 2022 (3 ans 6 mois)\n" +
                        "Région de Chambéry, France\n" +
                        "Backend technologie : Springboot 2+, kafka, Jasper, mirakl\n" +
                        "Front end : Angular 8\n" +
                        "Knowings (Groupe Everial)\n" +
                        "Ingénieur Recherche et Developpement\n" +
                        "octobre 2016 - août 2018 (1 an 11 mois)\n" +
                        "Bourget du lac\n" +
                        "Backend developpement : Spring, alfresco, solR\n" +
                        "Automation : Ansible, docker\n" +
                        "Arcad Software\n" +
                        "Alternant R&D JAVA\n" +
                        "septembre 2015 - septembre 2016 (1 an 1 mois)\n" +
                        "Annecy\n" +
                        "Création d'une solution de contrôle qualité grâce aux outils tels que sonarQube\n" +
                        "et jenkins\n" +
                        "gnúbila France\n" +
                        "Stage Developpeur Java\n" +
                        "juin 2015 - septembre 2015 (4 mois)\n" +
                        "Argonay\n" +
                        "Réalisation d'un portlet \"patient cart\". Le principe du portlet était de télécharger\n" +
                        "les données médicales d'un patient grâce à FedEHR.\n" +
                        "\n" +
                        "J'ai d'abord dû m'habituer à l’environnement de travail de l'entreprise avec le\n" +
                        "CMS JEE : Liferay\n" +
                        "Pour la RIA j'ai utiliser le framework front end vaadin (GWT based)\n" +
                        "Pour écrire un fichier xml qui représentais ces données médicale, j'ai utilisé\n" +
                        "JAXB\n" +
                        "Pour la gestion de build, je me suis familiarisé avec maven\n" +
                        "Infomaniak Network SA\n" +
                        "Web Developer\n" +
                        "juin 2014 - août 2014 (3 mois)\n" +
                        "- Maîtrise de Git et du workflow git-flow\n" +
                        "- Création d'un système de gestion des sons (acapela) pour le standard\n" +
                        "téléphonique (asterisk)\n" +
                        "- Création d'une application C++/Qt pour exporter une page Html/JavaScript\n" +
                        "en PDF\n" +
                        "- Utilisation de SonarCube pour le contrôle qualité du code source\n" +
                        "CISM\n" +
                        "Web Developer\n" +
                        "avril 2012 - mars 2013 (1 an)\n" +
                        "Annecy\n" +
                        "Développement du site web pour le Conseil International Du Sport Militaire\n" +
                        "(CISM) pour les JMMH (Jeux Mondiaux Militaire d'Hivers 2013) http://\n" +
                        "annecy2013.com\n" +
                        "Utilisation du framwork : YiiFramework\n" +
                        "conception de la base de données : Mysql\n" +
                        "Formation\n" +
                        "Dundalk Institute of Technology\n" +
                        "bachelor of honor, Commercial computing · (2014 - 2015)\n" +
                        "Université de Savoie\n" +
                        "Licence, STIC · (2013 - 2014)\n" +
                        "\n" +
                        "Saint michel\n" +
                        "BTS, IRIS · (2011 - 2013)\n" +
                        "Saint joseph\n";
        List<WorkJsonResumeDto> workJsonResumeDtos = instance.fetch(content);
        assertThat(workJsonResumeDtos).isNotEmpty()
                .extracting(WorkJsonResumeDto::getName)
                .containsExactlyInAnyOrder("abbeal",
                        "Webhelp Payment Services",
                        "Knowings (Groupe Everial)",
                        "Arcad Software",
                        "gnúbila France",
                        "Infomaniak Network SA",
                        "CISM");


        assertThat(workJsonResumeDtos)
                .extracting(WorkJsonResumeDto::getPosition)
                .containsExactlyInAnyOrder("Software Development Engineer",
                        "Ingénieur en recherche et développement logiciels",
                        "Ingénieur Recherche et Developpement",
                        "Alternant R&D JAVA",
                        "Stage Developpeur Java",
                        "Web Developer",
                        "Web Developer");
        assertThat(workJsonResumeDtos)
                .extracting(WorkJsonResumeDto::getLocation)
                .containsExactlyInAnyOrder("Montréal, Québec, Canada",
                        "Région de Chambéry, France",
                        "Bourget du lac",
                        "Annecy",
                        "Argonay",
                        // it's a mistake because there is no location set
                        "- Maîtrise de Git et du workflow git-flow",
                        "Annecy");

        assertThat(workJsonResumeDtos.get(0).getSummary()).isEqualTo("");
        assertThat(workJsonResumeDtos.get(1).getSummary()).isEqualTo("Backend technologie : Springboot 2+, kafka, Jasper, mirakl\n" +
                "Front end : Angular 8");
        assertThat(workJsonResumeDtos.get(2).getSummary()).isEqualTo("Backend developpement : Spring, alfresco, solR\n" +
                "Automation : Ansible, docker");
        assertThat(workJsonResumeDtos.get(3).getSummary()).isEqualTo("Création d'une solution de contrôle qualité grâce aux outils tels que sonarQube\n" +
                "et jenkins");
        assertThat(workJsonResumeDtos.get(4).getSummary()).isEqualTo("Réalisation d'un portlet \"patient cart\". Le principe du portlet était de télécharger\n" +
                "les données médicales d'un patient grâce à FedEHR.\n" +
                "J'ai d'abord dû m'habituer à l’environnement de travail de l'entreprise avec le\n" +
                "CMS JEE : Liferay\n" +
                "Pour la RIA j'ai utiliser le framework front end vaadin (GWT based)\n" +
                "Pour écrire un fichier xml qui représentais ces données médicale, j'ai utilisé\n" +
                "JAXB\n" +
                "Pour la gestion de build, je me suis familiarisé avec maven");
        assertThat(workJsonResumeDtos.get(5).getSummary()).isEqualTo("- Création d'un système de gestion des sons (acapela) pour le standard\n" +
                "téléphonique (asterisk)\n" +
                "- Création d'une application C++/Qt pour exporter une page Html/JavaScript\n" +
                "en PDF\n" +
                "- Utilisation de SonarCube pour le contrôle qualité du code source");
        assertThat(workJsonResumeDtos.get(6).getSummary()).isEqualTo("Développement du site web pour le Conseil International Du Sport Militaire\n" +
                "(CISM) pour les JMMH (Jeux Mondiaux Militaire d'Hivers 2013) http://\n" +
                "annecy2013.com\n" +
                "Utilisation du framwork : YiiFramework\n" +
                "conception de la base de données : Mysql");

    }
}