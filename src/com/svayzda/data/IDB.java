package com.svayzda.data;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDB {
    Connection getConnection() throws SQLException, ClassNotFoundException;
}
