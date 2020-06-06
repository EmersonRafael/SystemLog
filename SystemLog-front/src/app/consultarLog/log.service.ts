import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LOG_API } from 'src/app.api';
import { Params } from '@angular/router';

@Injectable()
export class LogService{
  
    constructor(private http: HttpClient) { }

    salvarUser() {
        var vazio = { nome : "" };
        return this.http.post(`${LOG_API}/api/salvar-user/`, vazio);
    }

    salvarLogUpload(formData : FormData, idUser : number) {
        return this.http.post(`${LOG_API}/api/salvar-log/${idUser}`, formData);
    }

    salvarLogTexto(texto : string) {
        return this.http.post(`${LOG_API}/api/salvar-log-texto/`, texto);
    }
    
    consultarLog(params: Params) {
        return this.http.get(`${LOG_API}/api/consultar-log/`, {params});
    }
    
    excluirLog(idUser : number) {
        return this.http.delete(`${LOG_API}/api/excluir-log/${idUser}`);
    }

    consultarListaLogs(params: Params) {
        return this.http.get(`${LOG_API}/api/consultar-log-list/`, {params});
    }

    baixarArquivoLog(idLog:number){
        return this.http.get(`${LOG_API}/api/baixar-arquivo-log/${idLog}`,{ responseType: "blob"});
    }

}