package mlm.tool.mungwin.com.mlmtool.services.utilities.autopay.jwtengine;

public interface JWTService {
    String generateSignedToken(); // add parameter Map<String, Object> payload if necessary
}
