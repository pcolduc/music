package com.example.music.controller;


import com.example.music.service.AppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {


//        @GetMapping("/search-music")
    public ResponseEntity<String> searchMusic(@RequestParam("track") String trackName) {

        try{
            return AppService.search(trackName);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok().body("bad request");
        }
    }

}

