package uet.kltn.judgment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<D> {
    private Object message;
    private Map<String, List<String>> error;
    @JsonProperty("status")
    private int status;
    private D data;

    public ResponseDto(D data) {
        this.data = data;
    }

    public ResponseDto(Object message, int status) {
        this.message = message;
        this.status = status;
    }
    public ResponseDto(Map<String, List<String>> error, String message, int status) {
        this.message = message;
        this.error = error;
        this.status = status;
    }

    public ResponseDto(Object message, int status, D data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }


}
