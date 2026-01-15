package com.micarrera.dashboardbackend.controlador;

import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micarrera.dashboardbackend.dto.RepositorioGitHubDTO;
import com.micarrera.dashboardbackend.dto.TerminalDTO;
import com.micarrera.dashboardbackend.servicio.ServicioGitHub;
import com.micarrera.dashboardbackend.servicio.ServicioTerminal;

@RestController
@RequestMapping("/api/terminal")
@CrossOrigin(origins = "*")
public class ControladorTerminal {

    private final ServicioGitHub servicioGitHub;
    private final ServicioTerminal servicioTerminal;
    
    public ControladorTerminal(ServicioGitHub servicioGitHub, ServicioTerminal servicioTerminal) {
        this.servicioGitHub = servicioGitHub;
        this.servicioTerminal = servicioTerminal;
    }

    @GetMapping({"/help", "/h"})
    public TerminalDTO help() {
        return new TerminalDTO(servicioTerminal.formatearAyuda(), "exito", null);
    }

    @GetMapping({"/proyectos", "/p"})
    public TerminalDTO proyectos() {
        List<RepositorioGitHubDTO> repos = servicioGitHub.obtenerRepositorios();
        return new TerminalDTO(servicioTerminal.formatearRepositoriosGitHub(repos), "exito", repos);
    }

    @GetMapping({"/about", "/a"})
    public TerminalDTO about() {
        return new TerminalDTO(servicioTerminal.formatearAbout(), "info", null);
    }

    @GetMapping({"/skills", "/s"})
    public TerminalDTO skills() {
        return new TerminalDTO(servicioTerminal.formatearSkills(), "info", null);
    }

    @GetMapping({"/contacto", "/c"})
    public TerminalDTO contact() {
        return new TerminalDTO(servicioTerminal.formatearContacto(), "info", null);
    }

    @GetMapping("/abrir/{indice}")
    public TerminalDTO abrirProyecto(@PathVariable int indice) {
        RepositorioGitHubDTO repo = servicioGitHub.obtenerRepositorioPorIndice(indice);
        
        if (repo == null) {
            return new TerminalDTO("[!] Proyecto no encontrado. Usa 'proyectos' para ver la lista", "error", null);
        }
        
        return new TerminalDTO(servicioTerminal.formatearDetalleRepositorio(repo), "exito", repo);
    }

    @GetMapping("/contacto/cv")
    public ResponseEntity<Resource> descargarCv() {
        Resource file = new ClassPathResource("static/curriculum.pdf");

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=curriculum.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(file);
    }
    
    @GetMapping("/{comando}")
    public TerminalDTO comandoInvalido(@PathVariable String comando) {
        return new TerminalDTO(servicioTerminal.formatearComandoInvalido(comando), "error", null);
    }
}
