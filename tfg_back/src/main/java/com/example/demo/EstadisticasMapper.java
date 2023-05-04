package com.example.demo;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstadisticasMapper implements RowMapper<Estadisticas> {

    @Override
    public Estadisticas mapRow(ResultSet rs, int rowNum) throws SQLException {
        Estadisticas nuevest = new Estadisticas();

        nuevest.setEstadistica_id(rs.getInt("estadistica_id"));  ;
        nuevest.setTorneo_id(rs.getInt("torneo_id"));
        nuevest.setNombre(rs.getString("username"));
        nuevest.setCalle(rs.getBoolean("calle"));
        nuevest.setGir(rs.getBoolean("gir")); ;
        nuevest.setOob(rs.getBoolean("oob")); ;
        nuevest.setPutts(rs.getInt("putts")); ;

        return nuevest;
    }
}
