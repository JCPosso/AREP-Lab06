package co.edu.escuelaing.arep.app;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * URLReader se encarga de leer la URL de la llave ya creada para ingresar a HTTPS.
 * @author  Juan Camilo Posso (JCPosso)
 * @version del 20/09/21 (1.0)
 */
public class URLReader {

    public static void main() {
        try {
            // Create a file and a password representation
            File trustStoreFile = new File("SecureService/keystores/myTrustStore");
            char[] trustStorePassword = "123456".toCharArray();

            // Load the trust store, the default type is "pkcs12", the alternative is "jks"
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);

            // Get the singleton instance of the TrustManagerFactory
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());

            // Itit the TrustManagerFactory using the truststore object
            tmf.init(trustStore);

            //Print the trustManagers returned by the TMF
            //only for debugging
            for(TrustManager t: tmf.getTrustManagers()){
                System.out.println(t);
            }
            //Set the default global SSLContext so all the connections will use it
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            SSLContext.setDefault(sslContext);



        } catch (KeyStoreException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static String readURL(String sitetoread) {
        try {
            URL siteURL = new URL(sitetoread);
            URLConnection urlConnection = siteURL.openConnection();
            System.out.println("-------message-body------");
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
            StringBuilder response = new StringBuilder();
            String inputLine = null;
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine.trim());
            }
            return response.toString();
        } catch (IOException x) {
            System.err.println(x);
        }
        return "";
    }
}