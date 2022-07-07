package anpopo.spring.framework.practice.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DMakerErrorCode {
    NO_DEVELOPER("해당 되는 개발자가 존재하지 않습니다."),
    DUPLICATED_MEMBER_ID("중복된 MemberId 입니다."),
    LEVEL_EXPERIENCE_YEARS_NOT_MATCH("개발자 레벨과 연차가 맞지 않습니다."),


    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다."),

    INVALID_REQUEST("잘못된 요청입니다."),
    ;

    private final String message;
}
