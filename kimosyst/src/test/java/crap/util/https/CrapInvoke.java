/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crap.util.https;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author JOVIR
 */
public class CrapInvoke {

    public void run() throws NoSuchAlgorithmException, KeyManagementException, MalformedURLException {
        try {
            SSLContext sslcontext = null;
            URL url;
            java.net.HttpURLConnection con = null;
            HttpsURLConnection.setDefaultHostnameVerifier(getDefaultHostnameVerifier());

            sslcontext = SSLContext.getInstance("TLS");
            TrustManager[] trust_mgr = getTrustManager();
            sslcontext.init(null, trust_mgr, new SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
            String https_url = "https://10.23.133.60:37001/rp-server/authentication/login";
            int timeout_millisec = 10000;
            String post_data = "{\"LoginRequests\":{\"user\":\"crmadmin\",\"password\":\"Crmadmin11\"}}";

            url = new URL(https_url);
            con = (java.net.HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            con.setAllowUserInteraction(true);
            con.setConnectTimeout(timeout_millisec);

            con.setDoOutput(true);
            con.setRequestMethod("POST");
            if (post_data != null && post_data.length() > 0) {
                DataOutputStream output = new DataOutputStream(con.getOutputStream());
                output.writeBytes(post_data);
                output.flush();
                output.close();
                System.out.println("sent");

            }
            System.out.println(con.getHeaderFields());

            String ascii_content = "";

            InputStream inputStream = null;
            try {
                inputStream = con.getInputStream();
            } catch (IOException ex) {
                inputStream = con.getErrorStream();
            }
            System.out.println("get inputstream done!!");
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                System.out.println("passed here!");
                ascii_content = "";
                String input;
                StringBuffer sb = new StringBuffer();
                while ((input = br.readLine()) != null) {
                    ascii_content += input;
                    System.out.println("-");
                }
                br.close();
                System.out.println("ascii_content=" + ascii_content);
                disconnect(con);
            } catch (Exception e) {
                disconnect(con);
            }

        } catch (IOException ex) {
            Logger.getLogger(CrapInvoke.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        try {
            new CrapInvoke().run();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(CrapInvoke.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(CrapInvoke.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CrapInvoke.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void disconnect(java.net.HttpURLConnection con) {
        if (con != null) {
            try {
                con.disconnect();
            } catch (Exception e) {
            }
        }
    }

    public static HostnameVerifier getDefaultHostnameVerifier() {
        return new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                return true;
            }
        };
    }

    private TrustManager[] getTrustManager() {
        TrustManager[] certs = new TrustManager[]{
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String t) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String t) {
                }

                public boolean isServerTrusted(X509Certificate[] certs) {
                    return true;
                }

                public boolean isClientTrusted(X509Certificate[] certs) {
                    return true;
                }
            }
        };

        return certs;
    }

}
