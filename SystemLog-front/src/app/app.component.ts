import { Component } from '@angular/core';
import {MenuItem} from 'primeng/api';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'assinador-front';

  items: MenuItem[];

  ngOnInit() {
    
    this.items = [
    {label: 'Cadastro', 
      items:[
      
        {label: 'Texto Certidão', icon: 'pi pi-fw pi-plus', routerLink:'cadastro-texto-certidao'},
        {label: 'Notificações', icon: 'pi pi-fw pi-bell', routerLink:'cadastro-notificacao'},

      ]    
    },
    {label: 'Open', icon: 'pi pi-fw pi-download'},
    {label: 'Undo', icon: 'pi pi-fw pi-refresh'}
  ];
  
  }


}
