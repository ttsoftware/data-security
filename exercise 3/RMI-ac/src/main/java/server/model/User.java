package server.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import server.model.dao.UserDaoImpl;
import server.service.DatabaseService;
import shared.exception.UserPermissionException;

import javax.json.*;
import java.io.Serializable;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@DatabaseTable(tableName = "Users", daoClass = UserDaoImpl.class)
public class User implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    @DatabaseField(canBeNull = false)
    private String password;

    @DatabaseField(canBeNull = false)
    private String salt;

    @DatabaseField(
            canBeNull = false,
            foreign = true,
            foreignAutoCreate = true,
            columnName = "fk_user_role")
    private UserRole role;

    /**
     * This is a JSON list of User specific UserPermission's
     */
    @DatabaseField(canBeNull = false, defaultValue = "[]")
    private String permissions;

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public List<String> getPermissions() {
        JsonArray permissionsJSONArray = Json.createReader(new StringReader(permissions)).readArray();
        List<JsonString> permissionsArray = permissionsJSONArray.getValuesAs(JsonString.class);

        return permissionsArray.stream().map(JsonString::getString).collect(Collectors.toList());
    }

    public void removePermission(UserPermission permission) {
        boolean userHasPermission = false;

        // we do not want to remove the permission if it does not exists in the list
        for (String userPermission : getPermissions()) {
            if (userPermission.equals(permission.name())) {
                userHasPermission = true;
            }
        }

        if (userHasPermission) {
            JsonArray permissionsJSONArray = Json.createReader(new StringReader(permissions)).readArray();

            // build new array
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

            // add all elements from existing array except the one we wish to remove
            permissionsJSONArray.forEach(p->{
                JsonString s = (JsonString) p;
                if (!s.getString().equals(permission.name())) {
                    jsonArrayBuilder.add(p);
                }
            });

            this.permissions = jsonArrayBuilder.build().toString();
        }
    }

    public void addPermission(UserPermission permission) {

        boolean userHasPermission = false;

        // we do not want to add the permission if it already exists in the list
        for (String userPermission : getPermissions()) {
            if (userPermission.equals(permission.name())) {
                userHasPermission = true;
            }
        }

        if (!userHasPermission) {
            JsonArray permissionsJSONArray = Json.createReader(new StringReader(permissions)).readArray();

            // build new array
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            jsonArrayBuilder.add(permission.name());

            // add all elements from existing array
            permissionsJSONArray.forEach(jsonArrayBuilder::add);

            this.permissions = jsonArrayBuilder.build().toString();
        }
    }

    /**
     * Checks if a User has the given permission.
     * This method takes both cases into consideration:
     * - First we check if the user has the given permission associated directly
     * - Second we check if the user's role has the given permission
     *
     * @param permission
     * @return boolean
     * @throws UserPermissionException
     */
    public boolean hasPermission(UserPermission permission) throws UserPermissionException {

        for (String userPermission : getPermissions()) {
            if (userPermission.equals(permission.name())) {
                return true;
            }
        }

        HashMap<String, Object> queryFields = new HashMap<>();
        queryFields.put("fk_user_role", this.getRole().getId());
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