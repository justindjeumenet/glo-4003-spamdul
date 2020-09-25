package ca.ulaval.glo4003.domain.contact.exception;

public class ContactNotFoundException extends RuntimeException {
  private static final long serialVersionUID = -898705420292326863L;

  public ContactNotFoundException(String message) {
    super(message);
  }
}