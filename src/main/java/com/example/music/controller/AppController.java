package com.example.music.controller;

import com.example.music.dto.Track;
import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.hc.client5.http.classic.methods.HttpGet;
//import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
//import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
//import org.apache.hc.client5.http.impl.classic.HttpClients;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;


@RestController
public class AppController {

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/search-music")
    public ResponseEntity<String> searchMusic(@RequestParam("track") String trackName) {

        String apiKey = "cee6a05bdca3eb832ae5eb9c05f520a2";
        String apiUrl = "http://ws.audioscrobbler.com/2.0/?method=track.search&track=" + trackName + "&api_key=" + apiKey + "&format=json";
        try{
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
            String responseBody = response.getBody();

//            JsonParser parser = new JsonParser();
//            JsonObject jsonObject = parser.parse(responseBody).getAsJsonObject();



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
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok().body("good");
        }





    }


//    @GetMapping("/searchTrack")
//    public String searchTrack(
//            @RequestParam String apiKey,
//            @RequestParam String trackName
//    ) {
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            String apiUrl = "http://ws.audioscrobbler.com/2.0/?method=track.search&track=" + trackName + "&api_key=" + apiKey + "&format=json";
//            HttpGet httpGet = new HttpGet(apiUrl);
//            CloseableHttpResponse response = httpClient.execute(httpGet);
//
//            // API 응답을 읽기
//            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//            StringBuilder responseText = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                responseText.append(line);
//            }
//
//            // JSON 파싱
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(responseText.toString());
//
//            // 필요한 데이터 추출 (예: 트랙 이름)
//            String trackNameResult = rootNode.get("results").get("trackmatches").get("track").get(0).get("name").asText();
//
//            return "Track Name: " + trackNameResult;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error occurred.";
//        }
//    }

//    @GetMapping("/searchTrack")
//    public String searchTrack(@RequestParam("apiKey") String apiKey, @RequestParam("trackName") String trackName) {
//        return apiKey + trackName;
//    }
}

