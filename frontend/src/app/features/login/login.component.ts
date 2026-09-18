import { Component } from '@angular/core'; import { FormBuilder,ReactiveFormsModule,Validators } from '@angular/forms'; import { Router } from '@angular/router'; import { AuthService } from '../../core/auth.service';
@Component({standalone:true,imports:[ReactiveFormsModule],templateUrl:'./login.component.html',styleUrl:'./login.component.scss'}) export class LoginComponent {
 loading=false;error='';form=this.fb.nonNullable.group({email:['demo@nexflow.dev',[Validators.required,Validators.email]],password:['Demo@123',Validators.required]});
 constructor(private fb:FormBuilder,private auth:AuthService,private router:Router){if(auth.isAuthenticated())router.navigate(['/dashboard']);}
 submit(){if(this.form.invalid)return;this.loading=true;this.error='';this.auth.login(this.form.getRawValue().email,this.form.getRawValue().password).subscribe({next:()=>this.router.navigate(['/dashboard']),error:()=>{this.error='Não foi possível entrar. Verifique as credenciais.';this.loading=false;}});}
}
