package com.lpy.util;

import com.google.gson.JsonObject;

import java.security.MessageDigest;
import java.util.Map;

/**
 * Created by lipeiyuan on 2018/7/15.
 */
public class ToutiaoUtil {

    public static String IMAGE_DIR = "/Users/lipeiyuan/lpy_src/images/";

    public static String TOUTIAO_DOMAIN = "http://localhost:8080/";

    public static String[] IMAGE_FILE_EXT = new String[]{"png","bmp","jpg","jpeg"};

    //md5
    public static String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] bytes) {

        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }

    public static String getJsonString(int code) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code",code);
        return jsonObject.toString();
    }

    public static String getJsonString(int code , Map<String , Object> map) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code",code);
        for (Map.Entry<String,Object> entry : map.entrySet()) {
            jsonObject.addProperty(entry.getKey(),entry.getValue().toString());
        }
        return jsonObject.toString();
    }

    public static String getJsonString(int code , String msg) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("code",code);
        jsonObject.addProperty("msg",msg);
        return jsonObject.toString();
    }

    //根据后缀名判断是不是图片文件
    public static boolean isFileAllowed(String fileExt) {
        for (String ext : IMAGE_FILE_EXT) {
            if (ext.equals(fileExt)) {
                return true;
            }
        }
        return false;
    }

}
