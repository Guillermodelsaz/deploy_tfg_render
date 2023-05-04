package com.example.demo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = {"http://localhost:4200", "http://www.rfegolf.es/PaginasServicios/ServicioHandicap.aspx"})

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }



    @GetMapping("/users/{username}")
    public User getUserById(@PathVariable String username) {
        return userService.getUserById(username);

    }

    @GetMapping("/premio/{username}/{id}")
    public String getpremio(@PathVariable String username, @PathVariable int id) {
        System.out.println("Premio controller");
        return userService.premio(username,id);

    }

    @GetMapping("/terminado/{username}/{id}")
    public int getUserById(@PathVariable String username, @PathVariable int id) {
        System.out.println("Terminados controller");
        return userService.terminados(username,id);

    }

    @GetMapping("/torneo/{username}")
    public List<Torneo> getTorneoById(@PathVariable String username) {

        return userService.getTorneobyId(username);

    }

    @GetMapping("/torneo/club/{username}")
    public List<Torneo> getClubTorneoById(@PathVariable String username) {
        return userService.getClubTorneobyId(username);
    }

    @GetMapping("/torneo/patro/{username}")
    public List<Torneo> getPatroTorneoById(@PathVariable String username) {
        System.out.println("eeeeeeeeeeeeeeeeee");
        return userService.getPatroTorneobyId(username);
    }

    @GetMapping("/campo/{id}")
    public List<Hoyo> getCampo(@PathVariable String id) {


        String club="club";
        return userService.getCampo(club);
    }



    @GetMapping("/estadisticas/{username}")
    public List<Estadisticas> getestadisticas(@PathVariable String username) {
        return userService.getEstadistica(username);

    }
    @GetMapping("/torneo/{username}/{id}")
    public List<Estadisticas> getestadisitcastorneo(@PathVariable String username, @PathVariable int id) {
        return userService.getEstadisticabyId(username, id);
    }


    @GetMapping("/resultados/{username}/{id}")
    public List<Resultado> getResultadoById(@PathVariable String username, @PathVariable int id) {
        return userService.getResultados(id,username);
    }

    @GetMapping("/resultados/{id}")
    public List<Resultado> getResultadoById( @PathVariable int id) {
        return userService.getResultadosdirecto(id);
    }

    @GetMapping("/t_res/{id}")
    public List<Resultado> getResultadoT( @PathVariable int id) {
        return userService.getResT(id);
    }


    @GetMapping("/hcp")
    public String gethcp(@RequestBody Licencia Licencia) throws IOException {
        System.out.println(Licencia);
        return userService.gethcp(Licencia);
    }

    @PostMapping("/users")
    public ResponseEntity<Map<String, String>> handleUserPost(@RequestBody User userPost) {
        return userService.handleUserPost(userPost);
    }


    @PostMapping("/usersp")
    public ResponseEntity<String> insUserPost(@RequestBody User userPost) {

        return userService.insertUserPost(userPost);
    }

    @PostMapping("/creartorneo")
    public ResponseEntity<String> createTorneo(
            @RequestParam String name,
            @RequestParam String club,
            @RequestParam String patrocinador,
            @RequestParam Boolean modalidad,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) MultipartFile playerList) {

        // Handle the received data here
        System.out.println("Tournament Name: " + name);
        System.out.println("Club: " + club);
        System.out.println("Patrocinador: " + patrocinador);
        System.out.println("Modalidad: " + modalidad);

        byte[] imageBytes = new byte[0];
        if (image != null) {
            System.out.println("Image received: " + image.getOriginalFilename());
            imageBytes = null;
            try {
                imageBytes = image.getBytes();


            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception as required
            }
            // Save the image to the server
        }
        ResponseEntity<String> primer= userService.insertTorneo(name, club, patrocinador, imageBytes, modalidad);
        int ids = userService.insertTorneo2(name, club, patrocinador, imageBytes, modalidad);
        System.out.println(ids);
        if (playerList != null) {
            System.out.println("Player List received: " + playerList.getOriginalFilename());           // Save the player list JSON file to the server
            // You can also parse the JSON and save the data to your database
            userService.insertParticipantes(ids,playerList);

        }

        // Save the tournament data to your database

        // Return a response to the client
        return new ResponseEntity<>("Torneo creado", HttpStatus.CREATED);
    }

    @PostMapping("/sorteo")
    public ResponseEntity<String> handleSorteoRequest(@RequestBody RequestBodyDTO requestBody) {
        // Print the received data.
        //System.out.println("Torneo ID: " + requestBody.getTorneoId());
        List<Gift> gifts = requestBody.getRegalosFile().getGifts();
        /*for (Gift gift : gifts) {
            System.out.println("Gift name: " + gift.getName());
            System.out.println("Gift amount: " + gift.getAmount());
        }*/
        userService.sortear(requestBody.getTorneoId(),gifts);

        // Process the request and perform the required operations.
        // Example: Saving the received data to a database or performing some calculations.

        // Send back a response.
        String response = "Sorteo request processed successfully!";
        return ResponseEntity.ok(response);
    }

}







