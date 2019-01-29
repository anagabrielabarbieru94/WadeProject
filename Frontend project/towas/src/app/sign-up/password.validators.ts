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
    let confirmPassword = form.controls.confirmPassword.value;

    if (confirmPassword.length <= 0) {
        return null;
    }

    if (confirmPassword != password) {
      // console.log('Nu se potrivesc');
        return {
            doesNotMatchPassword: true
        };
    }

    return null;
  }

  static checkLength(form: FormGroup) {
    let password = form.controls.password.value;
    let confirmPassword = form.controls.confirmPassword.value;

    if (confirmPassword.length <= 0) {
        return null;
    }

    if ((confirmPassword.length > password.length) || (confirmPassword.length < password.length)){
      // console.log('Nu au aceeasi dimensiune');
      return {
          doesNotHaveSameLength: true
      };
  }

    return null;
  }
}