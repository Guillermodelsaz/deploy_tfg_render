package com.example.demo;

import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.Gson;
import org.springframework.web.multipart.MultipartFile;
import java.nio.charset.StandardCharsets;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    public ResponseEntity<Map<String, String>> handleUserPost(User userPost) {
        User existingUser = userDAO.getUserByUsername(userPost.getUsername());
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "null"));
        } else if (existingUser.getPassword().equals(userPost.getPassword())) {
            Map<String, String> userMap = new HashMap<>();
            userMap.put("username", existingUser.getUsername());
            userMap.put("tipo", String.valueOf(existingUser.getTipo()));
            return ResponseEntity.ok(userMap);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "null"));
        }
    }


    public ResponseEntity<String> insertUserPost(User userPost) {
        User existingUser = userDAO.getUserByUsername(userPost.getUsername());

        if(existingUser == null && userPost.getEdad()!=0) {
            User createdUser = userDAO.insertUser(userPost.getUsername(), userPost.getPassword(),userPost.getNombre(), userPost.getApellidos(), userPost.getHcp(), userPost.getEdad(), userPost.getTipo(), userPost.getLicencia());
            return ResponseEntity.ok("bien");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("mal");
        } /*SI USUAIO NO EXISTE LO INSERTA SI EXISTE DEVUELVE EXCEPCION*/

    }



    public String gethcp(Licencia Lic) throws IOException {
        String url = "http://www.rfegolf.es/PaginasServicios/ServicioHandicap.aspx?HLic="+Lic.getLicencia();
        Document doc = Jsoup.connect(url).get();
        Elements rows = doc.select("table[id*=Result] tr.RowStyle_GridOlympus");
        String fin = "";
        for (Element row : rows) {
            for (Element cell : row.select("td")) {
                System.out.print(cell.text() + "\t");
                fin = cell.text();
            }

        }
        System.out.println("dddddddd");

        return fin;
    }

    public User getUserById(String username) {
        return userDAO.getUserByUsername(username);
    }

    public List<Torneo> getTorneobyId(String username) {
        List<String> torneos = userDAO.torneosPart(username);

        List<Torneo> torn = new ArrayList<>();
        for (String torneo : torneos) {


            torn.add(userDAO.getTorneoByUsername(Integer.valueOf(torneo)));
            // Do something with the torneo string here.
        }
        return torn;
    }

    public List<Torneo> getClubTorneobyId(String username) {

        return userDAO.getClubTorneoByUsername(username);
    }

    public List<Estadisticas> getEstadisticabyId(String username, int torneo_id) {

        return userDAO.getEstadisticasTorneo(torneo_id,username);
    }
    public List<Estadisticas> getEstadistica(String username) {

        return userDAO.getEstadisticas(username);
    }

    public List<Torneo> getPatroTorneobyId(String username) {

        return userDAO.getPatroTorneoByUsername(username);
    }

    public List<Resultado> getResultados(int id, String user) {
        return userDAO.getResultados(id,user);

    }
    public List<Hoyo> getCampo( String club) {
        return userDAO.getCampo(club);

    }
    public int terminados(String user, int id) {

        userDAO.getterminado(user,id);
        return userDAO.getterminado(user,id);
    }

    public ResponseEntity<String> insertTorneo(String name, String club, String patrocinador, byte[] imageBytes, Boolean modalidad) {

        if(userDAO.crear_torneo(name,club,patrocinador,imageBytes,modalidad)==1) {
            System.out.println(name);
            return ResponseEntity.ok("bien");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("mal");
        } /*SI USUAIO NO EXISTE LO INSERTA SI EXISTE DEVUELVE EXCEPCION*/

    }

    public int insertTorneo2(String name, String club, String patrocinador, byte[] imageBytes, Boolean modalidad) {

            System.out.println(name);
            return userDAO.crear_torneo2(name,club,patrocinador,imageBytes,modalidad);
        }




    public void insertParticipantes(int ids,MultipartFile playerList) {
        Gson gson = new Gson();

        try {
            // Read the content of the MultipartFile as a String
            String playerListJson = new String(playerList.getBytes(), StandardCharsets.UTF_8);

            // Deserialize the JSON string into a PlayerList object
            PlayerList playerListObject = gson.fromJson(playerListJson, PlayerList.class);
            ValidationResult validationResult = playerListObject.validatePlayerList();
            if (validationResult.isValid()) {
                System.out.println("Player list is valid.");
                playerListObject.assignSupervisors(); // Assign supervisors
                userDAO.insParticipantes(ids, playerListObject);
                // Print the supervisor assignments
                for (Player player : playerListObject.getPlayers()) {
                    System.out.println(player.getUsername() + " is supervised by " + player.getSupervisor());
                }
            } else {
                System.out.println("Player list is invalid: " + validationResult.getMessage());
            }
            //userDAO.insParticipantes(ids,playerListObject);
            // Access the list of players and their properties
            for (Player player : playerListObject.getPlayers()) {
                System.out.println("Username: " + player.getUsername());
                System.out.println("Tee Time: " + player.getTeeTime());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sortear(String torneoId, List<Gift> gifts) {
        List<String> participantes = userDAO.ParticipantesTorneo(Integer.valueOf(torneoId));
        System.out.println(participantes);
        Collections.shuffle(participantes);
        Random random = new Random();
        torneoId = "6";
        for (Gift gift : gifts) {
            int i = gift.getAmount();
            while (i>0){
                userDAO.inssorteo(Integer.valueOf(torneoId), gift.getName(),participantes.get(random.nextInt(participantes.size())),gift.getAmount());
                i--;
            }

        }

    }

    public List<Resultado> getResultadosdirecto(int id) {



        List<Resultado> allResults = userDAO.getReslive(id);

        if (allResults == null) {
            return null;
        }

        // Create a map to store the maximum thru and total score for each jugadorId
        Map<String, Integer> maxThruMap = new HashMap<>();
        Map<String, Integer> totalScoreMap = new HashMap<>();

        // Iterate over the results and update the maximum thru and total score for each jugadorId
        for (Resultado result : allResults) {
            String jugadorId = result.getJugadorId();
            int thru = result.getThru();
            int score = result.getScore();

            if (!maxThruMap.containsKey(jugadorId) || thru > maxThruMap.get(jugadorId)) {
                maxThruMap.put(jugadorId, thru);
            }

            if (!totalScoreMap.containsKey(jugadorId)) {
                totalScoreMap.put(jugadorId, score);
            } else {
                totalScoreMap.put(jugadorId, totalScoreMap.get(jugadorId) + score);
            }
        }

        // Create a new list to store the filtered results
        List<Resultado> filteredResults = new ArrayList<>();

        // Iterate over the results again and add the ones with the maximum thru for each jugadorId to the filtered list
        for (Resultado result : allResults) {
            String jugadorId = result.getJugadorId();
            int thru = result.getThru();
            int totalScore = totalScoreMap.get(jugadorId);

            if (thru == maxThruMap.get(jugadorId)) {
                result.setScore(totalScore);
                filteredResults.add(result);
            }
        }

        return filteredResults;
    }


    public List<Resultado> getResT(int id) {
        return userDAO.getReslive(id);
    }

    public String premio(String username, int id) {

        String regalo = userDAO.getPremio(username,id);

            System.out.println("SOY EL REGALOOOOOOOOOOOO "+ regalo);
        return regalo;
    }
}
