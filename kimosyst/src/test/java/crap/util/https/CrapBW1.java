package crap.util.https;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;
import java.io.*;
import java.net.HttpURLConnection;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.net.SocketTimeoutException;

public class CrapBW1 {

    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("bw.logger");

    /**
     * **** START SET/GET METHOD, DO NOT MODIFY ****
     */
    protected String host = "10.23.133.60";
    protected String port = "37001";
    protected String command_uri = "/rp-server/authentication/login";
    protected String method = "POST";
    protected String query_string = "";
    protected String post_data = "{\"LoginRequest\":{\"user\":\"crmadmin\",\"password\":\"Crmadmin11\"}}";
    protected int timeout_millisec = 10000;
    protected String[] header_names = null;
    protected String[] header_values = null;
    protected String basicauth_username = "";
    protected String basicauth_password = "";
    protected String ssl_version = "";
    protected String keymanager_algorithm = "";
    protected String keystore_type = "";
    protected String keystore_filepath = "";
    protected String keystore_pass = "";
    protected String key_password = "";
    protected String status_code = "";
    protected String binary_content = "";
    protected String ascii_content = "";

    public String gethost() {
        return host;
    }

    public void sethost(String val) {
        host = val;
    }

    public String getport() {
        return port;
    }

    public void setport(String val) {
        port = val;
    }

    public String getcommand_uri() {
        return command_uri;
    }

    public void setcommand_uri(String val) {
        command_uri = val;
    }

    public String getmethod() {
        return method;
    }

    public void setmethod(String val) {
        method = val;
    }

    public String getquery_string() {
        return query_string;
    }

    public void setquery_string(String val) {
        query_string = val;
    }

    public String getpost_data() {
        return post_data;
    }

    public void setpost_data(String val) {
        post_data = val;
    }

    public int gettimeout_millisec() {
        return timeout_millisec;
    }

    public void settimeout_millisec(int val) {
        timeout_millisec = val;
    }

    public String[] getheader_names() {
        return header_names;
    }

    public void setheader_names(String[] val) {
        header_names = val;
    }

    public String[] getheader_values() {
        return header_values;
    }

    public void setheader_values(String[] val) {
        header_values = val;
    }

    public String getbasicauth_username() {
        return basicauth_username;
    }

    public void setbasicauth_username(String val) {
        basicauth_username = val;
    }

    public String getbasicauth_password() {
        return basicauth_password;
    }

    public void setbasicauth_password(String val) {
        basicauth_password = val;
    }

    public String getssl_version() {
        return ssl_version;
    }

    public void setssl_version(String val) {
        ssl_version = val;
    }

    public String getkeymanager_algorithm() {
        return keymanager_algorithm;
    }

    public void setkeymanager_algorithm(String val) {
        keymanager_algorithm = val;
    }

    public String getkeystore_type() {
        return keystore_type;
    }

    public void setkeystore_type(String val) {
        keystore_type = val;
    }

    public String getkeystore_filepath() {
        return keystore_filepath;
    }

    public void setkeystore_filepath(String val) {
        keystore_filepath = val;
    }

    public String getkeystore_pass() {
        return keystore_pass;
    }

    public void setkeystore_pass(String val) {
        keystore_pass = val;
    }

    public String getkey_password() {
        return key_password;
    }

    public void setkey_password(String val) {
        key_password = val;
    }

    public String getstatus_code() {
        return status_code;
    }

    public void setstatus_code(String val) {
        status_code = val;
    }

    public String getbinary_content() {
        return binary_content;
    }

    public void setbinary_content(String val) {
        binary_content = val;
    }

    public String getascii_content() {
        return ascii_content;
    }

    public void setascii_content(String val) {
        ascii_content = val;
    }

    /**
     * **** END SET/GET METHOD, DO NOT MODIFY ****
     */
    public CrapBW1() {
    }

