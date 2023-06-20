export default abstract class ErrorMessage {
  constructor(private errorMessage: string) {}

  getErrorMessage(): string {
    return this.errorMessage;
  }

  setErrorMessage(message = ""): void {
    this.errorMessage = message;
  }
}