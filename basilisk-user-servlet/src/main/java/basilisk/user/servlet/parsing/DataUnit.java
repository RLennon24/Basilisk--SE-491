package basilisk.user.servlet.parsing;

import basilisk.user.servlet.encryption.EncrypterUtil;
import basilisk.user.servlet.exception.EncryptionException;
import basilisk.user.servlet.keygen.BasiliskUserKeyGen;
import basilisk.user.servlet.keygen.KeyCache;
import com.google.gson.Gson;
import lombok.Data;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@Data
public class DataUnit {
    private String id;
    private String data;
    private List<String> tags;
    private List<String> roles;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public String toEncryptedString() {
        try {
            byte[] toEncode = this.toString().getBytes();

            // create cipher and encode message with self identifier and session key
            Cipher cipher = Cipher.getInstance("RSA");
            SecureRandom random = new SecureRandom();
            cipher.init(Cipher.ENCRYPT_MODE, BasiliskUserKeyGen.getUserPublicKey(), random);
            byte[] cipherText = cipher.doFinal(toEncode);

            return EncrypterUtil.encrypt(cipherText);
        } catch (Exception e) {
            throw new EncryptionException("Could not do key transport for servlet", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DataUnit)) {
            return false;
        }

        DataUnit otherUnit = (DataUnit) o;
        return this.id.equals(otherUnit.id) && this.data.equals(otherUnit.data) && this.tags.containsAll(otherUnit.tags)
                && otherUnit.tags.containsAll(this.tags) && this.roles.containsAll(otherUnit.roles) && otherUnit.roles.containsAll(this.roles);
    }

    public static DataUnit fromString(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, DataUnit.class);
    }

    public static DataUnit fromEncryptedString(String str) {
        try {
            Cipher cipher = null;
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, BasiliskUserKeyGen.getUserPrivateKey());
            String dataUnit = new String(cipher.doFinal(EncrypterUtil.decrypt(str)),
                    "UTF-8");

            return DataUnit.fromString(dataUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}