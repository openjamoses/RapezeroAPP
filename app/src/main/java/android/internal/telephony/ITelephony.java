package android.internal.telephony;

/**
 * Created by john on 9/15/17.
 */

public interface ITelephony {

    boolean endCall();

    void answerRingingCall();

    void silenceRinger();
}