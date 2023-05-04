package com.example.demo;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TorneoMapper implements RowMapper<Torneo> {

    @Override
    public Torneo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Torneo torneo = new Torneo();

        torneo.setTorneoId(rs.getInt("torneo_id"));
        torneo.setName(rs.getString("name"));
        torneo.setClub(rs.getString("club"));
        torneo.setPatrocinador(rs.getString("patrocinador"));
        torneo.setImage(rs.getString("image"));
        torneo.setModalidad(rs.getBoolean("modalidad"));
        return torneo;
    }
}


