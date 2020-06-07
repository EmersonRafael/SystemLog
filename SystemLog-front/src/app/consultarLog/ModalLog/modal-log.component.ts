import { OnInit, Component, Input, Output, EventEmitter } from '@angular/core';
import { MessageService, SelectItem } from 'primeng/api';
import { LogService } from '../log.service';
import { saveAs as importedSaveAs } from "file-saver";

@Component({
  selector: 'app-modal-log',
  templateUrl: './modal-log.component.html',
  styleUrls: ['./modal-log.component.css'],
  providers: [
    LogService,
    MessageService,
  ]
})

export class ModalLogComponent implements OnInit {

  constructor(
    private logService: LogService,
    private messageService: MessageService,
  ) {

    this.types = [
      { label: 'Upload', value: 'Upload' },
      { label: 'Manual', value: 'Manual' },
    ];


  }

  showProgressBar: boolean;
  arquivo: File;
  selectedType: string;
  textoLog: string;
  uploadedFiles: any[] = [];
  types: SelectItem[];


  @Input() dadosLog: any;
  @Input() listLogs: any[];
  @Output() respostaConsultar = new EventEmitter<number>();

  ngOnInit() {
    this.selectedType = "Upload";
    this.limpar();
  }

  exibeMenssagem(severity: string, summary: string, detail: string) {
    this.messageService.add(
      {
        key: "msModal",
        severity: severity,
        summary: summary,
        detail: detail
      });
  }


  selecionarArquivo(event) {

    var datatablecopy = Object.assign([], this.uploadedFiles);

    for (let file of event.target.files) {
      if (!this.validacoesArquivo(file, datatablecopy)) {
        datatablecopy.push(file);
      }
    }

    this.uploadedFiles = datatablecopy;

  }


  validacoesArquivo(file: any, datatablecopy: any): boolean {

    var jaTem = false;
    var extPermitidas = ['log', 'txt'];

    if (typeof extPermitidas.find(function (ext) { return file.name.split('.').pop() == ext; }) == 'undefined') {
      this.exibeMenssagem("warn", "Warn Message", "Extenssões permitidas .log e .txt");
      return true;
    }

    for (let i = 0; i < datatablecopy.length; i++) {
      if (datatablecopy[i].name === file.name) {
        jaTem = true;
        this.exibeMenssagem("warn", "Warn Message", "Já existe um arquivo com esse nome" + file.name);
      }
    }

    return jaTem;

  }


  excluirArquivo(arquivo: File): void {

    var datatablecopy = Object.assign([], this.uploadedFiles);

    for (let i = 0; i < datatablecopy.length; i++) {
      if (datatablecopy[i].name === arquivo.name) {
        datatablecopy.splice(i, 1);
      }
    }

    this.uploadedFiles = datatablecopy;
  }


  salvarLogs() {

    this.showProgressBar = true;
    if (this.selectedType === 'Upload') {
      if (this.uploadedFiles.length > 0) {
        this.saveUpload();
      } else {
        this.exibeMenssagem("error", "Info Message", "Favor anexar os arquivos de log");
      }
    } else if (this.selectedType === 'Manual') {
      if (this.textoLog.length > 0) {
        this.saveManual();
      } else {
        this.showProgressBar = false;
        this.exibeMenssagem("info", "Info Message", "Favor preencha o campo de texto");
      }
    }

  }

  saveManual() {

    this.logService.salvarLogTexto(this.textoLog)
      .subscribe(data => {
        this.limpar();
        this.showProgressBar = false;
        this.exibeMenssagem("success", "Service Message", "Dados Salvos Com sucesso");
      }, (error) => {
        this.exibeMenssagem("error", "Error Message", "Erro ao tentar executar a operação");
      });

    this.respostaConsultar.emit(1);
  }

  saveUpload() {

    this.logService.salvarUser()
      .subscribe((data: any) => {

        if (data != null) {

          for (let i = 0; i < this.uploadedFiles.length; i++) {
            const formData: FormData = new FormData();
            formData.append('file', this.uploadedFiles[i]);

            this.logService.salvarLogUpload(formData, data.idDadosLog)
              .subscribe(data => {

              }, (error) => {
                this.exibeMenssagem("error", "Error Message", "Erro ao tentar executar a operação");
              });

          }

          this.limpar();
          this.showProgressBar = false;
          this.exibeMenssagem("success", "Service Message", "Dados Salvos Com sucesso");
        } else {
          this.showProgressBar = false;
        }

      }, (error) => {
        this.exibeMenssagem("error", "Error Message", "Erro ao tentar executar a operação");
        this.showProgressBar = false;
      });


    this.respostaConsultar.emit(1);

  }

  limpar() {
    this.uploadedFiles = [];
    this.textoLog = "";
  }


  fechar() {
    this.uploadedFiles = [];
    this.textoLog = "";
    this.respostaConsultar.emit(2);
  }

  baixarArquivo(log: any) {
    this.logService.baixarArquivoLog(log.idLog)
      .subscribe((data: any) => {
        importedSaveAs(data, log.nome);
      }, (error) => {
        this.exibeMenssagem("error", "Error Message", "Erro ao tentar executar a operação");
      });
  }


  habilitarSalvar(): boolean {

    if (this.selectedType === 'Upload') {
      return this.uploadedFiles.length < 1;
    } else if (this.selectedType === 'Manual') {
      return this.textoLog.length < 1;
    }

    return false;
  }

}