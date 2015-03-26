package ru.yaal.gazinformservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DerbyService {
    private Connection conn;
    private PreparedStatement create;
    private PreparedStatement find;

    public DerbyService() throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        conn = DriverManager.getConnection("jdbc:derby:memory:gaz;create=true", "", "");
        initDb();
        create = conn.prepareStatement("INSERT INTO account(name, family) VALUES (?,?)");
        find = conn.prepareStatement("SELECT name, family FROM account WHERE name=?");
    }

    private void initDb() throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("CREATE TABLE account (" +
                "name VARCHAR(256) PRIMARY KEY, " +
                "family VARCHAR(256))");
        st.close();
    }

    public void createAccount(String name, String family) throws SQLException {
        if (name == null) {
            throw new IllegalArgumentException("Name is null");
        }
        create.setString(1, name);
        create.setString(2, family != null ? family : "");
        create.executeUpdate();
    }

    public Account findAccount(String name) throws SQLException {
        if (name != null) {
            find.setString(1, name);
            ResultSet rs = find.executeQuery();
            return rs.next() ? new Account(name, rs.getString(2)) : null;
        } else {
            return null;
        }
    }

    public void stop() throws SQLException {
        conn.close();
    }
}