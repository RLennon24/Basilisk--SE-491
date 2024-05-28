package basilisk.web.servlet.idgen;

import basilisk.web.servlet.domain.DataUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class IdGenerationTest {
    static DataUnit testOneUnit = new DataUnit();
    static DataUnit testTwoUnit = new DataUnit();

    @BeforeAll
    static void setup() {
        System.out.println("Building new id generator");

        testOneUnit.setId("testOne");
        testOneUnit.setName("testNameOne");
        testOneUnit.setDataCreator("testCreatorOne");
        testOneUnit.setData("test data");
        testOneUnit.setRoles(Arrays.asList("basic", "government"));
        testOneUnit.setTags(Arrays.asList("personal", "common"));

        testTwoUnit.setId("testTwo");
        testTwoUnit.setData("other test data");
        testOneUnit.setName("testNameTwo");
        testOneUnit.setDataCreator("testCreatorTwo");
        testTwoUnit.setRoles(Arrays.asList("basic"));
        testTwoUnit.setTags(Arrays.asList("personal", "common"));
    }

    @Test
    void idgen() {
        Assertions.assertEquals(IdGeneration.generateId(testOneUnit), IdGeneration.generateId(testOneUnit));
        Assertions.assertNotEquals(IdGeneration.generateId(testOneUnit), IdGeneration.generateId(testTwoUnit));
    }
}
