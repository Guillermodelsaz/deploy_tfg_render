package com.example.demo;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultadoMapper implements RowMapper<Resultado> {

    @Override
    public Resultado mapRow(ResultSet rs, int rowNum) throws SQLException {
        Resultado resultado = new Resultado();
        resultado.setResultadoId(rs.getInt("resultado_id"));
        resultado.setTorneoId(rs.getInt("torneo_id"));
        resultado.setJugadorId(rs.getString("jugador_id"));
        resultado.setScore(rs.getInt("score"));
        resultado.setThru(rs.getInt("hoyo"));
        resultado.setFinished(rs.getBoolean("finished"));
        return resultado;
    }
}