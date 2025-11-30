import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TerminalResponse } from '../models/terminal-response';

@Injectable({
    providedIn: 'root',
})
export class TerminalService {
    private apiUrl = 'http://localhost:8080/api/terminal';

    constructor(private http: HttpClient) {}

    help(): Observable<TerminalResponse> {
        return this.ejecutarComando('help');
    }

    proyectos(filtros?: any): Observable<TerminalResponse> {
        return this.ejecutarComando('proyectos', filtros);
    }

    about(): Observable<TerminalResponse> {
        return this.ejecutarComando('about');
    }

    skills(): Observable<TerminalResponse> {
        return this.ejecutarComando('skills');
    }

    contact(): Observable<TerminalResponse> {
        return this.ejecutarComando('contact');
    }

    stats(): Observable<TerminalResponse> {
        return this.ejecutarComando('stats');
    }

    ejecutarComando(comando: string, params?: any): Observable<TerminalResponse> {
        return this.http.get<TerminalResponse>(`${this.apiUrl}/${comando}`, {
        params,
        });
    }
}
