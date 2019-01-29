import { ValidationErrors, AbstractControl, ValidatorFn, FormGroup } from "@angular/forms";

export class PasswordValidators{
  static patternValidator(regex: RegExp, error: ValidationErrors): ValidatorFn | null {
    return (control: AbstractControl): { [key: string]: any } => {
      if (!control.value) {
        return null;
      }
  
      const valid = regex.test(control.value);
  
      return valid ? null : error;
    };
  }

  static validate(form: FormGroup) {
    let password = form.controls.password.value;
    let repeatPassword = form.controls.confirmPassword.value;

    if (repeatPassword.length == 0) {
        return null;
    }

    if (repeatPassword !== password) {
        return {
            doesNotMatchPassword: true
        };
    }

    return null;
  }
}