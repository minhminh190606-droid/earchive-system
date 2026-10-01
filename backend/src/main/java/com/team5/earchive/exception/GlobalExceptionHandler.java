package com.team5.earchive.exception;

import com.team5.earchive.dto.ApiErrorResponse;
import com.team5.earchive.dto.FieldErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        List<FieldErrorResponse> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorResponse(
                        error.getField(),
                        error.getDefaultMessage() == null
                                ? "Giá trị không hợp lệ."
                                : error.getDefaultMessage()
                ))
                .toList();

        ApiErrorResponse body = createError(
                status.value(),
                "VALIDATION_ERROR",
                "Dữ liệu đầu vào không hợp lệ.",
                request,
                fieldErrors
        );

        return new ResponseEntity<>(body, headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object originalBody,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        int httpStatus = status.value();

        String code = switch (httpStatus) {
            case 400 -> "INVALID_REQUEST";
            case 404 -> "ENDPOINT_NOT_FOUND";
            case 405 -> "METHOD_NOT_ALLOWED";
            case 413 -> "PAYLOAD_TOO_LARGE";
            case 415 -> "UNSUPPORTED_MEDIA_TYPE";
            default -> httpStatus >= 500
                    ? "INTERNAL_ERROR"
                    : "HTTP_ERROR";
        };

        String message = switch (httpStatus) {
            case 400 -> "Yêu cầu không hợp lệ. Vui lòng kiểm tra dữ liệu.";
            case 404 -> "Đường dẫn API không tồn tại.";
            case 405 -> "Phương thức HTTP không được hỗ trợ.";
            case 413 -> "Nội dung gửi lên vượt giới hạn cho phép.";
            case 415 -> "Định dạng nội dung gửi lên không được hỗ trợ.";
            default -> httpStatus >= 500
                    ? "Hệ thống gặp lỗi. Vui lòng thử lại sau."
                    : "Không thể thực hiện yêu cầu.";
        };

        if (httpStatus >= 500) {
            log.error("Lỗi xử lý yêu cầu tại {}", getPath(request), ex);
        }

        ApiErrorResponse body = createError(
                httpStatus, code, message, request, List.of()
        );

        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(
            Exception ex,
            WebRequest request
    ) {
        log.error("Lỗi không dự kiến tại {}", getPath(request), ex);

        ApiErrorResponse body = createError(
                500,
                "INTERNAL_ERROR",
                "Hệ thống gặp lỗi. Vui lòng thử lại sau.",
                request,
                List.of()
        );

        return ResponseEntity.status(500).body(body);
    }

    private ApiErrorResponse createError(
            int status,
            String code,
            String message,
            WebRequest request,
            List<FieldErrorResponse> fieldErrors
    ) {
        return new ApiErrorResponse(
                Instant.now(),
                status,
                code,
                message,
                getPath(request),
                fieldErrors
        );
    }

    private String getPath(WebRequest request) {
        if (request instanceof ServletWebRequest servletRequest) {
            return servletRequest.getRequest().getRequestURI();
        }
        return "";
    }
}