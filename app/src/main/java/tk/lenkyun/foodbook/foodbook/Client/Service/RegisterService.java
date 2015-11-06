package tk.lenkyun.foodbook.foodbook.Client.Service;

import tk.lenkyun.foodbook.foodbook.Adapter.ConnectionAdapter;
import tk.lenkyun.foodbook.foodbook.Adapter.ConnectionResult;
import tk.lenkyun.foodbook.foodbook.Adapter.HTTPAdapter;
import tk.lenkyun.foodbook.foodbook.Adapter.HTTPResult;
import tk.lenkyun.foodbook.foodbook.Config;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.AuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.FacebookAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.SessionAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Operation.RegistrationBuilder;
import tk.lenkyun.foodbook.foodbook.Promise.Promise;
import tk.lenkyun.foodbook.foodbook.Promise.PromiseRun;

/**
 * Created by lenkyun on 15/10/2558.
 */
public class RegisterService {

    private static RegisterService instance = null;
    private static Object lock = new Object();

    private ConnectionAdapter mConnectionAdapter;
    public RegisterService(ConnectionAdapter connectionAdapter){
        mConnectionAdapter = connectionAdapter;
    }

    /**
     * Get service instance if not exists
     * @return A service instance
     */
    public static RegisterService getInstance(){
        if(instance == null){
            synchronized (lock){
                if(instance == null){
                    instance = new RegisterService(new HTTPAdapter(Config.SERVER));
                }
            }
        }

        return instance;
    }

    public Promise<SessionAuthenticationInfo> register(RegistrationBuilder builder) {
        final Promise<SessionAuthenticationInfo> promise = new Promise<>();

        Promise<ConnectionResult> resultPromise = mConnectionAdapter.createRequest()
                .addServicePath("register")
                .setSubmit(true)
                .setDataInputParam(builder)
                .execute();

        resultPromise.onSuccess(new PromiseRun<ConnectionResult>() {
            @Override
            public void run(String status, ConnectionResult result) {
                if(result.isError())
                    promise.failed(String.format("Error(%d) %s", result.getErrorCode(), result.getStatusDetail()));
                else
                    promise.success("success",
                            result.getResult(SessionAuthenticationInfo.class));
            }
        })
        .onFailed(new PromiseRun<ConnectionResult>() {
            @Override
            public void run(String status, ConnectionResult result) {
                promise.failed("Error" + result.getStatusDetail());
            }
        });

        return promise;
    }
}
