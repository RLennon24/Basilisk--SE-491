package basilisk.user.servlet.rolesmap;

import basilisk.user.servlet.parsing.DataUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;


class RolestoUserTest {

    static DataUnit testOneUnit = new DataUnit();
    static DataUnit testTwoUnit = new DataUnit();

    public static void setUp() {

        testOneUnit.setId("testOne");
        testOneUnit.setData("test data");
        testOneUnit.setRoles(Arrays.asList("basic", "government"));
        testOneUnit.setTags(Arrays.asList("personal", "common"));

        testTwoUnit.setId("testTwo");
        testTwoUnit.setData("other test data");
        testTwoUnit.setRoles(Arrays.asList("basic"));
        testTwoUnit.setTags(Arrays.asList("personal", "common"));

    }

    @Test
    void doesUserHaveRole() throws IOException {
        assertFalse(RolestoUser.doesUserHaveRole("Maheen", "basic"));
        assertFalse(RolestoUser.doesUserHaveRole("Maheen", "common"));
        assertFalse(RolestoUser.doesUserHaveRole("Maheen", "personal"));
        assertFalse(RolestoUser.doesUserHaveRole("Maheen", "government"));

        RolestoUser.giveUserRole("Riley", "basic");
        Assertions.assertTrue(RolestoUser.doesUserHaveRole("Riley", "basic"));

        RolestoUser.giveUserRole("Marcel", "common");
        Assertions.assertTrue(RolestoUser.doesUserHaveRole("Marcel", "common"));

        RolestoUser.giveUserRole("Maheen", "personal");
        Assertions.assertTrue(RolestoUser.doesUserHaveRole("Maheen", "personal"));

        RolestoUser.giveUserRole("Zach", "government");
        Assertions.assertTrue(RolestoUser.doesUserHaveRole("Zach", "government"));

        assertFalse(RolestoUser.doesUserHaveRole("Joe", "government"));

        RolestoUser.writeFiles();
        RolestoUser.readFromFiles();

    }

    @Test
    void writeFiles() {
        RolestoUser.writeFiles();

        File folder = Paths.get(System.getProperty("user.home") + File.separator +
                "basilisk" + File.separator + "RolestoUser.json").toFile();

        if (!folder.isFile()) {
            throw new RuntimeException("Storage path: " + folder.getPath() + " is not a folder/does not exist");
        }
    }

}
