package cs309.backend.models.Response;

import lombok.Data;

@Data
public class BaseResponse<T> {
    private boolean success;

    private String errorCode;

    private String exceptionCode;

    private T output;

    public BaseResponse(boolean success) {
        this.success = success;
    }
}
