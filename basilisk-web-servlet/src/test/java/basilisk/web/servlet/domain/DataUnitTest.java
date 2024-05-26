package basilisk.web.servlet.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

public class DataUnitTest {

    @Test
    public void testDataUnit() {
        DataUnit originalUnit = new DataUnit();
        originalUnit.setId("id");
        originalUnit.setData("data");
        originalUnit.setTags(Collections.singletonList("tag"));
        originalUnit.setRoles(Arrays.asList("role1", "role2"));

        String unitStr = originalUnit.toString();
        DataUnit newUnit = DataUnit.fromString(unitStr);
        Assertions.assertEquals(originalUnit, newUnit);
    }

}