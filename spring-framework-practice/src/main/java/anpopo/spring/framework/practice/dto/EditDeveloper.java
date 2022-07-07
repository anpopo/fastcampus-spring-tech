package anpopo.spring.framework.practice.dto;

import anpopo.spring.framework.practice.entity.Developer;
import anpopo.spring.framework.practice.type.DeveloperLevel;
import anpopo.spring.framework.practice.type.DeveloperSkillType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class EditDeveloper {
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
    }
}
