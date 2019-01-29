import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { UsernameValidators } from './username.validators';
import { FirstNameValidators } from './firstName.validators';
import { LastNameValidators } from './lastName.validators';
import { PasswordValidators } from './password.validators';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  src_url: String;
  passwordForm: FormGroup;
  form: FormGroup;

  // form = new FormGroup({
  //   username: new FormControl('', [
  //     Validators.required,
  //     Validators.minLength(6),
  //     UsernameValidators.cannotContainSpace
  //   ]),
  //   password: new FormControl('', [
  //     Validators.required,
  //     Validators.compose([
  //       Validators.minLength(8),
  //       PasswordValidators.patternValidator(/\d/, { hasNumber: true }),
  //       PasswordValidators.patternValidator(/[A-Z]/, { hasCapitalCase: true }),
  //       PasswordValidators.patternValidator(/[a-z]/, { hasSmallCase: true }),
  //       PasswordValidators.patternValidator(/[_+\-=,.]/, { hasSpecialCharacters: true })
  //     ])
  //   ]),
  //   confirmPassword: new FormControl('', [
  //     Validators.required
  //   ]),
  //   firstName: new FormControl('', [
  //       Validators.required,
  //       FirstNameValidators.cannotContainSpace
  //   ]),
  //   lastName: new FormControl('', [
  //       Validators.required,
  //       LastNameValidators.cannotContainSpace
  //   ]),
  //   email: new FormControl('', [
  //     Validators.required,
  //     Validators.email
  //   ])
  // })

  get username(){
    return this.form.get('username');
  }

  get password(){
    return this.form.get('password');
  }

  get confirmPassword(){
    return this.form.get('confirmPassword');
  }

  get firstName(){
    return this.form.get('firstName');
  }

  get lastName(){
    return this.form.get('lastName');
  }

  get email(){
    return this.form.get('email');
  }

  constructor(private formBuilder: FormBuilder) { 
    this.src_url = "./assets/images/tourists.png"
    this.passwordForm = this.formBuilder.group({
        password: new FormControl('', [
          Validators.required,
          Validators.compose([
            Validators.minLength(8),
            PasswordValidators.patternValidator(/\d/, { hasNumber: true }),
            PasswordValidators.patternValidator(/[A-Z]/, { hasCapitalCase: true }),
            PasswordValidators.patternValidator(/[a-z]/, { hasSmallCase: true }),
            PasswordValidators.patternValidator(/[_+\-=,.]/, { hasSpecialCharacters: true })
          ])
        ]),
        confirmPassword: new FormControl('', [
          Validators.required
        ])
    }, {
      validator: PasswordValidators.validate.bind(this)
    });

    this.form = this.formBuilder.group({
         username: new FormControl('', [
            Validators.required,
            Validators.minLength(6),
            UsernameValidators.cannotContainSpace
          ]),
          firstName: new FormControl('', [
              Validators.required,
              FirstNameValidators.cannotContainSpace
          ]),
          lastName: new FormControl('', [
              Validators.required,
              LastNameValidators.cannotContainSpace
          ]),
          email: new FormControl('', [
            Validators.required,
            Validators.email
          ]),
          passwordForm: this.passwordForm
    });
  }

  ngOnInit() {
  }

}
