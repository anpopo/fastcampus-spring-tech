package anpopo.spring.framework.practice.service;

import anpopo.spring.framework.practice.code.StatusCode;
import anpopo.spring.framework.practice.dto.CreateDeveloper;
import anpopo.spring.framework.practice.dto.DeveloperDetailDto;
import anpopo.spring.framework.practice.dto.DeveloperDto;
import anpopo.spring.framework.practice.dto.EditDeveloper;
import anpopo.spring.framework.practice.entity.Developer;
import anpopo.spring.framework.practice.entity.RetiredDeveloper;
import anpopo.spring.framework.practice.exception.DMakerException;
import anpopo.spring.framework.practice.repository.DeveloperRepository;
import anpopo.spring.framework.practice.repository.RetiredDeveloperRepository;
import anpopo.spring.framework.practice.type.DeveloperLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static anpopo.spring.framework.practice.exception.DMakerErrorCode.*;

/**
 * @author anpopo
 */

@RequiredArgsConstructor
@Service
public class DMakerService {
    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;

    /**
     * ACID
     * Atomic - 원자성 - 하나의 작업
     * Consistency - 일관성 - 트랜젝션 종료후 데이터 정합
     * Isolation - 고립성 - 데이터 베이스의 데이터를 고립시켜 사용
     * Durability - 지속성 - 모든 이력을 남기고 그것을 기반으로 데이터베이스 메모리에 작성
     *
     * @return
     */
    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {

        validateCreateDeveloperRequest(request);
        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .name(request.getName())
                .age(request.getAge())
                .memberId(request.getMemberId())
                .statusCode(StatusCode.EMPLOYED)
                .build();

        Developer savedDeveloper = developerRepository.save(developer);

        return CreateDeveloper.Response.fromEntity(savedDeveloper);
    }

    

    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAllByStatusCode(StatusCode.EMPLOYED)
                .stream()
                .map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(() -> new DMakerException(NO_DEVELOPER));
    }

    @Transactional
    public DeveloperDetailDto updateDeveloper(String memberId, EditDeveloper.Request request) {

        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> {
                    throw new DMakerException(NO_DEVELOPER);
                });

        developer.updateDeveloper(request.getDeveloperLevel(), request.getDeveloperSkillType(), request.getExperienceYears());
        return DeveloperDetailDto.fromEntity(developer);
    }

    private void validateCreateDeveloperRequest(CreateDeveloper.Request request) {
        // business validation
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer) -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                });

    }

    private void validateDeveloperLevel(DeveloperLevel developerLevel, Integer experienceYears) {
        if (developerLevel == DeveloperLevel.SENIOR && experienceYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCH);
        }

        if (developerLevel == DeveloperLevel.JUNGNIOR && (experienceYears < 4 || experienceYears > 10)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCH);
        }

        if (developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCH);
        }

        if (developerLevel == DeveloperLevel.NEW && experienceYears > 0) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCH);
        }
    }

    @Transactional
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> {
                    throw new DMakerException(NO_DEVELOPER);
                });

        developer.updateStatusCode(StatusCode.RETIED);

        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(developer.getMemberId())
                .name(developer.getName())
                .build();

        retiredDeveloperRepository.save(retiredDeveloper);
        return DeveloperDetailDto.fromEntity(developer);
    }

}
