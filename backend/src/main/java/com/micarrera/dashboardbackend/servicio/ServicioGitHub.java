package com.micarrera.dashboardbackend.servicio;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.micarrera.dashboardbackend.dto.RepositorioGitHubDTO;

@Service
public class ServicioGitHub {
    
    private static final Logger log = LoggerFactory.getLogger(ServicioGitHub.class);
    
    private static final String GITHUB_API_URL = "https://api.github.com";
    private static final String GITHUB_USERNAME = "Aaranaa00";
    private static final String REPO_EXCLUIDO = "portfolio";
    private static final Duration CACHE_DURATION = Duration.ofMinutes(5);
    
    private final RestClient restClient;
    
    private List<RepositorioGitHubDTO> cacheRepositorios;
    private long ultimaActualizacion;
    
    public ServicioGitHub() {
        this.restClient = RestClient.builder()
            .baseUrl(GITHUB_API_URL)
            .defaultHeader("Accept", "application/vnd.github.v3+json")
            .defaultHeader("User-Agent", "Portfolio-App")
            .build();
        this.cacheRepositorios = Collections.emptyList();
        this.ultimaActualizacion = 0;
    }
    
    public List<RepositorioGitHubDTO> obtenerRepositorios() {
        if (cacheValido()) {
            return cacheRepositorios;
        }
        
        return actualizarCache();
    }
    
    public RepositorioGitHubDTO obtenerRepositorioPorIndice(int indice) {
        List<RepositorioGitHubDTO> repos = obtenerRepositorios();
        
        if (indice < 1 || indice > repos.size()) {
            return null;
        }
        
        return repos.get(indice - 1);
    }
    
    private boolean cacheValido() {
        long tiempoTranscurrido = System.currentTimeMillis() - ultimaActualizacion;
        return !cacheRepositorios.isEmpty() && tiempoTranscurrido < CACHE_DURATION.toMillis();
    }
    
    private List<RepositorioGitHubDTO> actualizarCache() {
        try {
            log.info("Obteniendo repositorios de GitHub para usuario: {}", GITHUB_USERNAME);
            
            RepositorioGitHubDTO[] repos = restClient.get()
                .uri("/users/{username}/repos?sort=updated&per_page=100", GITHUB_USERNAME)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request, response) -> {
                    log.error("Error HTTP al obtener repos: {}", response.getStatusCode());
                    throw new RuntimeException("Error al obtener repos de GitHub: " + response.getStatusCode());
                })
                .body(RepositorioGitHubDTO[].class);
            
            if (repos != null) {
                log.info("Repositorios obtenidos: {}", repos.length);
                cacheRepositorios = Arrays.stream(repos)
                    .filter(r -> !REPO_EXCLUIDO.equalsIgnoreCase(r.getName()))
                    .toList();
                ultimaActualizacion = System.currentTimeMillis();
                log.info("Repositorios después de filtrar '{}': {}", REPO_EXCLUIDO, cacheRepositorios.size());
            }
            
            return cacheRepositorios;
            
        } catch (Exception e) {
            log.error("Excepción al obtener repos de GitHub: {}", e.getMessage(), e);
            if (!cacheRepositorios.isEmpty()) {
                return cacheRepositorios;
            }
            return Collections.emptyList();
        }
    }
}
