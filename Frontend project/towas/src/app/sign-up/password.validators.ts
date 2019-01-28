import { ValidationErrors, AbstractControl, ValidatorFn } from "@angular/forms";

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
}