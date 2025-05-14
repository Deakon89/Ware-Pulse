import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormGroup } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
     localStorage.removeItem('jwt'); // solo per debug!
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  onSubmit() {
  //   if (this.loginForm.valid) {
  //     console.log('Form Value', this.loginForm.value);
  //     // TODO: integrate with authentication service
  //   }
  // }
    if (this.loginForm.invalid) return;
      this.authService.login(this.loginForm.value).subscribe({
      next: ({token}) => {
      localStorage.setItem('jwt', token);
      this.router.navigate(['/profile']);
    },
    error: err => console.error('Login fallito', err)
  });
}

}
