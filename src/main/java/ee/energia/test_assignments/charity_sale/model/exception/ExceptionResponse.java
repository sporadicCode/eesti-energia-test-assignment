package ee.energia.test_assignments.charity_sale.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExceptionResponse {
    private final String code;
    private final String message;
}
