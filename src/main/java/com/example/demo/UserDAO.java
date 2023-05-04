package com.example.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getUsers() {
        String sql = "SELECT * FROM users";

        List<User> usrs = jdbcTemplate.query(
                sql,
                new UserMapper());
        return usrs;
    }

    public User insertUser(String username, String password,String nombre,String apellidos,String hcp, int edad,int tipo,String licencia) {
        String sql = "INSERT INTO users (username, password, nombre, apellidos, hcp, edad,tipo,licencia)\n" +
                "VALUES (?, ?, ?, ?, ?, ?,?,?)";

        User ins = new User();
        ins.setUsername(username);
        ins.setPassword(password);
        ins.setNombre(nombre);
        ins.setApellidos(apellidos);
        ins.setHcp(hcp);
        ins.setEdad(edad);
        ins.setTipo(tipo);
        ins.setLicencia(licencia);
        int rowsInserted = jdbcTemplate.update(sql, username, password,nombre,apellidos,hcp,edad,tipo,licencia);
        if (rowsInserted > 0) {
            System.out.println("Usuario insertado");
        }
    return ins;}

    public int crear_torneo(String name, String club,String patro,byte[] img,boolean modalidad) {
        String sql = "INSERT INTO torneo2 (name, club, patrocinador, image, modalidad)\n" +
                "VALUES (?, ?, ?, ?, ?)";


        int rowsInserted = jdbcTemplate.update(sql, name, club,patro,img,modalidad);
        if (rowsInserted > 0) {
            System.out.println("Usuario insertado");
            return 1;
        }
        return 0;
        }

        public User getUserByUsername(String username) {
            String sql = "SELECT * FROM users WHERE username = ?";
            List<User> users = jdbcTemplate.query(
                    sql,
                    new Object[]{username},
                    new UserMapper());

            if (users.isEmpty()) {
                return null;
            } else {
                return users.get(0);
            }
        }

    public int crear_torneo2(String name, String club, String patro, byte[] img, boolean modalidad) {
        String sql = "INSERT INTO torneo2 (name, club, patrocinador, image, modalidad) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsInserted = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, club);
            ps.setString(3, patro);
            ps.setBytes(4, img);
            ps.setBoolean(5, modalidad);
            return ps;
        }, keyHolder);

        if (rowsInserted > 0) {
            System.out.println("Torneo insertado con éxito con ID: " + keyHolder.getKey().intValue());
            return keyHolder.getKey().intValue();
        }

        return 0;
    }


    public Torneo getTorneoByUsername(int id) {
        System.out.println("SoyID:");
        System.out.println(id);

        String sql = "SELECT * FROM torneo2 WHERE torneo_id = ?";
        List<Torneo> torneos = jdbcTemplate.query(
                sql,
                new Object[]{id},
                new TorneoMapper());

        if (torneos.isEmpty()) {
            return null;
        } else {
            return torneos.get(0);
        }
    }



    public List<String> torneosPart(String user) {

        String sql = "SELECT torneo_id FROM participantes3 WHERE player_username=?";
        return jdbcTemplate.queryForList(sql, String.class, user);

    }


    public List<Torneo> getClubTorneoByUsername(String username) {
        String sql = "SELECT * FROM torneos WHERE club = ?";
        List<Torneo> torne = jdbcTemplate.query(
                sql,
                new Object[]{username},
                new TorneoMapper());

        if (torne.isEmpty()) {
            return null;
        } else {

            return torne;
        }
    }

    public List<Torneo> getPatroTorneoByUsername(String username) {
        String sql = "SELECT * FROM torneos WHERE patrocinador = ?";
        List<Torneo> torne = jdbcTemplate.query(
                sql,
                new Object[]{username},
                new TorneoMapper());

        if (torne.isEmpty()) {
            return null;
        } else {

            return torne;
        }
    }

    public List<Resultado> getResultados(int id, String user) {
        String sql = "SELECT * FROM resultados2 WHERE torneo_id = ? and jugador_id=?";
        List<Resultado> torne = jdbcTemplate.query(
                sql,
                new Object[]{id,user},
                new ResultadoMapper());

        if (torne.isEmpty()) {
            return null;
        } else {
            System.out.println(torne);
            return torne;
        }
    }

    public List<Estadisticas> getEstadisticasTorneo(int id,String username) {
        String sql = "SELECT * FROM estadisticas WHERE torneo_id = ? and username = ?";
        List<Estadisticas> torne = jdbcTemplate.query(
                sql,
                new Object[]{id,username},
                new EstadisticasMapper());

        if (torne.isEmpty()) {
            return null;
        } else {

            return torne;
        }
    }

    public List<Estadisticas> getEstadisticas(String user) {
        String sql = "SELECT * FROM estadisticas WHERE username = ?";
        List<Estadisticas> torne = jdbcTemplate.query(
                sql,
                new Object[]{user},
                new EstadisticasMapper());

        if (torne.isEmpty()) {
            return null;
        } else {

            return torne;
        }
    }

    public List<Hoyo> getCampo(String club) {
        String sql = "SELECT * FROM hoyo WHERE club = ?";
        List<Hoyo> torne = jdbcTemplate.query(
                sql,
                new Object[]{club},
                new HoyoMapper());

        if (torne.isEmpty()) {
            return null;
        } else {

            return torne;
        }
    }

    public int getterminado(String user, int id) {
        String sql = "SELECT * FROM resultados2 WHERE finished=1 and jugador_id= ? and torneo_id=?";
        List<Resultado> torne = jdbcTemplate.query(
                sql,
                new Object[]{user,id},
                new ResultadoMapper());

        if (torne.isEmpty()) {
            return 0;
        } else {
            return 1;
        }
    }

    public void insParticipantes(int torneoId, PlayerList playerListObject) {
        String insertSql = "INSERT INTO participantes3 (torneo_id, player_username, tee_time,marcador) VALUES (?, ?, ?,?)";
        for (Player player : playerListObject.getPlayers()) {
            jdbcTemplate.update(insertSql, torneoId, player.getUsername(), player.getTeeTime(),player.getSupervisor());
        }
    }

    public void inssorteo(int torneoId, String regalo, String ganador, int cantidad) {
        String insertSql = "INSERT INTO sorteos (torneo_id, regalo, ganador,cantidad) VALUES (?, ?,?,?)";

        jdbcTemplate.update(insertSql, torneoId, regalo, ganador,cantidad);

    }


    public List<String> ParticipantesTorneo(int torneoId) {
        torneoId =7;
        String sql = "SELECT player_username FROM participantes2 WHERE torneo_id=?";
        return jdbcTemplate.queryForList(sql, String.class, torneoId);

    }

    public List<Resultado> getReslive(int id) {

        String sql = "SELECT * FROM resultados2 WHERE torneo_id=?";

        List<Resultado> torne = jdbcTemplate.query(
                sql,
                new Object[]{id},
                new ResultadoMapper());

        if (torne.isEmpty()) {
            return null;
        } else {
            System.out.println(torne);
            return torne;
        }
    }

    public String getPremio(String user, int id) {
        String sql = "SELECT regalo FROM sorteos WHERE torneo_id = ? AND ganador = ?";
        String premio = null;
        try {
            premio = jdbcTemplate.queryForObject(sql, new Object[]{id, user}, String.class);
        } catch (EmptyResultDataAccessException e) {
            // handle empty result set
            premio = "No hubo suerte";
        }
        return premio;
    }

}






