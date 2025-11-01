package lottery.backend.login;

import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import edu.sjtu.oauth.applicationToolkit.CustomURLConnectionClient;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.token.OAuthToken;

public class JAccountUtil {

    private final String client_id;

    private final String client_secret;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public JAccountUtil(String client_id, String client_secret) {
        this.client_id = client_id;
        this.client_secret = client_secret;
    }

    public String getAuthorizationCode(String redirect_uri) throws OAuthSystemException, IOException {
        OAuthClientRequest request = OAuthClientRequest
                                     .authorizationLocation("https://jaccount.sjtu.edu.cn/oauth2/authorize")
                                     .setResponseType("code")
                                     .setScope("essential")
                                     .setClientId(client_id)
                                     .setRedirectURI(redirect_uri)
                                     .buildQueryMessage();
        return request.getLocationUri();
    }
    public String getAccessToken(String code, String redirect_uri) throws Exception {
        OAuthClientRequest request = OAuthClientRequest
                                     .tokenLocation("https://jaccount.sjtu.edu.cn/oauth2/token")
                                     .setGrantType(GrantType.AUTHORIZATION_CODE)
                                     .setCode(code)
                                     .setRedirectURI(redirect_uri)
                                     .setClientId(client_id)
                                     .setClientSecret(client_secret)
                                     .buildBodyMessage();
        OAuthClient client = new OAuthClient(new CustomURLConnectionClient(5000, 10000));
        OAuthJSONAccessTokenResponse response = client.accessToken(request, OAuthJSONAccessTokenResponse.class);
        OAuthToken token = response.getOAuthToken();
        return token.getAccessToken();
    }
}