    public void invoke() throws Exception {
        /* Available Variables: DO NOT MODIFY
	In  : String host
	In  : String port
	In  : String command_uri
	In  : String method
	In  : String query_string
	In  : String post_data
	In  : int timeout_millisec
	In  : String[] header_names
	In  : String[] header_values
	In  : String basicauth_username
	In  : String basicauth_password
	In  : String ssl_version
	In  : String keymanager_algorithm
	In  : String keystore_type
	In  : String keystore_filepath
	In  : String keystore_pass
	In  : String key_password
	Out : String status_code
	Out : String binary_content
	Out : String ascii_content
* Available Variables: DO NOT MODIFY *****/
        boolean use_header = !isEmpty(header_names) && !isEmpty(header_values);
        boolean use_basicauth = !isEmpty(basicauth_username);
        boolean use_post_data = "POST".equals(method);
        boolean use_ssl_cert = !isEmpty(ssl_version) && !isEmpty(keymanager_algorithm) && !isEmpty(keystore_type) && !isEmpty(keystore_filepath);

        StringBuffer log_message = new StringBuffer("\n");
        log_message.append("****** URL Parameters Begin *******\n");
        log_message.append("host : ").append(host).append("\n");
        log_message.append("port : ").append(port).append("\n");
        log_message.append("method : ").append(method).append("\n");
        log_message.append("command_uri : ").append(command_uri).append("\n");
        log_message.append("query_string : ").append(query_string).append("\n");
        log_message.append("header names : ");
        if (use_header) {
            for (int i = 0; i < header_names.length; i++) {
                log_message.append(header_names[i]).append(":").append(header_values[i]).append(", ");
            }
        }
        log_message.append("\n");
        if (use_basicauth) {
            log_message.append("basicauth_username : ").append(basicauth_username).append("\n");
            log_message.append("basicauth_password : ").append(basicauth_password).append("\n");
        }
        if (use_post_data) {
            log_message.append("post_data : ").append(post_data).append("\n");
        }
        if (use_ssl_cert) {
            log_message.append("ssl_version : ").append(ssl_version).append("\n");
            log_message.append("keymanager_algorithm : ").append(keymanager_algorithm).append("\n");
            log_message.append("keystore_type : ").append(keystore_type).append("\n");
            log_message.append("keystore_filepath : ").append(keystore_filepath).append("\n");
            log_message.append("keystore_pass : ").append(keystore_pass).append("\n");
            log_message.append("key_password : ").append(key_password).append("\n");
        }
        log_message.append("******  URL Parameters End  *******\n");
        logger.info(log_message);

        port = (port != null && port.length() > 0) ? ":" + port : "";
        command_uri = (command_uri != null && command_uri.length() > 0) ? command_uri : "";
        query_string = (query_string != null && query_string.length() > 0) ? "?" + query_string : "";
        String https_url = "https://" + host + port + command_uri + query_string;
        URL url;
        java.net.HttpURLConnection con = null;
        try {
            SSLContext sslcontext = null;
            if (use_ssl_cert) {

                sslcontext = SSLContext.getInstance(ssl_version);
                KeyManagerFactory kmf = KeyManagerFactory.getInstance(keymanager_algorithm);
                KeyStore ks = KeyStore.getInstance(keystore_type);
                ks.load(new FileInputStream(keystore_filepath), keystore_pass.toCharArray());
                kmf.init(ks, key_password.toCharArray());

                TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(ks);
                TrustManager[] tm = tmf.getTrustManagers();

                sslcontext.init(kmf.getKeyManagers(), tm, null);
            } else {
                HttpsURLConnection.setDefaultHostnameVerifier(getDefaultHostnameVerifier());

                sslcontext = SSLContext.getInstance("SSL");
                TrustManager[] trust_mgr = getTrustManager();
                sslcontext.init(null, trust_mgr, new SecureRandom());
            }

            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());

            url = new URL(https_url);
            con = (java.net.HttpURLConnection) url.openConnection();
            con.setConnectTimeout(timeout_millisec);

            if (use_header) {
                for (int i = 0; i < header_names.length; i++) {
                    con.setRequestProperty(header_names[i], header_values[i]);
                }
            }
            con.setRequestProperty("Content-Type", "application/json");
            if (use_basicauth) {
                String authString = basicauth_username + ":" + basicauth_password;
                char[] authBase64 = encodeBase64(authString.getBytes(), 0, authString.length());
                con.setRequestProperty("Authorization", "Basic " + new String(authBase64));
            }

            System.out.println("post_data=" + post_data);
            if (use_post_data) {
                con.setDoOutput(true);
                con.setRequestMethod(method);
                System.out.println("method=" + method);
                if (post_data != null && post_data.length() > 0) {
                    System.out.println("post_data2=" + post_data);
                    DataOutputStream output = new DataOutputStream(con.getOutputStream());
                    output.writeBytes(post_data);
                    output.flush();
                    output.close();
                }
            }
            System.out.println("head=" + con.getHeaderFields());
            status_code = "" + con.getResponseCode();
            
            try {
                BufferedReader br = null;
                if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                }else{
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                ascii_content = "";
                String input;
                StringBuffer sb = new StringBuffer();
                while ((input = br.readLine()) != null) {
                    ascii_content += input;
                }
                br.close();

                disconnect(con);
            } catch (Exception e) {
                disconnect(con);
            }

            log_message = new StringBuffer("\n");
            log_message.append("****** Content of the URL Begin *******\n");
            log_message.append("reponse code : ").append(status_code).append("\n");
            log_message.append("content : ").append(ascii_content).append("\n");
            log_message.append("******  Content of the URL End  ********\n");
            logger.info(log_message);
        } catch (SocketTimeoutException ste) {
            logger.info(ste.getMessage());
            disconnect(con);
            status_code = "408";
        } catch (Exception e) {
            logger.info(e.getMessage());
            disconnect(con);
            throw e;
        }
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

    private void disconnect(java.net.HttpURLConnection con) {
        if (con != null) {
            try {
                con.disconnect();
            } catch (Exception e) {
            }
        }
    }

    private static final char[] map1 = new char[64];

    static {
        int i = 0;
        for (char c = 'A'; c <= 'Z'; c++) {
            map1[i++] = c;
        }
        for (char c = 'a'; c <= 'z'; c++) {
            map1[i++] = c;
        }
        for (char c = '0'; c <= '9'; c++) {
            map1[i++] = c;
        }
        map1[i++] = '+';
        map1[i++] = '/';
    }

    public static char[] encodeBase64(byte[] in, int iOff, int iLen) {
        int oDataLen = (iLen * 4 + 2) / 3; // output length without padding
        int oLen = ((iLen + 2) / 3) * 4; // output length including padding
        char[] out = new char[oLen];
        int ip = iOff;
        int iEnd = iOff + iLen;
        int op = 0;
        while (ip < iEnd) {
            int i0 = in[ip++] & 0xff;
            int i1 = ip < iEnd ? in[ip++] & 0xff : 0;
            int i2 = ip < iEnd ? in[ip++] & 0xff : 0;
            int o0 = i0 >>> 2;
            int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
            int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
            int o3 = i2 & 0x3F;
            out[op++] = map1[o0];
            out[op++] = map1[o1];
            out[op] = op < oDataLen ? map1[o2] : '=';
            op++;
            out[op] = op < oDataLen ? map1[o3] : '=';
            op++;
        }
        return out;
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }

    public static HostnameVerifier getDefaultHostnameVerifier() {
        return new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                return true;
            }
        };
    }

    public static void main(String args[]) throws Exception {
        new CrapBW1().invoke();
    }
}
