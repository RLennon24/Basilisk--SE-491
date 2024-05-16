package basilisk.user.servlet.keygen;

import lombok.Getter;
import lombok.Setter;

import javax.crypto.SecretKey;
import java.security.PublicKey;

public class KeyCache {
    @Getter
    @Setter
    private static SecretKey sessionKey;
    @Getter @Setter
    private static SecretKey macKey;
    @Getter @Setter
    private static SecretKey encodingKey;
    @Getter @Setter
    private static PublicKey serverPublicKey;
}
