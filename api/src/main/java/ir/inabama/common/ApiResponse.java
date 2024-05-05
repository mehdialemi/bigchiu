package ir.inabama.common;

public class ApiResponse {
    private Boolean success;
    private int code;
    private String message;

    public final static ApiResponse FAILED = new ApiResponse(false);
    public final static ApiResponse SUCCESS = new ApiResponse(true);

    public ApiResponse() {

    }

    public ApiResponse(Boolean success) {
        this(success, "");
    }

    public ApiResponse(Boolean success, String message) {
        this(success, 0, message);
    }


    public ApiResponse(Boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "success: " + success + ", message: " + message;
    }


}
