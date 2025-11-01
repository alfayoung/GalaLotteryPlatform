package lottery.backend.database.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserEntity {
    private Integer id;
    private String jAccount;
    private String name;
    private String prize;
    private Boolean enrolled;
    private Byte[] profile;
    private Timestamp createTime;
    private Timestamp updateTime;

    public UserEntity(String jAccount, String name, String prize, Boolean enrolled, Byte[] profile) {
        this.jAccount = jAccount;
        this.name = name;
        this.prize = prize;
        this.enrolled = enrolled;
        this.profile = profile;
    }

    // 无参构造函数非常重要，见：https://cloud.tencent.com/developer/article/1655614
    public UserEntity() {}
}