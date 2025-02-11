package api.user;

import api.base.BaseHttpClient;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import api.constants.Url;

public class UserApi extends BaseHttpClient {

    public ValidatableResponse register(Object body) {
        return doPostRequest(Url.USER_REGISTER_API, body);
    }

    public ValidatableResponse login(Object body) {
        return doPostRequest(Url.USER_AUTH_API, body);
    }

    public Response change(String token, Object body) {
        return doPatchRequest(Url.USER_CHANGE_API, token, body);
    }

    public Response change( Object body) {
        return doPatchRequest(Url.USER_CHANGE_API, body);
    }

    public ValidatableResponse delete(String token) {
        return doDeleteRequest(Url.USER_DELETE_API, token);
    }
}
