package org.qualihub.resume.domain.dto.jsonresume;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class BasicsJsonResumeDto {
    @ToString.Exclude
    String image;
    String name;
    String label;

    String email;
    String phone;
    String url;
    String summary;

    // not in standard
    String age;

}
