package pl.edu.uj.ii.smartdom.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import pl.edu.uj.ii.smartdom.Constants;

/**
 * Created by Mohru on 10.08.2017.
 */

public class SSLUtils {

    private static SSLContext sslContext;
    private static X509TrustManager trustManager;

    public static void initSSLConfig(Context context) {
        try {
            AssetManager assetManager = context.getResources().getAssets();
            InputStream inputStream = assetManager.open(Constants.CERTIFICATE_FILE_NAME);

            CertificateFactory factory = CertificateFactory.getInstance("X.509");   // jedyny dosteny typ into: https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html
            InputStream certInput = new BufferedInputStream(inputStream);
            Certificate cert = factory.generateCertificate(certInput);

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", cert);

            String trustManagerAlg = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustFactory = TrustManagerFactory.getInstance(trustManagerAlg);
            trustFactory.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustFactory.getTrustManagers(), null);

            TrustManager[] trustManagers = trustFactory.getTrustManagers();
            if (trustManagers.length == 1 && (trustManagers[0] instanceof X509TrustManager)) {
                SSLUtils.sslContext = sslContext;
                SSLUtils.trustManager = (X509TrustManager) trustManagers[0];
            }

        } catch (CertificateException | NoSuchAlgorithmException | KeyManagementException | KeyStoreException | IOException e) {
            e.printStackTrace();
        }
    }

    public static SSLContext getSslContext() {
        return sslContext;
    }

    public static X509TrustManager getTrustManager() {
        return trustManager;
    }
}
