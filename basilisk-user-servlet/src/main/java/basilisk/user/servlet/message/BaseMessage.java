package basilisk.user.servlet.message;

import lombok.Data;

@Data
public class BaseMessage {

    String timestamp;
    String message;
    String signature;
}
