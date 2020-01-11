package mlm.tool.mungwin.com.mlmtool.exceptions;
/**
 * @author nouks
 *
 * @date 23 Oct 2019
 */
public enum SecurityErrorCodes {
    ACCESS_DENIED,
    UNAUTHORIZED,
    NETWORK_ERROR,
    EMAIL_USED,
    USERNAME_USED,
    FAILED_TO_PARSE_JWT,
    FAILED_TO_DECODE_JWT,
    PASSWORD_NOT_CONFIRMED,
    FAILED_TO_DECODE_HTTPCLIENT_EXCEPTION,
}
