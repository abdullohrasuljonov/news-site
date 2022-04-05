package uz.pdp.newssite.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@ResponseStatus(HttpStatus.FORBIDDEN)
@Data
public class ForbiddenExceptions extends RuntimeException{

    private String type;
    private String message;

    public ForbiddenExceptions(String type, String message) {
        this.type = type;
        this.message = message;
    }
}
