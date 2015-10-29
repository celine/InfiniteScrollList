package project.celine.infinitescroll.data;

/**
 * Created by wenchihhsieh on 15/10/29.
 */
public class ErrorEvent {
    public static final int SERVER_ERROR = 1;

    public int getError() {
        return error;
    }

    public int error;
    public ErrorEvent(int error){
        this.error = error;
    }
}
