package cs309.backend.models.Response;

import lombok.Data;

@Data
public class BaseRS<T> {
    private boolean success;

    private String errorCode;

    private String exceptionCode;

    private T output;

    public BaseRS(boolean success ) {
        this.success = success;
    }
}
