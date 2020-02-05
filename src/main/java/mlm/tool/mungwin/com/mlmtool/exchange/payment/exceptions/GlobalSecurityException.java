package mlm.tool.mungwin.com.mlmtool.exchange.payment.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * @author nouks
 *
 * @date 23 Oct 2019
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GlobalSecurityException extends RuntimeException {
    private HttpStatus httpStatus;
    private String errorCode;
    private String devHint;

    public GlobalSecurityException(String message, HttpStatus httpStatus, String errorCode, String devHint) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.devHint = devHint;
    }
}
