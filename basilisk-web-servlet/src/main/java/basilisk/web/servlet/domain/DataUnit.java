package basilisk.web.servlet.domain;

import com.google.gson.Gson;
import lombok.Data;

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
}