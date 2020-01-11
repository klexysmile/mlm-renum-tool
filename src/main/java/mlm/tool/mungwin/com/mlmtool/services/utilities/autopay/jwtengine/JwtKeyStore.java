package mlm.tool.mungwin.com.mlmtool.services.utilities.autopay.jwtengine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
@Component
public class JwtKeyStore {
    @Value("${customer.service.security.auto-bonus-keystore}")
    private String PRIVATE_KEY_STORE;

    PrivateKey getPrivateKey() throws Exception {
        File file = new File(PRIVATE_KEY_STORE);
        InputStream inputStream = new FileInputStream(file);
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        keyStore.load(inputStream, "mgtest@1".toCharArray());
        KeyStore.PasswordProtection keyPassword = new KeyStore.PasswordProtection("mgtest@1".toCharArray());
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("servercert", keyPassword);
        return privateKeyEntry.getPrivateKey();
    }
}