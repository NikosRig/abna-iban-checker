package nl.abna.ibanchecker.infrastructure.gateway;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;

public class AbnaMutualTlsUtil {
    public static SSLContext createSSLContext(InputStream pkcs12Stream, String pkcsPassword) throws Exception {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(pkcs12Stream, pkcsPassword.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, "".toCharArray());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(),null,null);

            return sslContext;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
