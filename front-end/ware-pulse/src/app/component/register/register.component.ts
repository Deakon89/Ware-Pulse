import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { AuthService, RegisterPayload } from '../../service/auth.service';
import { Router } from '@angular/router';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
// Validator to check that two controls match
export const confirmPasswordValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const password = control.get('password');
  const confirm = control.get('confirmPassword');
  return password && confirm && password.value !== confirm.value
    ? { passwordMismatch: true }
    : null;
};

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})

export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  isLoading = false;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    // Initialize form in constructor to avoid using fb before init
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  ngOnInit(): void {
    // No form initialization here; already done in constructor
  }

  onSubmit(): void {
    if (this.registerForm.invalid) {
      return;
    }
    this.isLoading = true;
    this.errorMessage = null;

    const payload: RegisterPayload = {
      username: this.registerForm.value.username as string,
      email: this.registerForm.value.email as string,
      password: this.registerForm.value.password as string
    };

    this.authService.register(payload).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/login']);
        // handle success (e.g., navigate to login or show message)
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = err.error?.message || 'Registration failed';
      }
    });
  }
}



// export class RegisterComponent {
//   registerForm!: FormGroup;
//   form = this.fb.group({
//     username: ['', Validators.required],
//     email: ['', [Validators.required, Validators.email]],
//     password: ['', [Validators.required, Validators.minLength(6)]]
//   });
  // photoPreview: string | ArrayBuffer | null = null;

  // constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {}

  // ngOnInit(): void {
  //   this.registerForm = this.fb.group({
  //     username: ['', Validators.required],
  //     // lastName: ['', Validators.required],
  //     email: ['', [Validators.required, Validators.email]],
  //     // company: [''],
  //     passwords: this.fb.group({
  //       password: ['', [Validators.required, Validators.minLength(6)]],
  //       confirmPassword: ['', Validators.required]
  //     }, { validators: confirmPasswordValidator })
  //   });
  // }

  // get firstName() { return this.registerForm.get('firstName')!; }
  // get lastName() { return this.registerForm.get('lastName')!; }
  // get email() { return this.registerForm.get('email')!; }
  // get password() { return this.registerForm.get(['passwords', 'password'])!; }

  // onFileSelected(event: Event): void {
  //   const file = (event.target as HTMLInputElement).files?.[0];
  //   if (file) {
  //     this.registerForm.patchValue({ photo: file });
  //     const reader = new FileReader();
  //     reader.onload = () => this.photoPreview = reader.result;
  //     reader.readAsDataURL(file);
  //   }
  // }

  // onSubmit(): void {
  //   if (this.registerForm.valid) {
  //     const formValue = { ...this.registerForm.value };
  //     // formValue.passwords contiene password e confirmPassword
  //     // Inserisci qui la chiamata al servizio di registrazione
  //     console.log('Dati registrazione:', formValue, 'Foto:', this.photoPreview);
  //   }
  // }

  // onSubmit() {
  //   if (this.form.invalid) return;
  //   this.authService.register(this.form.value).subscribe({
  //     next: () => this.router.navigate(['/login']),
  //     error: err => console.error('Register failed', err)
  //   });
  // }
