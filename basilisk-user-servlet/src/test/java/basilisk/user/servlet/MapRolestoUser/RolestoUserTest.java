package basilisk.user.servlet.MapRolestoUser;

import basilisk.user.servlet.parsing.DataUnit;
import basilisk.user.servlet.parsing.JsonParseCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


class RolestoUserTest {

    private final RolestoUser rolestouser = new RolestoUser();

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
            assertFalse(rolestouser.doesUserHaveRole("Maheen","basic"));
            assertFalse(rolestouser.doesUserHaveRole("Maheen","common"));
            assertFalse(rolestouser.doesUserHaveRole("Maheen","personal"));
            assertFalse(rolestouser.doesUserHaveRole("Maheen","government"));

            rolestouser.giveUserRole("Riley", "basic");
            Assertions.assertTrue(rolestouser.doesUserHaveRole("Riley","basic"));

            rolestouser.giveUserRole("Marcel", "common");
            Assertions.assertTrue(rolestouser.doesUserHaveRole("Marcel","common"));

            rolestouser.giveUserRole("Maheen", "personal");
            Assertions.assertTrue(rolestouser.doesUserHaveRole("Maheen","personal"));

            rolestouser.giveUserRole("Zach", "government");
            Assertions.assertTrue(rolestouser.doesUserHaveRole("Zach","government"));

            assertFalse(rolestouser.doesUserHaveRole("Joe","government"));

            rolestouser.writeFiles();
            rolestouser.readFromFiles();

        }
        @Test
        void writeFiles(){
        RolestoUser.writeFiles();

            File folder = Paths.get(System.getProperty("user.home") + File.separator +
                    "basilisk" + File.separator + "RolestoUserJSON").toFile();

            if (!folder.isFile()) {
                throw new RuntimeException("Storage path: " + folder.getPath() + " is not a folder/does not exist");
            }
        }

    }
