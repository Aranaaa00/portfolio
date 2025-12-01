import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { TerminalService } from '../../services/terminal';
import { TerminalResponse } from '../../models/terminal-response';

@Component({
  selector: 'app-terminal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './terminal.html',
  styleUrls: ['./terminal.scss'],
})
export class Terminal {
    mensajeBienvenida = [
        '[+] Bienvenido al portfolio en modo terminal',
        '[*] Escribe "help" para ver los comandos disponibles',
        '[*] Comandos rápidos: p (proyectos), a (about), s (skills), c (contacto)',
    ];

    comandoActual = '';
    historial: Array<{texto: string, esComando: boolean, clase?: string}> = [];
    historialComandos: string[] = [];
    indiceHistorial: number = -1;
    modoClaro = false;

    constructor(
        private servicioTerminal: TerminalService,
        private cdr: ChangeDetectorRef
    ) {}

    /**
    * Método principal que se ejecuta cuando el usuario presiona Enter.
    * Captura el comando escrito, lo valida, lo agrega al historial visual
    * y delega el procesamiento real a procesarComando().
    */
    ejecutarComandoDesdeEvento(evento: Event): void {
        const input = evento.target as HTMLInputElement;
        const entrada = input.value.trim();
        const hayEntrada = entrada !== '';

        if (hayEntrada) {
            this.agregarAlHistorial(`guest@portfolio:~$ ${entrada}`, true);
            this.historialComandos.push(entrada);
            this.indiceHistorial = this.historialComandos.length;
            this.cdr.detectChanges();
            this.procesarComando(entrada);
            this.comandoActual = '';
        }
    }

    /**
     * Navega por el historial de comandos con las flechas arriba/abajo.
     * Arriba: comando anterior | Abajo: comando siguiente
     */
    navegarHistorial(direccion: 'arriba' | 'abajo'): void {
        const hayHistorial = this.historialComandos.length > 0;
        
        if (!hayHistorial) return;
        
        if (direccion === 'arriba') {
            if (this.indiceHistorial > 0) {
                this.indiceHistorial--;
            }
        } else {
            if (this.indiceHistorial < this.historialComandos.length) {
                this.indiceHistorial++;
            }
        }
        
        this.comandoActual = this.indiceHistorial < this.historialComandos.length 
            ? this.historialComandos[this.indiceHistorial] 
            : '';
    }

    /**
    * Analiza la entrada del usuario dividiéndola en comando y argumentos.
    * Por ejemplo: "proyectos --tecnologia angular" se divide en:
    * - comando: "proyectos"
    * - argumentos: ["--tecnologia", "angular"]
    * Luego obtiene el Observable del servicio correspondiente y se suscribe.
    */
    private procesarComando(entrada: string): void {
        const partes = entrada.split(' ');
        const comando = partes[0].toLowerCase();
        const argumentos = partes.slice(1);

        const metodoServicio = this.obtenerMetodoServicio(comando, argumentos);
        const comandoValido = metodoServicio !== null;

        if (comandoValido) {
            metodoServicio.subscribe({
                next: (respuesta) => this.mostrarRespuesta(respuesta),
                error: () => this.mostrarError()
            });
        }
    }

    /**
    * Mapea el comando escrito por el usuario al método correspondiente del servicio.
    * Soporta atajos (p → proyectos, a → about, etc.).
    * Devuelve un Observable<TerminalResponse> si el comando existe, o null si no.
    * 
    * Ejemplo: "proyectos" → servicioTerminal.proyectos()
    * Ejemplo: "p --tecnologia java" → servicioTerminal.proyectos({tecnologia: "java"})
    */
    private obtenerMetodoServicio(comando: string, argumentos: string[]): Observable<TerminalResponse> | null {
        if (comando === 'clear' || comando === 'cls') {
            this.limpiarHistorial();
            return null;
        }

        let resultado: Observable<TerminalResponse> | null = null;

        switch (comando) {
            case 'clear':
            case 'cls':
                this.limpiarHistorial();
                return null;
            case 'help':
            case 'h':
                resultado = this.servicioTerminal.help();
                break;
            
            case 'about':
            case 'a':
                resultado = this.servicioTerminal.about();
                break;
            
            case 'skills':
            case 's':
                resultado = this.servicioTerminal.skills();
                break;
            
            case 'proyectos':
            case 'p':
                resultado = this.manejarComandoProyectos(argumentos);
                break;
            
            case 'contacto':
            case 'c':
                resultado = this.servicioTerminal.contact();
                break;
            
            case 'stats':
                resultado = this.servicioTerminal.stats();
                break;
        }
        
        return resultado;
    }

