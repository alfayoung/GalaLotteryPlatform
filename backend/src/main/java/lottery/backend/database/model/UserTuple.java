package lottery.backend.database.model;

import lombok.Data;

@Data
public class UserTuple {
    private String name;
    private Byte[] profile;

    public UserTuple(String name, Byte[] profile) {
        this.name = name;
        this.profile = profile;
    }
}