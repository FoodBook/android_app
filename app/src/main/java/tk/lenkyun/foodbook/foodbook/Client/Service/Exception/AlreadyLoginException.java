package tk.lenkyun.foodbook.foodbook.Client.Service.Exception;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class AlreadyLoginException extends RuntimeException implements LoginException {
    public AlreadyLoginException(){
        super("Already login");
    }
}
