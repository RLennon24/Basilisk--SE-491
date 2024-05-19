package basilisk.user.servlet.exception;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EncryptionExceptionTest {
    @Test
    public void testEncryptionExceptionThrown() {
        EncryptionException ex = assertThrows(EncryptionException.class, () -> {
            throw new EncryptionException();
        });
    }

    @Test
    public void testEncryptionExceptionWithMessageThrown() {
        EncryptionException ex = assertThrows(EncryptionException.class, () -> {
            throw new EncryptionException("Testing");
        });
        assertEquals("Testing", ex.getMessage());
    }

    @Test
    public void testEncryptionExceptionWithCauseThrown() {
        EncryptionException ex = assertThrows(EncryptionException.class, () -> {
            throw new EncryptionException(new NullPointerException());
        });
        assertEquals(NullPointerException.class, ex.getCause().getClass());
    }

    @Test
    public void testEncryptionExceptionWithCauseAndMessageThrown() {
        EncryptionException ex = assertThrows(EncryptionException.class, () -> {
            throw new EncryptionException("Testing", new NullPointerException());
        });
        assertEquals("Testing", ex.getMessage());
    }
}