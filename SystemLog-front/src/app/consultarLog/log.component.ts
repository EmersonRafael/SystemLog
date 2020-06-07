import { OnInit, Component } from '@angular/core';
import { LogService } from './log.service';
import { MessageService , ConfirmationService} from 'primeng/api';


@Component({
  selector: 'app-log',
  templateUrl: './log.component.html',
  styleUrls: ['./log.component.css'],
  providers: [
    LogService,
    MessageService,
    ConfirmationService
  ]
})

export class LogComponent implements OnInit {

  constructor(
    private logService: LogService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) { }

  listaLogs: any[] = []
  ip: string;
  userAgent: string;
  dtInsersaoInicio: Date;
  dtInsersaoFim: Date;
  dadosLog: any;
  listLogs: any[];
  displayModal: boolean;
  showProgressBar: boolean;


  ngOnInit() {
    this.displayModal = false;
    this.showProgressBar = false;
    this.consultarLogs();
  }

  exibeMenssagem(severity: string, summary: string, detail: string) {
    this.messageService.add(
      {
        key: "msConsulta",
        severity: severity,
        summary: summary,
        detail: detail
      });
  }

  dataFormatada(data: Date): string {

    var diaN = data.getDate();
    var dia = diaN.toString();
    if (diaN < 10) {
      dia = 0 + diaN.toString();
    }

    var mesN = data.getMonth() + 1;
    var mes = mesN.toString();
    if (mesN < 10) {
      mes = 0 + mesN.toString();
    }

    var HorasN = data.getHours();
    var hora = HorasN.toString();
    if (HorasN < 10) {
      hora = 0 + HorasN.toString();
    }

    var minutosN = data.getMinutes();
    var minutos = minutosN.toString();
    if (minutosN < 10) {
      minutos = 0 + minutosN.toString();
    }

    var datestring = this.dtInsersaoInicio.getFullYear() + "-" + mes + "-" + dia + " " + hora + ":" + minutos + ":00";

    return datestring;
  }


  consultarLogs() {

    this.showProgressBar = true;
    let query: any = {};
    this.listaLogs = []

    if (this.dtInsersaoInicio != null) {
      query.dtInsersaoInicio = this.dataFormatada(this.dtInsersaoInicio);
    }

    if (this.dtInsersaoFim != null) {
      query.dtInsersaoFim = this.dataFormatada(this.dtInsersaoFim);
    }

    if (this.ip != null && this.ip != '') {
      query.ip = this.ip;
    }

    if (this.userAgent != null && this.userAgent != '') {
      query.userAgent = this.userAgent;
    }

    this.logService.consultarLog(query)
      .subscribe((data: any) => {
        this.showProgressBar = false;
        this.listaLogs = data;
      }, (error) => {
        this.showProgressBar = false;
        this.exibeMenssagem("error", "Error Message", "Erro ao tentar executar a operação");
      });

  }


  limparFiltro() {
    this.ip = "";
    this.userAgent = "";
    this.dtInsersaoInicio = null;
    this.dtInsersaoFim = null;
  }

  showModalDialog() {
    this.dadosLog = null;
    this.displayModal = true;
  }

  confirm(dados: any) {
    this.confirmationService.confirm({
        message: 'Deseja realmente deletar o registro?',
        accept: () => {
           this.excluirLog(dados);
        }
    });
  }

  excluirLog(dados: any) {

    this.showProgressBar = true;

    this.logService.excluirLog(dados.idDadosLog)
      .subscribe((data: any) => {
        this.showProgressBar = false;
        this.exibeMenssagem("success", "Service Message", "Dados excluídos com sucesso");
        this.consultarLogs();
      }, (error) => {
        this.showProgressBar = false;
        this.exibeMenssagem("error", "Error Message", "Erro ao tentar executar a operação");
      });

  }

  visualizar(dados: any) {
    this.dadosLog = dados;
    this.consultarListaLog();
  }


  consultarListaLog() {

    let query: any = {};
    query.idDados = this.dadosLog.idDadosLog;

    this.logService.consultarListaLogs(query)
      .subscribe((data: any) => {
        this.listLogs = data;
        this.displayModal = true;
      }, (error) => {
        this.exibeMenssagem("error", "Error Message", "Erro ao tentar executar a operação");
      });

  }



  public pesquisarNovamente($event) {
    this.displayModal = false;

    if($event == 1){
      this.exibeMenssagem("success", "Service Message", "Dados Salvos Com sucesso");
      setTimeout(() => { this.consultarLogs(); }, 2000);
    }
    
  }


}