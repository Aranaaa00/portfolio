package com.micarrera.dashboardbackend.servicio;

import org.springframework.stereotype.Service;
import com.micarrera.dashboardbackend.entidad.Proyecto;
import java.util.List;

@Service
public class ServicioTerminal {
    
    public String formatearAyuda() {
    String comandos = "Comandos disponibles:\n" +
                     "  help (h)                    - Muestra esta ayuda\n" +
                     "  clear (cls)                 - Limpia la pantalla\n" +
                     "  proyectos (p)               - Lista todos los proyectos\n" +
                     "  proyectos --estado completado   - Filtra por estado del proyecto\n" +
                     "  proyectos --tecnologia angular  - Filtra por tecnología usada\n" +
                     "  proyectos --estado completado --tecnologia angular - Combina filtros\n" +
                     "  about (a)                   - Información sobre mí\n" +
                     "  skills (s)                  - Mis habilidades técnicas\n" +
                     "  stats                       - Estadísticas del portfolio\n" +
                     "  contacto (c)                 - Información de contacto";
    return comandos;
}
    
    public String formatearListaProyectos(List<Proyecto> proyectos) {
        StringBuilder sb = new StringBuilder();
        
        if (proyectos.isEmpty()) {
            sb.append("No hay proyectos disponibles");
        } else {
            sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            sb.append("Proyectos encontrados: ").append(proyectos.size()).append("\n");
            sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
            
            for (int i = 0; i < proyectos.size(); i++) {
                Proyecto p = proyectos.get(i);
                
                sb.append("[ID:").append(p.getId()).append("] ");
                sb.append(obtenerIconoEstado(p.getEstado())).append(" ");
                sb.append(p.getNombreProyecto()).append("\n");
                sb.append("    ").append(p.getEstado());
                sb.append(" | ").append(p.getTecnologias()).append("\n\n");
            }
            
            sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
        
        sb.append("\n[~] Usa 'proyectos/ID' para ver detalle | 'help' para más comandos");

        return sb.toString();
    }
    
    public String formatearDetalleProyecto(Proyecto p) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("[!] DETALLE DEL PROYECTO\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        
        sb.append("Nombre: ").append(p.getNombreProyecto()).append("\n");
        sb.append("Estado: ").append(obtenerIconoEstado(p.getEstado())).append(" ").append(p.getEstado()).append("\n");
        sb.append("Tecnologías: ").append(p.getTecnologias()).append("\n");
        sb.append("Inicio: ").append(p.getFechaInicio()).append("\n\n");
        
        sb.append("Descripción:\n");
        sb.append(p.getDescripcion()).append("\n\n");
        
        if (p.getUrlGithub() != null) {
            sb.append("[>] GitHub: ").append(p.getUrlGithub()).append("\n");
        }
        
        if (p.getUrlDemo() != null) {
            sb.append("[>] Demo: ").append(p.getUrlDemo()).append("\n");
        }
        
        sb.append("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        sb.append("\n[~] Usa 'proyectos' para volver | 'help' para más comandos");

        return sb.toString();
    }
    
    public String formatearAbout() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("===========================================\n");
        sb.append("  ABOUT\n");
        sb.append("===========================================\n\n");
        
        sb.append("[+] Nombre: Manuel\n");
        sb.append("[*] Estudios: 2º Desarrollo de Aplicaciones Web\n");
        sb.append("[>] Siguiente paso: sumar en un equipo de desarrollo\n\n");
        
        sb.append("[#] Stack Principal:\n");
        sb.append("    - Backend: Spring Boot + PostgreSQL\n");
        sb.append("    - Frontend: Angular 21\n");
        sb.append("    - DevOps: Docker, Git, GitHub\n");
        sb.append("    - He tocado MERN stack\n\n");
        
        sb.append("[~] Ubicación: España\n");
        sb.append("[~] Idiomas: Español (nativo), Inglés (B1 sin titulación)\n\n");
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        sb.append("\n[~] Usa 'skills' para ver habilidades | 'help' para más comandos");

        return sb.toString();
    }
    
    public String formatearSkills() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("[!] HABILIDADES TÉCNICAS\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        
        sb.append("[#] Backend:\n");
        sb.append("   Java                [████████░░] 80%\n");
        sb.append("   Spring Boot         [████████░░] 80%\n");
        sb.append("   PostgreSQL          [███████░░░] 70%\n");
        sb.append("   Node.js             [█████░░░░░] 50%\n\n");
        
        sb.append("[#] Frontend:\n");
        sb.append("   Angular 21          [████████░░] 80%\n");
        sb.append("   HTML/CSS            [█████████░] 90%\n");
        sb.append("   TypeScript          [███████░░░] 70%\n");
        sb.append("   React               [████░░░░░░] 40%\n\n");
        
        sb.append("[#] DevOps & Tools:\n");
        sb.append("   Git/GitHub          [████████░░] 80%\n");
        sb.append("   Docker              [██████░░░░] 60%\n");
        sb.append("   Linux               [██████░░░░] 60%\n\n");
        
        sb.append("[#] Otras:\n");
        sb.append("   REST APIs           [████████░░] 80%\n");
        sb.append("   SQL                 [███████░░░] 70%\n");
        sb.append("   Python              [█████░░░░░] 50%\n\n");
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        sb.append("\n[~] Usa 'stats' para estadísticas | 'help' para más comandos");

        return sb.toString();
    }
    
    public String formatearMensajeError(String mensaje) {
        return "Error: " + mensaje;
    }
    
    private String obtenerIconoEstado(String estado) {
        if (estado == null) {
            return "?";
        }
        
        String icono = switch (estado) {
            case "Completado" -> "✓";
            case "En desarrollo" -> "⚙";
            case "Pausado" -> "⏸";
            default -> "•";
        };
        
        return icono;
    }

    public String formatearEstadisticas(List<Proyecto> proyectos) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("[!] ESTADÍSTICAS DEL PORTFOLIO\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        
        // Total proyectos
        sb.append("[#] Total de proyectos: ").append(proyectos.size()).append("\n\n");
        
        // Proyectos por estado
        long completados = proyectos.stream()
            .filter(p -> "Completado".equals(p.getEstado()))
            .count();
        long enDesarrollo = proyectos.stream()
            .filter(p -> "En desarrollo".equals(p.getEstado()))
            .count();
        long pausados = proyectos.stream()
            .filter(p -> "Pausado".equals(p.getEstado()))
            .count();
        
        sb.append("[#] Por estado:\n");
        sb.append("    ✓ Completados:      ").append(completados).append("\n");
        sb.append("    ⚙ En desarrollo:    ").append(enDesarrollo).append("\n");
        sb.append("    ⏸ Pausados:         ").append(pausados).append("\n\n");
        
        // Proyectos destacados
        long destacados = proyectos.stream()
            .filter(Proyecto::getDestacado)
            .count();
        sb.append("[#] Destacados: ").append(destacados).append("\n\n");
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        sb.append("\n[~] Usa 'proyectos' para ver lista | 'help' para más comandos");

        return sb.toString();
    }

    // Método que srive para medir cuántas letras difieren entre dos palabras
    private int calcularDistanciaLevenshtein(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        
        for (int i = 0; i <= s1.length(); i++) {
            dp[i][0] = i;
        }
        
        for (int j = 0; j <= s2.length(); j++) {
            dp[0][j] = j;
        }
        
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                int costo = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                
                dp[i][j] = Math.min(
                    Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                    dp[i - 1][j - 1] + costo
                );
            }
        }
        
