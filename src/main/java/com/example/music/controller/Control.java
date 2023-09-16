package com.example.music.controller;

import com.example.music.dto.Track;
import com.example.music.service.AppService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class Control {

    //검색화면 구현
    @GetMapping("/searchhome")
    public String searchMusic(){
        return "search";
    }

    //검색리스트 구현
    @GetMapping("/search-music")
    public String searchList(@RequestParam String track, Model model){
        List<Track> list;
        try{
           list = AppService.searchmusic(track);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        model.addAttribute("tracks", list);

        return "searchlist";// 주석 테스트...........................................................
    }
}
