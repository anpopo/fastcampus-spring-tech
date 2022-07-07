package anpopo.spring.framework.practice.dto;

import anpopo.spring.framework.practice.entity.Developer;
import anpopo.spring.framework.practice.type.DeveloperLevel;
import anpopo.spring.framework.practice.type.DeveloperSkillType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDto {

    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private String memberId;


    public static DeveloperDto fromEntity(Developer developer) {
        return DeveloperDto.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .memberId(developer.getMemberId())
                .build();
    }
}
