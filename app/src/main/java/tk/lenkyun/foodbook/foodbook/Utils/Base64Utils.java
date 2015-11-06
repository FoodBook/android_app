package tk.lenkyun.foodbook.foodbook.Utils;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by lenkyun on 7/11/2558.
 */
public class Base64Utils {

    // Configuring for url safe base64
    public static String encode(String string){
        return encode(string.getBytes());
    }

    public static String encode(byte[] bytes){
        Base64 base64 = new Base64(true);
        return new String(base64.encode(bytes));
    };

    public static byte[] decode(String string){
        Base64 base64 = new Base64(true);
        return base64.decode(string);
    }
}
