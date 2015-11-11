package tk.lenkyun.foodbook.foodbook.Client.Service.Exception;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class InvalidUserInfoException extends RuntimeException implements LoginException {
    public InvalidUserInfoException(){
        super("Invalid password");
    }
}
