package ee.energia.test_assignments.charity_sale.advice;

import ee.energia.test_assignments.charity_sale.exception.NotEnoughCashException;
import ee.energia.test_assignments.charity_sale.exception.NotEnoughStockException;
import ee.energia.test_assignments.charity_sale.exception.ProductNotFoundException;
import ee.energia.test_assignments.charity_sale.model.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(NotEnoughCashException.class)
    public ResponseEntity<ExceptionResponse> onNotEnoughCashException(NotEnoughCashException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse("NOT_ENOUGH_CASH", e.getMessage()));
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<ExceptionResponse> onNotEnoughStockException(NotEnoughStockException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse("NOT_ENOUGH_STOCK", e.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> onProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse("PRODUCT_NOT_FOUND", e.getMessage()));
    }
}
