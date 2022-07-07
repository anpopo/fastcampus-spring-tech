package anpopo.spring.framework.practice.service;

import anpopo.spring.framework.practice.dto.CreateDeveloper;
import anpopo.spring.framework.practice.entity.Developer;
import anpopo.spring.framework.practice.exception.DMakerErrorCode;
import anpopo.spring.framework.practice.exception.DMakerException;
import anpopo.spring.framework.practice.repository.DeveloperRepository;
import anpopo.spring.framework.practice.type.DeveloperLevel;
import anpopo.spring.framework.practice.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.Optional;

import static anpopo.spring.framework.practice.exception.DMakerErrorCode.*;

/**
 * @author anpopo
 */

@RequiredArgsConstructor
@Service
public class DMakerService {
    private final DeveloperRepository developerRepository;

    /**
     * ACID
     * Atomic - 원자성 - 하나의 작업
     * Consistency - 일관성 - 트랜젝션 종료후 데이터 정합
     * Isolation - 고립성 - 데이터 베이스의 데이터를 고립시켜 사용
     * Durability - 지속성 - 모든 이력을 남기고 그것을 기반으로 데이터베이스 메모리에 작성
     */
    @Transactional
    public void createDeveloper(CreateDeveloper.Request request) {

        validateCreateDeveloperRequest(request);
        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .name(request.getName())
                .age(request.getAge())
                .memberId(request.getMemberId())
                .build();

        developerRepository.save(developer);

    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        // business validation

        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienceYears = request.getExperienceYears();

        if (developerLevel == DeveloperLevel.SENIOR && experienceYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCH);
        }

        if (developerLevel == DeveloperLevel.JUNGNIOR && (experienceYears < 4 || experienceYears > 10)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCH);
        }

        if (developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCH);
        }

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer) -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                });

    }

}
