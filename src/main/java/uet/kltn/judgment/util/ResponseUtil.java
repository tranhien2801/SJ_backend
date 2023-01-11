package uet.kltn.judgment.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uet.kltn.judgment.constant.Constant;
import uet.kltn.judgment.dto.PageDto;
import uet.kltn.judgment.dto.ResponseDto;

import java.util.List;
import java.util.Map;

@Component
public class ResponseUtil {
    public ResponseEntity<ResponseDto> getNotFoundResponse(String object) {
        return new ResponseEntity<>(new ResponseDto(String.format(Constant.FORMAT_NOT_FOUND, object),
                HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ResponseDto> getBadRequestResponse() {
        return new ResponseEntity<>(new ResponseDto(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseDto> getBadRequestResponse(Object message) {
        return new ResponseEntity<>(new ResponseDto(message,
                HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseDto> getBadRequestResponse(Map<String, List<String>> error) {
        return new ResponseEntity<>(new ResponseDto(error, HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseDto> getBadRequestResponse(Object err, String message) {
        return new ResponseEntity<>(new ResponseDto((Map<String, List<String>>) err, message,
                HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseDto> getInternalServerErrorResponse() {
        return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ResponseDto> getSuccessResponse(PageDto responseData) {
        responseData.setMessage(HttpStatus.OK.getReasonPhrase());
        responseData.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto> getSuccessResponse(Object responseData) {
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.getReasonPhrase(),
                HttpStatus.OK.value(), responseData), HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto> getSuccessResponse() {
        return new ResponseEntity<>(new ResponseDto(HttpStatus.OK.getReasonPhrase(),
                HttpStatus.OK.value()), HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto> getConflictResponse() {
        return new ResponseEntity<>(new ResponseDto(HttpStatus.CONFLICT.getReasonPhrase(), HttpStatus.CONFLICT.value()),
                HttpStatus.CONFLICT);
    }

    public ResponseEntity<ResponseDto> getConflictResponse(Object message) {
        return new ResponseEntity<>(new ResponseDto(message, HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
    }

    public ResponseEntity<ResponseDto> getUnauthorizedResponse() {
        return new ResponseEntity<>(new ResponseDto(HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseDto> getUnauthorizedResponse(String message) {
        return new ResponseEntity<>(new ResponseDto(message, HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<ResponseDto> getForbiddenResponse() {
        return new ResponseEntity<>(new ResponseDto(HttpStatus.FORBIDDEN.getReasonPhrase(),
                HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<ResponseDto> getForbiddenResponse(String message) {
        return new ResponseEntity<>(new ResponseDto(message, HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
    }

    public ResponseEntity<ResponseDto> getUnprocessableEntityResponse(Object message) {
        return new ResponseEntity<>(new ResponseDto(message,
                HttpStatus.UNPROCESSABLE_ENTITY.value()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
