package lottery.backend;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import edu.sjtu.api.applicationToolkit.API;

import jakarta.annotation.PostConstruct;
import lottery.backend.login.JAccountUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lottery.backend.database.model.UserTuple;
import lottery.backend.database.dao.MybatisDAO;
import lottery.backend.database.model.ProfileRequest;

@ImportResource("classpath:beans.xml")
@RestController
@RequestMapping("/api")
public class LotteryController {
    private final AtomicLong Counter = new AtomicLong();
    public record Greeting(long id, String content) {}

    @Value("${client.id}")
    private String client_id;
    @Value("${client.secret}")
    private String client_secret;
    @Value("${admin.password}")
    private String adminPassword;
    private JAccountUtil TokenUtil;

    @Autowired
    private API api;

    private final MybatisDAO mybatisDAO;

    @Autowired
    public LotteryController(MybatisDAO mybatisDAO) {
        this.mybatisDAO = mybatisDAO;
    }

    @PostConstruct
    public void init() {
        TokenUtil = new JAccountUtil(client_id, client_secret);
    }

    @GetMapping("/getAuthorizationCode")
    public String getAuthorizationCode(@RequestParam(value = "redirect_uri") String redirect_uri) {
        try {
            return TokenUtil.getAuthorizationCode(redirect_uri);
        } catch (OAuthSystemException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/getProfile")
    public Map<String, Object> getProfile(@RequestParam(value = "code") String code,
                                          @RequestParam(value = "redirect_uri") String redirect_uri) {
        // 注意，这里的 redirect_uri 必须要与 getAuthorizationCode 一致
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = null;
        try {
            url = String.format("https://api.sjtu.edu.cn/v1/me/profile?access_token=%s",
                                TokenUtil.getAccessToken(code, redirect_uri));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity,"UTF-8");
                jsonObject = new JSONObject(result);
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return CommonUtil.toMap(jsonObject);
    }

    @GetMapping("/checkPassword")
    public boolean checkPassword(@RequestParam(value = "password") String password) {
        return password.equals(adminPassword);
    }

    @GetMapping("/hello")
    public Greeting hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(Counter.incrementAndGet(), String.format("Hello %s!", name));
    }

    @GetMapping("/getAllRegistered")
    public List<String> getAllRegistered() {
        return mybatisDAO.selectEnrolledUsers();
    }

    @GetMapping("/getUserProfile")
    public Byte[] getUserProfile(@RequestParam(value = "jAccount") String jAccount) {
        if (jAccount == null || jAccount.isEmpty()) {
            return new Byte[0];
        }
        System.out.println(String.format("正在请求 %s 的头像", jAccount));
        return mybatisDAO.getUserProfile(jAccount);
    }

    @PostMapping("/registerUser")
    public boolean registerUser(@RequestParam(value = "jAccount") String jAccount,
                                @RequestParam(value = "name") String name) {
        if (jAccount == null || jAccount.isEmpty()) {
            return false;
        }
        return mybatisDAO.registerUser(jAccount, name);
    }

    @PostMapping("/registerForLottery")
    public boolean registerForLottery(@RequestParam(value = "jAccount") String jAccount) {
        if (jAccount == null || jAccount.isEmpty()) {
            return false;
        }
        return mybatisDAO.setEnrolledTrue(jAccount);
    }

    @PostMapping("/setAndGetPrizeWinners")
    public List<UserTuple> getAndSetPrizeWinners(@RequestParam(value = "prize") String prize,
                                                 @RequestParam(value = "num") int num) {
        return mybatisDAO.setAndGetPrizeWinners(prize, num);
    }

    @PostMapping("/uploadProfile")
    public boolean uploadProfile(@RequestBody ProfileRequest request) {
        System.out.println(String.format("收到来自 %s %s 的上传头像请求", request.getJAccount(), request.getProfile()));
        return mybatisDAO.uploadProfile(request.getJAccount(), request.getProfile());
    }
}