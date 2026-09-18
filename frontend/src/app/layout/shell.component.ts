import { Component } from '@angular/core'; import { Router,RouterLink,RouterLinkActive,RouterOutlet } from '@angular/router'; import { AuthService } from '../core/auth.service';
@Component({selector:'app-shell',standalone:true,imports:[RouterOutlet,RouterLink,RouterLinkActive],templateUrl:'./shell.component.html',styleUrl:'./shell.component.scss'})
export class ShellComponent {constructor(public auth:AuthService,private router:Router){} logout(){this.auth.logout();this.router.navigate(['/login']);}}
