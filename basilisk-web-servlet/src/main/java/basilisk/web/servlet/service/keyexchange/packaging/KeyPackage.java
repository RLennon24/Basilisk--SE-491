package basilisk.web.servlet.service.keyexchange.packaging;

import lombok.Data;

@Data
public class KeyPackage {

    String timestamp;
    String key;
    String signature;
}
