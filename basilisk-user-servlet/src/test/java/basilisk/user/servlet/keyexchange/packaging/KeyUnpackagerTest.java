package basilisk.user.servlet.keyexchange.packaging;

import basilisk.user.servlet.exception.EncryptionException;
import basilisk.user.servlet.keygen.BasiliskUserKeyGen;
import basilisk.user.servlet.message.BaseMessage;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class KeyUnpackagerTest {

    @Test
    public void testProcessPublicKeyPackage() {
        BasiliskUserKeyGen.generateKeyPair();

        BaseMessage keyMessage = new BaseMessage();
        keyMessage.setMessage("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmRmMRIJiVB2NF7Cz4Ojje7Y8Z3PHiPRXKJosCEsd9VrVkIipTqxu4t8S1Sj8vAAD7F2/vSonSD+8N9Rb6T1O7+J7riefLr3+Ve1k7tjJNs33pfRNdz56h5K3FGG9kg6ZJ+WQ6cop/6dj72t5J0Ig+Li9Ohv9/HFQC6omflTWJh8k92LKIiFR0euQ9dI04TLOtXE+0oHHO5gM5p1wG0KGs3Bwt4BYsYCrHL3H0xLFJMsT7jUHto3jndvFJfSXlVLEvTSxkN8gL1v+3FfrneSp0TxuN6eVmDIL+B7vedZojI9kGjDg24bLeWoTI/NYv0TCfGlVyFNmwzPzLK5u3dFQdwIDAQAB");
        keyMessage.setSignature("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmRmMRIJiVB2NF7Cz4Ojje7Y8Z3PHiPRXKJosCEsd9VrVkIipTqxu4t8S1Sj8vAAD7F2/vSonSD+8N9Rb6T1O7+J7riefLr3+Ve1k7tjJNs33pfRNdz56h5K3FGG9kg6ZJ+WQ6cop/6dj72t5J0Ig+Li9Ohv9/HFQC6omflTWJh8k92LKIiFR0euQ9dI04TLOtXE+0oHHO5gM5p1wG0KGs3Bwt4BYsYCrHL3H0xLFJMsT7jUHto3jndvFJfSXlVLEvTSxkN8gL1v+3FfrneSp0TxuN6eVmDIL+B7vedZojI9kGjDg24bLeWoTI/NYv0TCfGlVyFNmwzPzLK5u3dFQdwIDAQAB\n" +
                "flShdzcNmp7nfHD4NxTWi66WUkTSBz1qmFTDp5LYwOVGTdEV6YucbeMUcOcyjgc9f+XHf8cl2XrryrjNejlR51YomJLnEkhY+ehfFZYurCsTtocyxUtrwIV/Q28p910bFpNeKoevxmwaTOwuN4yBWGLO5AE8p+wKJMWmaNn7miJqCk23auOZp29NiLtpOKq37lIRN0CMTTpr1jWpq8moN/4FRyfo8MktL01CMpLu0yxzhK6FW+dGPBRQm9nhPTu0zIYpdRGJQD/V5TF2kZZyBoXKRPwU5kmPIC9wMsNmytXSwmZNfXdDd5Typ4I8bhdM+6myC1Drp1Px9jdd0+Dp8w==");
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(System.currentTimeMillis()));
        keyMessage.setTimestamp(timeStamp);

        KeyUnpackager.processPublicKeyPackage(keyMessage);

        keyMessage.setTimestamp("");
        EncryptionException ex = assertThrows(EncryptionException.class, () -> {
            KeyUnpackager.processPublicKeyPackage(keyMessage);
        });
        assertEquals("Could not unpackage shared key", ex.getMessage());

        timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date(2020, 10, 10));
        keyMessage.setTimestamp(timeStamp);
        ex = assertThrows(EncryptionException.class, () -> {
            KeyUnpackager.processPublicKeyPackage(keyMessage);
        });
        assertEquals("Could not unpackage shared key", ex.getMessage());
    }
}