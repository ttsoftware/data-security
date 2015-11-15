package server.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "UserRolePermissions")
public class UserRolePermission implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private UserPermission permission;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "fk_user_role")
    private UserRole role;

    public UserRolePermission() {
    }

    public int getId() {
        return id;
    }

    public UserPermission getPermission() {
        return permission;
    }

    public void setPermission(UserPermission permission) {
        this.permission = permission;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
