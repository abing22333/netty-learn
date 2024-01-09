package flash.netty.chat.domain;

import lombok.Data;

@Data
public class Session {
    private String userId;
    private String userName;

    @Override
    public String toString() {
        return "[" + userName + "," + userId + "]";
    }
}
