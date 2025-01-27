package pl.radgor144.inbound;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.radgor144.swiftcode.createswiftcode.SwiftCodeExistException;
import pl.radgor144.swiftcode.swiftcodebycountryiso2code.CountryISO2CodeNotFoundException;
import pl.radgor144.swiftcode.swiftcodebyswiftcode.SwiftCodeNotFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ExceptionResponse handleRuntimeException(RuntimeException runtimeException) {
        log.error("Unexpected exception occurred: {}", runtimeException.getMessage(), runtimeException);
        return new ExceptionResponse(runtimeException.getMessage());
    }
    @ExceptionHandler(SwiftCodeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ExceptionResponse handleSwiftCodeNotFoundException(SwiftCodeNotFoundException swiftCodeNotFoundException) {
        log.error("SwiftCodeNotFoundException occurred", swiftCodeNotFoundException);
        return new ExceptionResponse(swiftCodeNotFoundException.getMessage());
    }

    @ExceptionHandler(CountryISO2CodeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ExceptionResponse handleCountryISO2CodeNotFoundException(CountryISO2CodeNotFoundException countryISO2CodeNotFoundException) {
        log.error("CountryISO2CodeNotFoundException occurred", countryISO2CodeNotFoundException);
        return new ExceptionResponse(countryISO2CodeNotFoundException.getMessage());
    }

    @ExceptionHandler(SwiftCodeExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ExceptionResponse handleSwiftCodeExistException(SwiftCodeExistException swiftCodeExistException) {
        log.error("SwiftCodeExistException occurred", swiftCodeExistException);
        return new ExceptionResponse(swiftCodeExistException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ExceptionResponse handleSwiftCodeExistException(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("MethodArgumentNotValidException occurred", methodArgumentNotValidException);
        final String errorDetails = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> "\n" + fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining());
        return new ExceptionResponse("Validation error:" + errorDetails);
    }
}
