package anpopo.spring.framework.practice.service;

import anpopo.spring.framework.practice.code.StatusCode;
import anpopo.spring.framework.practice.dto.CreateDeveloper;
import anpopo.spring.framework.practice.dto.DeveloperDetailDto;
import anpopo.spring.framework.practice.entity.Developer;
import anpopo.spring.framework.practice.exception.DMakerErrorCode;
import anpopo.spring.framework.practice.exception.DMakerException;
import anpopo.spring.framework.practice.repository.DeveloperRepository;
import anpopo.spring.framework.practice.repository.RetiredDeveloperRepository;
import anpopo.spring.framework.practice.type.DeveloperLevel;
import anpopo.spring.framework.practice.type.DeveloperSkillType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    @InjectMocks
    private DMakerService dMakerService;

    private final Developer developer = Developer.builder()
            .developerLevel(DeveloperLevel.SENIOR)
            .developerSkillType(DeveloperSkillType.BACK_END)
            .experienceYears(12)
            .statusCode(StatusCode.EMPLOYED)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .memberId("memberId")
            .name("name")
            .age(32).build();

    private final CreateDeveloper.Request developerRequest = CreateDeveloper.Request.builder()
            .developerLevel(DeveloperLevel.SENIOR)
            .developerSkillType(DeveloperSkillType.BACK_END)
            .experienceYears(12)
            .memberId("memberId")
            .name("name")
            .age(32)
            .build();

    @Test
    void test() {
        BDDMockito.given(developerRepository.findByMemberId(ArgumentMatchers.anyString()))
                .willReturn(Optional.of(developer));
        DeveloperDetailDto developerDetail = dMakerService.getDeveloperDetail("memberId");

        Assertions.assertEquals(developerDetail.getName(), "name");
        Assertions.assertEquals(developerDetail.getAge(), 32);
        Assertions.assertEquals(developerDetail.getExperienceYears(), 12);
        Assertions.assertEquals(DeveloperLevel.SENIOR, developerDetail.getDeveloperLevel());

    }

    @DisplayName("성공 - 개발자를 생성한다.")
    @Test
    void createDeveloperTest_success() {
        // given
        BDDMockito.given(developerRepository.findByMemberId(ArgumentMatchers.anyString()))
                .willReturn(Optional.empty());


        BDDMockito.given(developerRepository.save(ArgumentMatchers.any(Developer.class)))
                .willReturn(developer);

        ArgumentCaptor<Developer> captor = ArgumentCaptor.forClass(Developer.class);

        // when
        CreateDeveloper.Response developer = dMakerService.createDeveloper(developerRequest);


        // then
        Mockito.verify(developerRepository, Mockito.times(1))
                .save(captor.capture());

        Developer savedDeveloper = captor.getValue();

        org.assertj.core.api.Assertions.assertThat(savedDeveloper.getDeveloperLevel()).isEqualTo(DeveloperLevel.SENIOR);
        org.assertj.core.api.Assertions.assertThat(savedDeveloper.getDeveloperSkillType()).isEqualTo(DeveloperSkillType.BACK_END);
        org.assertj.core.api.Assertions.assertThat(savedDeveloper.getMemberId()).isEqualTo("memberId");
        org.assertj.core.api.Assertions.assertThat(savedDeveloper.getAge()).isEqualTo(32);
    }


    @DisplayName("실패 - 개발자를 생성한다.")
    @Test
    void createDeveloperTest_validation() {
        // given

        BDDMockito.given(developerRepository.findByMemberId(ArgumentMatchers.anyString()))
                .willReturn(Optional.of(developer));


        // when
        // then
        DMakerException dMakerException = Assertions.assertThrows(DMakerException.class, () -> dMakerService.createDeveloper(developerRequest));

        Assertions.assertEquals(DMakerErrorCode.DUPLICATED_MEMBER_ID, dMakerException.getDMakerErrorCode());
    }


}