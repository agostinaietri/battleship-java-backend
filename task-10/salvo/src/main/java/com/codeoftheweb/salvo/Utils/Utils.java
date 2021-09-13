package com.codeoftheweb.salvo.Utils;

import com.codeoftheweb.salvo.model.GamePlayer;
import com.codeoftheweb.salvo.model.Player;
import com.codeoftheweb.salvo.model.Salvo;
import com.codeoftheweb.salvo.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class Utils {

    @Autowired
    private PlayerRepository playerRepository;

    public static Map<String, Object> makeMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    public static boolean isGuest(Authentication auth) {
        return auth == null || auth instanceof AnonymousAuthenticationToken;
    }

    public Player getPlayers(Authentication auth) {
        return playerRepository.findByUserName(auth.getName());
    }

    /*

    traeme todas las locations del oponente y mis ship locations,
    si la location del ship y la del salvo del oponente no coinciden, ponelas en el list de hits

     */

    public static List<String> getHits(Salvo salvo) {
        List<String> hitLocations = new ArrayList<>();
        List<String> gpSelfSalvoLocations = salvo.getSalvoLocations(); //this.getGamePlayer().getSalvoes().stream().flatMap(salvo -> salvo.getSalvoLocations().stream()).collect(toList());
        List<String> ShipLocations = salvo.getGamePlayer().getOpponent().get().getShips().stream().flatMap(location -> location.getShipLocations().stream()).collect(toList());
        hitLocations = gpSelfSalvoLocations.stream().filter(location -> ShipLocations.contains(location)).collect(toList());

        return hitLocations;
    }

}