        return dp[s1.length()][s2.length()];
    }

    private String sugerirComando(String comandoInvalido) {
        String[] comandosValidos = {"help", "proyectos", "about", "skills", "stats", "contacto"};
        String sugerencia = null;
        int menorDistancia = Integer.MAX_VALUE;
        
        for (String comandoValido : comandosValidos) {
            int distancia = calcularDistanciaLevenshtein(comandoInvalido, comandoValido);
            
            if (distancia < menorDistancia && distancia <= 3) {
                menorDistancia = distancia;
                sugerencia = comandoValido;

                if (distancia == 1) 
                    break;
            }
        }
        
        return sugerencia;
    }

    
    public String formatearComandoInvalido(String comando) {
        String sugerencia = sugerirComando(comando);
        StringBuilder sb = new StringBuilder();
        
        sb.append("[!] Comando no encontrado: '").append(comando).append("'\n\n");
        
        if (sugerencia != null) {
            sb.append("¿Quisiste decir '").append(sugerencia).append("'?\n\n");
        }
        
        sb.append("Usa 'help' para ver comandos disponibles");
        
        return sb.toString();
    }

    public String formatearContacto() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("[!] INFORMACIÓN DE CONTACTO\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        
        sb.append("Si te interesa contactar, estas son las vías:\n\n");
        
        sb.append("[#] Email:\n");
        sb.append("    manuelaranajob@gmail.com\n");
        sb.append("    Suelo responder en 24-48h\n\n");
        
        sb.append("[#] LinkedIn:\n");
        sb.append("    https://linkedin.com/in/arana00/\n");
        sb.append("    Para networking o propuestas profesionales\n\n");
        
        sb.append("[#] GitHub:\n");
        sb.append("    https://github.com/Aaranaa00\n");
        sb.append("    Revisa mi código o colabora en proyectos\n\n");
        
        sb.append("[>] Actualmente buscando prácticas (3 meses)\n");
        sb.append("[>] Ubicado en España\n\n");
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sb.append("\n[~] Usa 'about' para conocerme | 'proyectos' para ver mi trabajo");
        
        return sb.toString();
    }
}
