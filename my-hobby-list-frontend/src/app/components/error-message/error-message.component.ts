import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-error-message',
  templateUrl: './error-message.component.html',
  styleUrls: ['./error-message.component.css']
})
export class ErrorMessageComponent {
  @Input() message: string = "";
  @Output() setErrorMessage: EventEmitter<any> = new EventEmitter()

  closeMessage() {
    this.setErrorMessage.emit("")
  }
}
