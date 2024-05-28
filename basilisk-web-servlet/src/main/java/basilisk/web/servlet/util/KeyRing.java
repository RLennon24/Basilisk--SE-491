package basilisk.web.servlet.util;

import lombok.Getter;
import lombok.Setter;

import javax.crypto.SecretKey;
import java.security.PublicKey;

public class KeyRing {

    @Getter
    private final PublicKey publicKey;
    @Getter
    private final String owner;
    @Getter
    private final String ownerIp;
    @Getter @Setter
    private SecretKey sessionKey;
    @Getter @Setter
    private SecretKey macKey;
    @Getter @Setter
    private SecretKey encodingKey;

    public KeyRing(String owner, String ownerIp, PublicKey publicKey) {
        this.owner = owner;
        this.ownerIp = ownerIp;
        this.publicKey = publicKey;
    }

}
