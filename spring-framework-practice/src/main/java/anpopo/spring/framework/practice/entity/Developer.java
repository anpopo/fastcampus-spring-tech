package anpopo.spring.framework.practice.entity;

import anpopo.spring.framework.practice.code.StatusCode;
import anpopo.spring.framework.practice.type.DeveloperLevel;
import anpopo.spring.framework.practice.type.DeveloperSkillType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author anpopo
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Developer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Enumerated(EnumType.STRING)
    private DeveloperLevel developerLevel;

    @Enumerated(EnumType.STRING)
    private DeveloperSkillType developerSkillType;
    @Column
    private Integer experienceYears;

    @Column
    private String memberId;

    @Column
    private String name;

    @Column
    private Integer age;

    @Enumerated(EnumType.STRING)
    private StatusCode statusCode;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void updateDeveloperFromRequest(DeveloperLevel developerLevel, DeveloperSkillType developerSkillType, Integer experienceYears) {
        this.developerLevel = developerLevel;
        this.developerSkillType = developerSkillType;
        this.experienceYears = experienceYears;
    }

    public void updateStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