    /**
    * Gestiona el comando "proyectos" que puede tener filtros opcionales.
    * Si no hay argumentos, devuelve todos los proyectos.
    * Si hay argumentos (ej: --tecnologia angular), los parsea y los envía como filtros.
    * 
    * Ejemplo sin filtros: "proyectos" → GET /api/terminal/proyectos
    * Ejemplo con filtros: "proyectos --tecnologia angular" → GET /api/terminal/proyectos?tecnologia=angular
    */
    private manejarComandoProyectos(argumentos: string[]): Observable<TerminalResponse> {
        const hayArgumentos = argumentos.length > 0;
        const resultado = hayArgumentos 
            ? this.servicioTerminal.proyectos(this.parsearArgumentos(argumentos))
            : this.servicioTerminal.proyectos();
        
        return resultado;
    }

    /**
    * Convierte un array de argumentos estilo Unix en un objeto de filtros.
    * Busca patrones "--clave valor" y los convierte en {clave: valor}.
    * 
    * Ejemplo: ["--tecnologia", "angular", "--año", "2024"]
    * Resultado: {tecnologia: "angular", año: "2024"}
    */
    private parsearArgumentos(argumentos: string[]): any {
        const filtros: any = {};
        
        for (let i = 0; i < argumentos.length; i++) {
            const esFlag = argumentos[i].startsWith('--');
            if (esFlag) {
                const clave = argumentos[i].substring(2);
                const valor = argumentos[i + 1];
                const hayValor = valor && !valor.startsWith('--');
        
                if (hayValor) {
                    filtros[clave] = valor;
                    i++;
                }
            }
        }
        return filtros;
    }

    /**
    * Procesa la respuesta del backend (TerminalDTO).
    * Extrae el campo "salida" que contiene el texto ASCII formateado,
    * lo divide por saltos de línea y agrega cada línea al historial.
    * 
    * El historial se renderiza automáticamente en el HTML con *ngFor.
    */
    private mostrarRespuesta(respuesta: TerminalResponse): void {
        // Línea vacía para separación visual
        this.agregarAlHistorial('', false, 'bloque-respuesta');

        const lineas = respuesta.salida.split('\n');
        lineas.forEach(linea => this.agregarAlHistorial(linea, false, 'linea-respuesta'));
        this.cdr.detectChanges();
        this.hacerScrollAbajo();
    }

    /**
    * Fuerza el scroll del historial hacia abajo para mostrar siempre el último contenido.
    * Se ejecuta después de agregar nuevas líneas al historial.
    */
    private hacerScrollAbajo(): void {
        setTimeout(() => {
            const historial = document.querySelector('.historial-comandos');
            if (historial) {
                historial.scrollTop = historial.scrollHeight;
            }
        }, 0);
    }

    /**
    * Muestra un mensaje de error cuando falla la conexión con el backend.
    * Esto ocurre cuando el servidor está caído o hay problemas de red.
    */
    private mostrarError(): void {
        this.agregarAlHistorial('[!] Error: No se pudo conectar con el servidor', false, '');
    }

    /**
    * Punto único para agregar líneas al historial.
    * Mantiene la separación de responsabilidades y facilita futuras mejoras
    * (como límite de líneas, timestamps, colores por tipo, etc.).
    */
    private agregarAlHistorial(linea: string, esComando: boolean = false, clase: string = ''): void {
        this.historial.push({
            texto: linea,
            esComando: esComando,
            clase: clase
        });
    }

    /**
    * Alterna entre modo claro y oscuro.
    * Cambia la variable local para controlar el icono (☀/☽)
    * y añade/quita la clase CSS "modo-claro" del body para aplicar estilos.
    */
    cambiarTema() {
        this.modoClaro = !this.modoClaro;
        document.body.classList.toggle('modo-claro');
    }

    /**
     * Limpia todo el historial de comandos, dejando solo el mensaje de bienvenida visible.
     * Simula el comportamiento del comando 'clear' en terminales Unix.
     */
    private limpiarHistorial(): void {
        this.historial = [];
        this.hacerScrollAbajo();
    }
}
