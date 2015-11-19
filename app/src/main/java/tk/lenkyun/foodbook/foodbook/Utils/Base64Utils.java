package tk.lenkyun.foodbook.foodbook.Utils;

import android.util.Base64;

/**
 * Created by lenkyun on 7/11/2558.
 */
public class Base64Utils {

    // Configuring for url safe base64
    public static String encode(String string){
        return encode(string.getBytes());
    }

    public static String encode(byte[] bytes){
        return Base64.encodeToString(bytes, Base64.NO_WRAP | Base64.URL_SAFE);
    };

    public static byte[] decode(String string){
        return Base64.decode(string, Base64.NO_WRAP | Base64.URL_SAFE);
    }
}
