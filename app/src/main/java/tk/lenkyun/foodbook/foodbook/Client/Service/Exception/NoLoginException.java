package tk.lenkyun.foodbook.foodbook.Client.Service.Exception;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class NoLoginException extends RuntimeException {
    public NoLoginException(){
        super("No user session already in use.");
    }
}
