package shared;

import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import server.model.User;
import server.model.UserPermission;
import server.model.UserRole;
import server.model.UserRolePermission;
import server.model.dao.UserDaoImpl;
import server.service.DatabaseService;

public abstract class DatabaseTest extends DBTestCase {

    private IDatabaseTester databaseTester;

    protected void setUp() throws Exception {

        databaseTester = new JdbcDatabaseTester(
                "org.sqlite.JDBC",
                "jdbc:sqlite:printservice-test.db"
        );

        DatabaseService.setDatabaseUrl("jdbc:sqlite:printservice-test.db");

        // try to create roles and permissions for users

        DatabaseService.createTable(UserRole.class);
        DatabaseService.createTable(UserRolePermission.class);
        DatabaseService.createTable(User.class);

        IDataSet dataset = databaseTester.getConnection().createDataSet();

        // We now have a valid but empty database
        databaseTester.setDataSet(dataset);
        databaseTester.onSetup();
    }

    protected void tearDown() throws Exception {
        databaseTester.onTearDown();
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return databaseTester.getDataSet();
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }
}