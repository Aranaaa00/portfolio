import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

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
    '[*] Comandos rápidos: p (proyectos), a (about), s (skills), c (contact)',
  ];

  comandoActual = '';
  historial: string[] = [];

  ejecutarComando(): void {
    if (!this.comandoActual.trim()) return;

    this.historial.push(`guest@portfolio:~$ ${this.comandoActual}`);
    this.historial.push(`[*] Comando "${this.comandoActual}" ejecutado`);
    
    this.comandoActual = '';
  }
}
