package async.crash.com.venuesuite;

import java.util.Calendar;

/**
 * Created by mitchthornton on 7/6/18.
 * Using a method to pass data between fragments / activities so the components stay loosely coupled
 */


public interface MyContract {

    // Implement this to pass data between fragments
    void passDate(Calendar calendar, int year, int month, int day);

}
