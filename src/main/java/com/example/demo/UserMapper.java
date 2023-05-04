package com.example.demo;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User nuevUser = new User();

        nuevUser.setId(rs.getInt("id"))  ;
        nuevUser.setUsername(rs.getString("username"));
        nuevUser.setPassword(rs.getString("password"));
        nuevUser.setNombre(rs.getString("nombre"));
        nuevUser.setApellidos(rs.getString("apellidos")) ;
        nuevUser.setHcp(rs.getString("hcp")) ;
        nuevUser.setEdad(rs.getInt("edad")) ;
        nuevUser.setTipo(rs.getInt("tipo"));
        nuevUser.setLicencia(rs.getString("licencia"));
        return nuevUser;
    }
}

