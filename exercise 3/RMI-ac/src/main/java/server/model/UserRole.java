package server.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import shared.exception.UserPermissionException;
import server.service.DatabaseService;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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

    /**
     * Adds the permission to this user role
     * @param permission
     * @throws SQLException
     */
    public void addPermission(UserPermission permission) throws SQLException {

        UserRolePermission userRolePermission = new UserRolePermission();
        userRolePermission.setPermission(permission);
        userRolePermission.setRole(this);

        DatabaseService.getDao(UserRolePermission.class).create(userRolePermission);
    }

    /**
     * Removes the permission for this UserRole
     * @param permission
     * @throws SQLException
     */
    public void removePermission(UserPermission permission) throws SQLException {

        HashMap<String, Object> queryFields = new HashMap<>();
        queryFields.put("fk_user_role", this.getId());
        queryFields.put("permission", permission);

        // there should only be one mapping matching this query
        List<UserRolePermission> permissions = DatabaseService.getDao(UserRolePermission.class).queryForFieldValues(queryFields);
        DatabaseService.getDao(UserRolePermission.class).delete(permissions);
    }

    public boolean hasPermission(UserPermission permission) throws UserPermissionException {

        HashMap<String, Object> queryFields = new HashMap<>();
        queryFields.put("fk_user_role", this.getId());
        queryFields.put("permission", permission);

        try {
            List<UserRolePermission> permissions = DatabaseService.getDao(UserRolePermission.class).queryForFieldValues(queryFields);

            // There should only be one permission for that role with that name
            if (permissions.size() != 1) {
                throw new UserPermissionException("User did not meet permission requirements for operation: " + permission);
            }
            return true;
        } catch (SQLException e) {
            throw new UserPermissionException(e);
        }
    }
}
