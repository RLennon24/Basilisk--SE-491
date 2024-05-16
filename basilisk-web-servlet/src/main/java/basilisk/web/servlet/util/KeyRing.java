package basilisk.web.servlet.util;

import lombok.Getter;
import lombok.Setter;

import javax.crypto.SecretKey;
import java.security.PublicKey;

public class KeyRing {

    @Getter
    private final PublicKey publicKey;
    @Getter @Setter
    private SecretKey sessionKey;
    @Getter @Setter
    private SecretKey macKey;
    @Getter @Setter
    private SecretKey encodingKey;

    public KeyRing(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

}
