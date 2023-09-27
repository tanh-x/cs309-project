package cs309.backend.models.Response;

import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;

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
