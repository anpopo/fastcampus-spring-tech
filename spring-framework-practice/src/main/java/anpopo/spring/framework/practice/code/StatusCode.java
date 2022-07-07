package anpopo.spring.framework.practice.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusCode {

    EMPLOYED("고용"),
    RETIED("퇴직"),
    ;

    private final String description;
}
