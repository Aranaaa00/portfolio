import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TerminalResponse } from '../models/terminal-response';

@Injectable({
    providedIn: 'root',
})
export class TerminalService {
    private apiUrl = '/api/terminal';

    constructor(private http: HttpClient) {}

    help(): Observable<TerminalResponse> {
        return this.http.get<TerminalResponse>(`${this.apiUrl}/help`);
    }

    proyectos(): Observable<TerminalResponse> {
        return this.http.get<TerminalResponse>(`${this.apiUrl}/proyectos`);
    }

    about(): Observable<TerminalResponse> {
        return this.http.get<TerminalResponse>(`${this.apiUrl}/about`);
    }

    skills(): Observable<TerminalResponse> {
        return this.http.get<TerminalResponse>(`${this.apiUrl}/skills`);
    }

    contact(): Observable<TerminalResponse> {
        return this.http.get<TerminalResponse>(`${this.apiUrl}/contacto`);
    }

    abrir(indice: number): Observable<TerminalResponse> {
        return this.http.get<TerminalResponse>(`${this.apiUrl}/abrir/${indice}`);
    }
}
