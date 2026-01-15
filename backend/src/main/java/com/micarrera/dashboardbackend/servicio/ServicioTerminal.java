package com.micarrera.dashboardbackend.servicio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.micarrera.dashboardbackend.dto.RepositorioGitHubDTO;

@Service
public class ServicioTerminal {
    
    public String formatearAyuda() {
        return """
               Comandos disponibles:
                 help (h)              - Muestra esta ayuda
                 clear (cls)           - Limpia la pantalla
                 proyectos (p)         - Lista repositorios de GitHub
                 abrir <id>            - Ver detalle del proyecto (ej: abrir 1)
                 about (a)             - Información sobre mí
                 skills (s)            - Mis habilidades técnicas
                 contacto (c)          - Información de contacto
               """;
    }
    
    public String formatearRepositoriosGitHub(List<RepositorioGitHubDTO> repos) {
        StringBuilder sb = new StringBuilder();
        
        if (repos.isEmpty()) {
            sb.append("[!] No se pudieron obtener los repositorios de GitHub");
            return sb.toString();
        }
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("[!] MIS PROYECTOS EN GITHUB\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        
        for (int i = 0; i < repos.size(); i++) {
            RepositorioGitHubDTO repo = repos.get(i);
            
            sb.append("[").append(i + 1).append("] ");
            sb.append(repo.getName());
            
            if (repo.getLanguage() != null) {
                sb.append(" (").append(repo.getLanguage()).append(")");
            }
            sb.append("\n");
            
            if (repo.getDescription() != null && !repo.getDescription().isBlank()) {
                sb.append("    ").append(repo.getDescription()).append("\n");
            }
            
            sb.append("    <a href=\"").append(repo.getHtmlUrl()).append("\" target=\"_blank\"><u>GitHub</u></a>\n\n");
        }
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sb.append("\n[~] Usa 'abrir <id>' para ver detalle (ej: abrir 1)");
        
        return sb.toString();
    }
    
    public String formatearDetalleRepositorio(RepositorioGitHubDTO repo) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("[!] ").append(repo.getName().toUpperCase()).append("\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        
        if (repo.getDescription() != null && !repo.getDescription().isBlank()) {
            sb.append(repo.getDescription()).append("\n\n");
        }
        
        if (repo.getLanguage() != null) {
            sb.append("[#] Lenguaje: ").append(repo.getLanguage()).append("\n");
        }
        
        sb.append("\n[>>] <a href=\"").append(repo.getHtmlUrl()).append("\" target=\"_blank\">Abrir en GitHub</a>\n\n");
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sb.append("\n[~] Usa 'proyectos' para volver");
        
        return sb.toString();
    }
    
    public String formatearAbout() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("===========================================\n");
        sb.append("  ABOUT\n");
        sb.append("===========================================\n\n");
        
        sb.append("[+] Nombre: Manuel\n");
        sb.append("[*] Estudios: 2do Desarrollo de Aplicaciones Web\n");
        sb.append("[>] Siguiente paso: sumar en un equipo de desarrollo\n\n");
        
        sb.append("[#] Stack Principal:\n");
        sb.append("    - Backend: Java, Spring Boot\n");
        sb.append("    - Frontend: Angular, TypeScript\n");
        sb.append("    - DevOps: Docker, Git\n");
        sb.append("    - He tocado MERN stack\n\n");
        
        sb.append("[~] Ubicacion: Espana\n");
        sb.append("[~] Idiomas: Espanol (nativo), Ingles (B1)\n\n");
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sb.append("\n[~] Usa 'skills' para ver habilidades");

        return sb.toString();
    }
    
    public String formatearSkills() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("[!] HABILIDADES TECNICAS\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        
        sb.append("[#] Backend:\n");
        sb.append("   Java                [========  ] 80%\n");
        sb.append("   Spring Boot         [========  ] 80%\n");
        sb.append("   PostgreSQL          [=======   ] 70%\n");
        sb.append("   Node.js             [=====     ] 50%\n\n");
        
        sb.append("[#] Frontend:\n");
        sb.append("   Angular             [========  ] 80%\n");
        sb.append("   HTML/CSS            [========= ] 90%\n");
        sb.append("   TypeScript          [=======   ] 70%\n");
        sb.append("   React               [====      ] 40%\n\n");
        
        sb.append("[#] DevOps & Tools:\n");
        sb.append("   Git/GitHub          [========  ] 80%\n");
        sb.append("   Docker              [======    ] 60%\n");
        sb.append("   Linux               [======    ] 60%\n\n");
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sb.append("\n[~] Usa 'contacto' para ver como contactarme");

        return sb.toString();
    }

    public String formatearContacto() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("[!] CONTACTO\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");
        
        sb.append("[#] Email:\n");
        sb.append("    manuelaranajob@gmail.com\n\n");
        
        sb.append("[#] LinkedIn:\n");
        sb.append("    <a href=\"https://linkedin.com/in/arana00/\" target=\"_blank\"><u>linkedin.com/in/arana00</u></a>\n\n");
        
        sb.append("[#] GitHub:\n");
        sb.append("    <a href=\"https://github.com/Aaranaa00\" target=\"_blank\"><u>github.com/Aaranaa00</u></a>\n\n");
        
        sb.append("[#] CV:\n");
        sb.append("    <a href=\"/api/terminal/contacto/cv\" download=\"curriculum.pdf\"><u>Descargar CV (PDF)</u></a>\n\n");

        sb.append("[>] Buscando practicas (3 meses)\n");
        sb.append("[>] Ubicado en Espana\n\n");
        
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sb.append("\n[~] Usa 'about' para conocerme mejor");
        
        return sb.toString();
    }

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
        String[] comandosValidos = {"help", "proyectos", "about", "skills", "contacto", "abrir"};
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
            sb.append("Quisiste decir '").append(sugerencia).append("'?\n\n");
        }
        
        sb.append("Usa 'help' para ver comandos disponibles");
        
        return sb.toString();
    }
}
