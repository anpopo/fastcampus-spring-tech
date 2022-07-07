package anpopo.spring.framework.practice.dto;

import anpopo.spring.framework.practice.exception.DMakerErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DMakerErrorResponse {

    private DMakerErrorCode errorCode;
    private String errorMessage;
}
