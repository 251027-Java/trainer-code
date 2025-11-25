import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  //Depedency Inject Router to switch URLS
  constructor(private router:Router){}

  //Variables that will hold the username/password
  username:string = ""
  password:string = ""

  //Hardcoded login for now
  login(){
    if(this.username === "user" && this.password === "password"){
      //Switch URLs within Typescript using Router
      this.router.navigateByUrl("/dashboard")
    } else {
      alert("Invalid Username or Password")
    }
  }

}
