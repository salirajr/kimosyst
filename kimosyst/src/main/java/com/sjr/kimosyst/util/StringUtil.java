package com.sjr.kimosyst.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjr.kimosyst.model.MutasiSubmission;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rais <rais.gowa@gmail.com>
 */
public class StringUtil {

    public static String buildString(String prefix, String infix, String suffix, String... strings) {
        StringBuilder sb = new StringBuilder();
        if (prefix != null) {
            sb.append(prefix);
        }
        int last = strings.length - 1;
        for (int i = 0; i < last; i++) {
            sb.append(strings[i]);
            if (infix != null) {
                sb.append(infix);
            }
        }
        sb.append(strings[last]);
        if (suffix != null) {
            sb.append(suffix);
        }
        return sb.toString();
    }

    public static String toJSONString(Object object) {
        if (object == null) {
            return "{}";
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(StringUtil.class.getName()).log(Level.SEVERE, null, ex);
            return "['" + ex + "']";
        }

    }

    public static boolean isNullOrBlank(String in) {
        return in == null || in.trim().equals("");
    }

    public static String padZero(Object in, int nPad) {
        return String.format("%0" + nPad + "d", in);
    }

    public static String[] textWrap(int lChar, String... texts) {
        String normText = "";
        for (String text : texts) {
            normText += text;
        }
        int nNormText = normText.length() - 1;
        String[] result = new String[1 + (nNormText / lChar)];
        int itrCharMax;
        for (int i = 0; i < result.length; i++) {
            itrCharMax = ((i + 1) * lChar);
            result[i] = normText.substring(i * lChar, itrCharMax < nNormText ? itrCharMax : nNormText);
        }
        return result;
    }

    public static String concats(String delimiter, Object... texts) {
        String result = "";

        for (Object text : texts) {
            result += text + delimiter;
        }
        result = result.substring(0, result.length() - delimiter.length());
        System.out.println("concats[" + Arrays.toString(texts) + "]=" + result);
        return result;
    }

    public static String asURL(String q) throws UnsupportedEncodingException {
        return URLEncoder.encode(q, "UTF-8");
    }

}
