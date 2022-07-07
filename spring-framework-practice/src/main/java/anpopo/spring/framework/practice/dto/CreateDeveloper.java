package anpopo.spring.framework.practice.dto;


import anpopo.spring.framework.practice.type.DeveloperLevel;
import anpopo.spring.framework.practice.type.DeveloperSkillType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateDeveloper {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request {
        @NotNull
        private DeveloperLevel developerLevel;

        @NotNull
        private DeveloperSkillType developerSkillType;

        @NotNull @Min(0) @Max(50)
        private Integer experienceYears;

        @NotNull
        @Size(min = 3, max = 20, message = "name size must 3 to 20")
        private String name;

        @NotNull
        @Size(min = 3, max = 50, message = "memberId size must 3 to 50")
        private String memberId;


        @Min(99)
        private Integer age;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response {
        private DeveloperLevel developerLevel;
        private DeveloperSkillType developerSkillType;
        private Integer experienceYears;

        private String name;
        private String memberId;
        private Integer age;
    }

}
