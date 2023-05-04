package com.example.demo;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HoyoMapper implements RowMapper<Hoyo> {

    @Override
    public Hoyo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Hoyo hoyo = new Hoyo();
        hoyo.setId(rs.getInt("id"));
        hoyo.setClub(rs.getString("club"));
        hoyo.setDistancia(rs.getLong("distancia"));
        hoyo.setHcp(rs.getInt("hcp"));
        hoyo.setPar(rs.getInt("par"));
        hoyo.setImage(rs.getString("image"));
        hoyo.setNumero(rs.getInt("numero"));
        return hoyo;
    }
}
