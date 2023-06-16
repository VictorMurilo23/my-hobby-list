import { Component } from '@angular/core';
import ErrorMessage from 'src/app/abstract/ErrorMessage';
import IRegister from 'src/app/interfaces/IRegister';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-registry',
  templateUrl: './registry.component.html',
  styleUrls: ['./registry.component.css']
})
export class RegistryComponent extends ErrorMessage {
  private email: string = "";
  private password: string = "";
  private username: string = ""; 

  constructor(private userService: UserService) {
    super("");
  }
}
