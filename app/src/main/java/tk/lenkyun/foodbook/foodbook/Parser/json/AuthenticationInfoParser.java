package tk.lenkyun.foodbook.foodbook.Parser.json;

import org.json.JSONException;
import org.json.JSONObject;

import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.AuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.FacebookAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.SessionAuthenticationInfo;
import tk.lenkyun.foodbook.foodbook.Domain.Data.Authentication.UserAuthenticationInfo;

/**
 * Created by lenkyun on 4/11/2558.
 */
public class AuthenticationInfoParser extends JSONParser<AuthenticationInfo> {
    @Override
    public JSONObject parse(AuthenticationInfo object) {
        JSONObject json = new JSONObject();
        try {
            json.put("type", object.getAuthenticationType());
            json.put("id", object.getId());
            json.put("info", object.getInfo());

            return json;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public AuthenticationInfo from(final JSONObject json) {
        // If supported authentication type
        try {
            final String type = json.getString("type");
            if (type.equals(UserAuthenticationInfo.AUTH_TYPE)) {
                return new UserAuthenticationInfo(
                        json.getString("id"),
                        json.getString("info")
                );
            } else if (type.equals(SessionAuthenticationInfo.AUTH_TYPE)) {
                String user = "-1";
                if(json.has("uid")){
                    user = json.getString("uid");
                }
                return new SessionAuthenticationInfo(
                        user,
                        json.getString("id"),
                        json.getString("info")
                );
            } else if (type.equals(FacebookAuthenticationInfo.AUTH_TYPE)) {
                return new FacebookAuthenticationInfo(
                        json.getString("id"),
                        json.getString("info")
                );
            } else {
                return new AuthenticationInfo(type) {
                    private String id = json.getString("id"),
                                   info = json.getString("info");

                    @Override
                    public String getId() {
                        return id;
                    }

                    @Override
                    public String getInfo() {
                        return info;
                    }

                    @Override
                    public void setAuthenticateInfo(String authenticateInfo) {
                    }
                };
            }
        } catch (JSONException e) {
            return null;
        }
    }
}
