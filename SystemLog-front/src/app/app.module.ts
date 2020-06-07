import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './core/navbar/navbar.component';
import { LogComponent } from './consultarLog/log.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ProgressBarModule} from 'primeng/progressbar';
import {ToastModule} from 'primeng/toast';
import {FieldsetModule} from 'primeng/fieldset';
import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {ButtonModule} from 'primeng/button';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule }   from '@angular/forms';
import {DialogModule} from 'primeng/dialog';
import { ModalLogComponent } from './consultarLog/ModalLog/modal-log.component';
import { TableModule } from 'primeng/table';
import {SelectButtonModule} from 'primeng/selectbutton';
import {CalendarModule} from 'primeng/calendar';
import {TooltipModule} from 'primeng/tooltip';
import {ConfirmDialogModule} from 'primeng/confirmdialog';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    LogComponent,
    ModalLogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ToastModule,
    FieldsetModule,
    ProgressBarModule,
    MessagesModule,
    MessageModule,
    ButtonModule,
    HttpClientModule,
    FormsModule,
    DialogModule,
    TableModule,
    SelectButtonModule,
    CalendarModule,
    TooltipModule,
    ConfirmDialogModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
