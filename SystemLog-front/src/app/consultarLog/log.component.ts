import { OnInit, Component, ViewChild } from '@angular/core';
import { LogService } from './log.service';
import { MessageService } from 'primeng/api';

@Component({
    selector: 'app-log',
    templateUrl: './log.component.html',
    styleUrls: ['./log.component.css'],
    providers: [
      LogService,
        MessageService,
    ]
  })
  
  export class LogComponent implements OnInit {

  constructor(
    private logService: LogService,
    private messageService: MessageService,
  ) { }
    
  listaLogs: any[] = []
  ip : string;
  userAgent : string;
  dtInsersaoInicio : Date;
  dtInsersaoFim : Date;
  dadosLog:any;
  listLogs: any[];
  displayModal: boolean;
  showProgressBar: boolean;
  

  ngOnInit() {
    this.displayModal = false;
    this.showProgressBar = false;
    this.consultarLogs();
  }


dataFormatada(data:Date) : string{

    var diaN = data.getDate();
    var dia = diaN.toString();
    if(diaN < 10){
      dia = 0+diaN.toString();
    }

    var mesN = data.getMonth()+1;
    var mes = mesN.toString();
    if(mesN < 10){
      mes = 0+mesN.toString();
    }

    var HorasN = data.getHours();
    var hora = HorasN.toString();
    if(HorasN < 10){
      hora = 0+HorasN.toString();
    }

    var minutosN = data.getMinutes();
    var minutos = minutosN.toString();
    if(minutosN < 10){
      minutos = 0+minutosN.toString();
    }

  var datestring = this.dtInsersaoInicio.getFullYear()+"-"+mes+"-"+dia+" "+hora +":"+minutos+":00";

  return datestring;
}


consultarLogs(){

  this.showProgressBar = false;
  let query: any = {};
  this.listaLogs = []

  if(this.dtInsersaoInicio != null){
    query.dtInsersaoInicio = this.dataFormatada(this.dtInsersaoInicio);
  }

  if(this.dtInsersaoFim != null){
    query.dtInsersaoFim = this.dataFormatada(this.dtInsersaoFim);
  }
 
  if(this.ip != null && this.ip != ''){
    query.ip = this.ip;
  }
  
  if(this.userAgent != null && this.userAgent != ''){
    query.userAgent = this.userAgent; 
  }

  this.logService.consultarLog(query)
  .subscribe((data: any) => {
   this.showProgressBar = false;
   this.listaLogs = data;
   }, (error) => {
   this.messageService.add(
     { key:"myKey",
     severity:'error',
     summary: 'Erro',
     detail: 'Erro ao tentar executar a operação'});
});


}


limparFiltro(){
  this.ip = "";
  this.userAgent = "";
  this.dtInsersaoInicio = null;
  this.dtInsersaoFim = null;
}

  showModalDialog() {
    this.dadosLog = null;
    this.displayModal = true;
}

  excluirLog(dados:any){

    this.showProgressBar = false;
    
    this.logService.excluirLog(dados.idDadosLog)
    .subscribe((data: any) => {
     this.showProgressBar = false;
     this.messageService.add(
      { key:"myKey",
      severity:'success',
      summary: 'Service Message',
      detail: 'Dados excluídos com sucesso'});
      this.consultarLogs();
     }, (error) => {
     this.messageService.add(
       { key:"myKey",
       severity:'error',
       summary: 'Erro',
       detail: 'Erro ao tentar executar a operação'});
  });
  


  }

  visualizar(dados:any){
    this.dadosLog = dados;
    this.consultarListaLog();
  }

  public erro() : void{
    this.messageService.add(
      { key:"myKey",
      severity:'error',
      summary: 'Erro',
      detail: 'Algum erro aconteceu'});
  }    


  consultarListaLog(){

    let query: any = {};
    query.idDados = this.dadosLog.idDadosLog;

    this.logService.consultarListaLogs(query)
    .subscribe((data: any) => {
     this.listLogs = data;
     this.displayModal = true;
     }, (error) => {
      this.erro();
  });

  }



  public pesquisarNovamente($event){
    
    this.displayModal = false;
    this.messageService.add(
      { key:"myKey",
      severity:'success',
      summary: 'Service Message',
      detail: 'Dados Salvos Com sucesso'});


    setTimeout(() =>
      {
        this.consultarLogs();
      },
      2000);
  }


  }