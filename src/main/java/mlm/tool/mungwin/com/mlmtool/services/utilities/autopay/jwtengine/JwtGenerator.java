package mlm.tool.mungwin.com.mlmtool.services.utilities.autopay.jwtengine;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Map;
@Service
public class JwtGenerator {
    private JwtKeyStore jwtKeyStore;

    @Autowired
    public JwtGenerator(JwtKeyStore jwtKeyStore) {
        this.jwtKeyStore = jwtKeyStore;
    }

    public String generateToken(JwtToken jwtToken){
        String token = null;

        Map<String, Object> header;
        Map<String, Object> payLoad;
        try {
            payLoad = jwtToken.getPayload();
            header = jwtToken.getHeader();
            PrivateKey privateKey = jwtKeyStore.getPrivateKey();
            token = Jwts.builder()
                    .setClaims(payLoad)
                    .setHeader(header)
                    .signWith(SignatureAlgorithm.RS256, privateKey).compact();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
}
