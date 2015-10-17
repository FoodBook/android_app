package tk.lenkyun.foodbook.foodbook.Client.Service.Exception;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class InvalidUserInfoException extends RuntimeException {
    public InvalidUserInfoException(){
        super("Invalid password");
    }
}
