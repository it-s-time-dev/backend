package Itstime.planear.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final ResultType resultType;
    private final T data;
    @JsonProperty("message")
    private final String errorMessage;

    private ApiResponse(ResultType resultType, T data, String errorMessage) {
        this.resultType = resultType;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResultType.SUCCESS, data, null);
    }

    public static ApiResponse<?> fail(String errorMessage) {
        return new ApiResponse<>(ResultType.FAIL, null, errorMessage);
    }
}
