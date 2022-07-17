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
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static anpopo.spring.framework.practice.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static anpopo.spring.framework.practice.exception.DMakerErrorCode.NO_DEVELOPER;

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
     */
    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {

        validateCreateDeveloperRequest(request);

        return CreateDeveloper.Response.fromEntity(developerRepository.save(createDeveloperFromRequest(request)));
    }


    @Transactional(readOnly = true)
    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAllByStatusCode(StatusCode.EMPLOYED)
                .stream()
                .map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DeveloperDetailDto getDeveloperDetail(String memberId) {
        return DeveloperDetailDto.fromEntity(getDeveloperByMemberId(memberId));
    }

    @Transactional
    public DeveloperDetailDto updateDeveloper(String memberId, EditDeveloper.Request request) {

        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

        Developer developer = getDeveloperByMemberId(memberId);

        developer.updateDeveloperFromRequest(request.getDeveloperLevel(), request.getDeveloperSkillType(), request.getExperienceYears());

        return DeveloperDetailDto.fromEntity(developer);
    }


    @Transactional
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        Developer developer = getDeveloperByMemberId(memberId);

        developer.updateStatusCode(StatusCode.RETIED);

        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(developer.getMemberId())
                .name(developer.getName())
                .build();

        retiredDeveloperRepository.save(retiredDeveloper);
        return DeveloperDetailDto.fromEntity(developer);
    }

    // -------------------------- PRIVATE METHOD --------------------------
    private Developer getDeveloperByMemberId(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .orElseThrow(() -> {
                    throw new DMakerException(NO_DEVELOPER);
                });
    }

    private void validateCreateDeveloperRequest(@NotNull CreateDeveloper.Request request) {
        // business validation
        validateDeveloperLevel(request.getDeveloperLevel(), request.getExperienceYears());

        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent((developer) -> {
                    throw new DMakerException(DUPLICATED_MEMBER_ID);
                });

    }

    private void validateDeveloperLevel(DeveloperLevel developerLevel, Integer experienceYears) {
        developerLevel.validateExperienceYears(experienceYears);
    }

    private Developer createDeveloperFromRequest(CreateDeveloper.Request request) {
        return Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYears(request.getExperienceYears())
                .name(request.getName())
                .age(request.getAge())
                .memberId(request.getMemberId())
                .statusCode(StatusCode.EMPLOYED)
                .build();
    }

}
