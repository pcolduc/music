package com.example.music.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {

    @JsonProperty("name")
    private String name;
    @JsonProperty("artist")
    private String artist;
}
