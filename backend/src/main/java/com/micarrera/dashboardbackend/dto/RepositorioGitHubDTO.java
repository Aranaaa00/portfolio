package com.micarrera.dashboardbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RepositorioGitHubDTO {
    
    private Long id;
    private String name;
    private String description;
    
    @JsonProperty("html_url")
    private String htmlUrl;
    
    @JsonProperty("clone_url")
    private String cloneUrl;
    
    private String language;
    
    @JsonProperty("stargazers_count")
    private Integer stars;
    
    @JsonProperty("forks_count")
    private Integer forks;
    
    @JsonProperty("updated_at")
    private String updatedAt;
    
    @JsonProperty("created_at")
    private String createdAt;
    
    private String[] topics;
}
