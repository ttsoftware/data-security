package shared.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.dao.ObjectCache;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import shared.service.DatabaseService;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@DatabaseTable(tableName = "UserRoles")
public class UserRole implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    @ForeignCollectionField(eager = false)
    private ForeignCollection<UserRolePermission> permissions;

    public UserRole() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ForeignCollection<UserRolePermission> getPermissions() {
        return permissions;
    }

    public void addPermission(UserPermission permission) throws SQLException {

        UserRolePermission userRolePermission = new UserRolePermission();
        userRolePermission.setPermission(permission);
        userRolePermission.setRole(this);

        DatabaseService.getDao(UserRolePermission.class).create(userRolePermission);
    }

    public boolean hasPermission(UserPermission permission) throws SQLException {

        HashMap<String, Object> queryFields = new HashMap<>();
        queryFields.put("fk_user_role", this.getId());
        queryFields.put("permission", permission);

        List<UserRolePermission> permissions = DatabaseService.getDao(UserRolePermission.class)
                .queryForFieldValues(queryFields);

        return permissions.size() == 1;
    }
}
