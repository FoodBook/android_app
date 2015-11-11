package tk.lenkyun.foodbook.foodbook.Domain.Data.Body;

import org.json.JSONObject;
import tk.lenkyun.foodbook.foodbook.Domain.Data.FoodbookType;

/**
 * Created by lenkyun on 5/11/2558.
 */
public class ResponseDesc implements FoodbookType {
    private String status = "";
    private int error = 0;
    private JSONObject data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
