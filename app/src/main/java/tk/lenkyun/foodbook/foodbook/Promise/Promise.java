package tk.lenkyun.foodbook.foodbook.Promise;

/**
 * Created by lenkyun on 5/11/2558.
 */
public class Promise<E> {
    private PromiseRun<E> successRun = null, failedRun = null;
    private boolean alreadySuccess = false;
    private boolean alreadyFailed = false;

    private E obj = null;
    private String status = "";

    public Promise<E> onSuccess(PromiseRun<E> run){
        if(alreadySuccess)
            run.run(status, obj);

        successRun = run;
        return this;
    }

    public Promise<E> onFailed(PromiseRun<E> run){
        if(alreadyFailed)
            run.run(status, obj);

        failedRun = run;
        return this;
    }

    public void failed(String status){
        if(failedRun != null)
            failedRun.run(status, null);
    }

    public void success(String status, E obj){
        if(successRun != null)
            successRun.run(status, obj);
    }

    public void bind(final Promise<E> promise){
        onSuccess(new PromiseRun<E>() {
            @Override
            public void run(String status, E result) {
                promise.success(status, result);
            }
        });

        bindOnFailed(promise);
    }

    public void bindOnFailed(final Promise promise){
        onFailed(new PromiseRun<E>() {
            @Override
            public void run(String status, E result) {
                promise.failed(status);
            }
        });
    }
}
