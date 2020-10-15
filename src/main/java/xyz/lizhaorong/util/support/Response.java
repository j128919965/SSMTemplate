package xyz.lizhaorong.util.support;

public class Response {

    private static final String OK = "ok";
    private static final String ERROR = "error";

    private boolean success;
    private String message;
    private Object data;

    public static Response success(){
        Response response = new Response();
        response.message=OK;
        response.success=true;
        return response;
    }

    public static Response success(Object data){
        Response response = new Response();
        response.message=OK;
        response.success=true;
        response.data = data;
        return response;
    }

    public static Response failure(){
        Response response = new Response();
        response.message=ERROR;
        response.success=false;
        return response;
    }

    public static Response failure(String message,Object data){
        Response response = new Response();
        response.message=message;
        response.success=false;
        response.data = data;
        return response;
    }

    public static Response failure(String message){
        Response response = new Response();
        response.message=message;
        response.success=false;
        return response;
    }

    public static Response failure(ErrorCode errorCode){
        Response response = new Response();
        response.message=errorCode.message();
        response.data=errorCode.code();
        response.success=false;
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
