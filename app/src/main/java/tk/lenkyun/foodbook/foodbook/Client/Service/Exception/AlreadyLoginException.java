package tk.lenkyun.foodbook.foodbook.Client.Service.Exception;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class AlreadyLoginException extends RuntimeException {
    public AlreadyLoginException(){
        super("Already login");
    }
}
