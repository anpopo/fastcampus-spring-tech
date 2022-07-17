package anpopo.spring.framework.practice.type;

import anpopo.spring.framework.practice.exception.DMakerException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

import static anpopo.spring.framework.practice.exception.DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCH;

/**
 * @author anpopo
 */

@Getter
@AllArgsConstructor
public enum DeveloperLevel {

    NEW("신입 개발자", years -> years == 0),
    JUNIOR("주니어 개발자", years -> years < 5),
    JUNGNIOR("중니어 개발자", years -> years >= 5 && years < 10),
    SENIOR("시니어 개발자", years -> years > 10),
    ;
    private final String description;
    private final Function<Integer, Boolean> validateFunction;

    public void validateExperienceYears(int experienceYear) {
        if (validateFunction.apply(experienceYear)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEARS_NOT_MATCH);
        }
    }

}
