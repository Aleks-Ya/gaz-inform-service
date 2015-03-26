package ru.yaal.gazinformservice;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DerbyServiceTest {

    private static DerbyService service;

    @BeforeClass
    public static void beforeStart() throws SQLException, ClassNotFoundException {
        service = new DerbyService();
    }

    @Test
    public void createAndFind() throws SQLException {
        String name1 = "Иван";
        String family1 = "Иванов";
        String name2 = "Петр";
        String family2 = "Петров";
        service.createAccount(name1, family1);
        service.createAccount(name2, family2);

        Account actual1 = service.findAccount(name1);
        Account actual2 = service.findAccount(name2);
        accountEquals(actual1, name1, family1);
        accountEquals(actual2, name2, family2);
    }

    @Test
    public void findNotExists() throws SQLException {
        assertNull(service.findAccount("NotExists"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNull() throws SQLException {
        service.createAccount(null, "Петров");
    }

    @Test
    public void findNull() throws SQLException {
        assertNull(service.findAccount(null));
    }

    @AfterClass
    public static void afterClass() throws SQLException {
        service.stop();
    }

    private void accountEquals(Account actual, String nameExcepted, String familyExpected) {
        assertEquals(nameExcepted, actual.getName());
        assertEquals(familyExpected, actual.getFamily());
    }
}