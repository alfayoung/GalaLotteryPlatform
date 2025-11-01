package lottery.backend.database.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProfileRequest {
    // 不加 JsonProperty 无法实现自动类型转换，获得的请求全是 null
    @JsonProperty("jAccount")
    private String jAccount;
    @JsonProperty("profile")
    private Byte[] profile;
}