package mlm.tool.mungwin.com.mlmtool.services.utilities.autopay.jwtengine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * author nouks
 */
@Service(value = "autoBonusJwt")
public class JWTServiceImpl implements JWTService{
    JwtGenerator jwtGenerator;

    public JWTServiceImpl() {
    }

    @Autowired
    public void setJwtGenerator(JwtGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public String generateSignedToken() {
        JwtToken jwtToken = new JwtToken();
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "RS256");
        header.put("typ", "JWT");
        jwtToken.setHeader(header);
        Map<String, Object> payLoad = new HashMap<>();
        Long iat = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(1));
        Long exp = iat + 60000L;
        payLoad.put("iat", iat);
        payLoad.put("exp", exp);
        payLoad.put("user_name", "mungwin");
        payLoad.put("scope", Arrays.asList("read", "write"));
        payLoad.put("aud", Collections.singletonList("mungwin"));
        payLoad.put("uuid", "8fe97e51-d054-4328-9ee7-ed12b41c881a");
        payLoad.put("jti",  UUID.randomUUID().toString());
        payLoad.put("client_id", "MGApp1");
        payLoad.put("authorities", Collections.singletonList("SYSTEM"));
        jwtToken.setPayload(payLoad);
        return jwtGenerator.generateToken(jwtToken);
    }
}
