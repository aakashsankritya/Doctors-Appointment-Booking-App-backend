package com.medizine.backend.exceptions;

abstract class MedizineAppException extends RuntimeException {

  MedizineAppException() {
  }

  MedizineAppException(String message) {
    super(message);
  }

  public abstract int getErrorType();
}
