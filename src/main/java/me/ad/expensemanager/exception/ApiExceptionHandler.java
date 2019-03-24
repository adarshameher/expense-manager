package me.ad.expensemanager.exception;

import java.sql.SQLException;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = "Method " + ex.getMethod().toUpperCase() + " is not supported";
		message += "\n Supported methods are ";
		message += ex.getSupportedHttpMethods().stream().map(HttpMethod::name).collect(Collectors.joining(", "));
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, message, ex));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = "MediaType " + ex.getContentType() + " is not supported";
		message += "\n Supported MediaTypes are ";
		message += ex.getSupportedMediaTypes().stream().map(MediaType::getType).collect(Collectors.joining(", "));
		return buildResponseEntity(new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, message, ex));
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String message = "PathVariable " + ex.getVariableName() + " is missing";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, message, ex));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = "Malformed request";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, message, ex));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = "Error writing response";
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, message, ex));
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = "Validation Error";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, message, ex));
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintsViolation(ConstraintViolationException cve) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Validation Error");
		apiError.addValidationErrors(cve.getConstraintViolations());
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		apiError.setMessage(ex.getMessage());
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(SQLException.class)
	protected ResponseEntity<Object> handleSQLException(SQLException ex) {
		String message = "SQLState: " + ex.getSQLState()
			+ ", SQLErrorCode: " + ex.getErrorCode()
			+ ", SQLErrorMessage: " + ex.getMessage();
		return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, message, ex));
	}
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
