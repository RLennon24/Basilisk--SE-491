package basilisk.web.servlet.idgen;

import basilisk.web.servlet.domain.DataUnit;

import java.util.UUID;

public class IdGeneration {

    public static String generateId(DataUnit dataUnit) {
        String strKey = dataUnit.getName() + dataUnit.getDataCreator() + dataUnit.getTimestamp();
        UUID uuid = UUID.nameUUIDFromBytes(strKey.getBytes());
        String strUUID = uuid.toString();
        return strUUID;
    }

}
