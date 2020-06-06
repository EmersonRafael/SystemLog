import { OnInit, Component, Input, Output , EventEmitter } from '@angular/core';
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
      {label: 'Upload', value: 'Upload'},
      {label: 'Manual', value: 'Manual'},
  ];
  

   }
    
  showProgressBar: boolean;
  arquivo: File;
  selectedType:string;
  textoLog:string;
  uploadedFiles: any[] = [];
  types: SelectItem[];


  @Input() dadosLog:any;
  @Input() listLogs:any[];
  @Output() respostaConsultar = new EventEmitter<number>();
  
  ngOnInit() {
    this.selectedType = "Upload";
    this.limpar();
  }

  selecionarArquivo(event) {

    var datatablecopy = Object.assign([], this.uploadedFiles);

    for(let file of event.target.files) {

      var jaTem = false;

      for (let i = 0; i <  datatablecopy.length; i++) {
        if (datatablecopy[i].name === file.name) {
          jaTem = true;
        }
      }

      if(!jaTem){
        datatablecopy.push(file);
      }else{
        this.messageService.add(
          { key:"erroModal",
          severity:'error',
          summary: 'Erro',
          detail: 'Já existe um arquivo com esse nome '+file.name});
      }
      
    }

    this.uploadedFiles = datatablecopy;

  }

  excluirArquivo(arquivo: File): void {

    var datatablecopy = Object.assign([], this.uploadedFiles);

    for (let i = 0; i <  datatablecopy.length; i++) {
     if (datatablecopy[i].name === arquivo.name) {
       datatablecopy.splice(i, 1);
     }
    }

    this.uploadedFiles = datatablecopy;
   }


salvarLogs(){
   
  this.showProgressBar = true;
  if(this.selectedType === 'Upload'){
    this.saveUpload();
  }else if(this.selectedType === 'Manual'){
    this.saveManual();
  } 

}

saveManual(){

  this.logService.salvarLogTexto(this.textoLog)
   .subscribe(data => {
   
    this.limpar();
    this.showProgressBar = false;
    this.messageService.add(
      { key:"erroModal",
      severity:'success',
      summary: 'Service Message',
      detail: 'Dados Salvos Com sucesso'});

    }, (error) => {

    this.messageService.add(
      { key:"erroModal",
      severity:'error',
      summary: 'Erro',
      detail: 'Erro ao tentar executar a operação'});

 });


 this.respostaConsultar.emit(2);

}

saveUpload(){

  this.logService.salvarUser()
  .subscribe((data: any) => {

    if(data != null){
        
      for (let i = 0; i <  this.uploadedFiles.length; i++) {
        const formData: FormData = new FormData();
        formData.append('file', this.uploadedFiles[i]);
        
        this.logService.salvarLogUpload(formData, data.idDadosLog)
        .subscribe(data => {
        
         }, (error) => {
     
         this.messageService.add(
           { key:"erroModal",
           severity:'error',
           summary: 'Erro',
           detail: 'Erro ao tentar executar a operação'});
     
      });
    
    } 

    this.limpar();
    this.showProgressBar = false;
    this.messageService.add(
      { key:"erroModal",
      severity:'success',
      summary: 'Service Message',
      detail: 'Dados Salvos Com sucesso'});

    }else{
      this.showProgressBar = false;
    }
     
  }, (error) => {
    this.erro();         
    this.showProgressBar = false;
  });


  this.respostaConsultar.emit(2);

}

  public erro() : void{
    this.messageService.add(
      { key:"erroModal",
      severity:'error',
      summary: 'Erro',
      detail: 'Algum erro aconteceu'});
  }   
  
  
  limpar(){
    this.uploadedFiles = []; 
    this.textoLog = "";
  }


  baixarArquivo(log: any){
    
    this.logService.baixarArquivoLog(log.idLog)
    .subscribe((data: any) => {
       importedSaveAs(data, log.nome);
    }, (error) => {
      this.erro();
    });
  }

}