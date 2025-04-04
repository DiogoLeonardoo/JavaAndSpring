package br.com.study.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {
}
