package com.example.music.service;

import com.example.music.dto.Track;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppService {

    public static ResponseEntity<String> search(String trackName) throws JsonProcessingException {

        String apiKey = "cee6a05bdca3eb832ae5eb9c05f520a2";
        String apiUrl = "http://ws.audioscrobbler.com/2.0/?method=track.search&track=" + trackName + "&api_key=" + apiKey + "&format=json";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);

        JsonNode resultsNode = rootNode.get("results");
        JsonNode trackmatchesNode = resultsNode.get("trackmatches");
        ArrayNode trackArrayNode = (ArrayNode) trackmatchesNode.get("track");

        List<Track> tracks = new ArrayList<>();
        for (JsonNode trackNode : trackArrayNode) {
            Track track = objectMapper.treeToValue(trackNode, Track.class);
            tracks.add(track);
            System.out.println("Track Name: " + track.getName());
            System.out.println("Artist: " + track.getArtist());
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String gsonStr = gson.toJson(tracks);
        return ResponseEntity.ok().body(gsonStr);
    }


    public static List<Track> searchmusic(String trackName) throws JsonProcessingException {

        String apiKey = "cee6a05bdca3eb832ae5eb9c05f520a2";
        String apiUrl = "http://ws.audioscrobbler.com/2.0/?method=track.search&track=" + trackName + "&api_key=" + apiKey + "&format=json";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);

        JsonNode resultsNode = rootNode.get("results");
        JsonNode trackmatchesNode = resultsNode.get("trackmatches");
        ArrayNode trackArrayNode = (ArrayNode) trackmatchesNode.get("track");

        List<Track> tracks = new ArrayList<>();
        for (JsonNode trackNode : trackArrayNode) {
            Track track = objectMapper.treeToValue(trackNode, Track.class);
            tracks.add(track);
        }
        return tracks;
    }
}
