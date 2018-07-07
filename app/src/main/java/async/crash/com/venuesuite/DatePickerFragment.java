package async.crash.com.venuesuite;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by mitchthornton on 7/5/18.
 */


public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener{

    private int year, month, day;

    static int REQUEST_CODE = 0;
    static String EDIT_TEXT_BUNDLE_KEY = "et_bundle";


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something
        this.year = year;
        this.month = month;
        this.day = day;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        sendResult(cal, 1);
    }


    private void sendResult(Calendar calendar, int REQUEST_CODE) {
        String EDIT_TEXT_BUNDLE_KEY = "test";
        String editTextString = "testing123";

//        Intent intent = new Intent();
//        intent.putExtra(EDIT_TEXT_BUNDLE_KEY, editTextString);
//        getTargetFragment().onActivityResult(
//                getTargetRequestCode(), REQUEST_CODE, intent);


        MyContract mHost = (MyContract) getTargetFragment();
        mHost.passDate(calendar, year, month, day);
    }
//
//    @Override
//    public void passData(String data) {
//
//    }

    // Getters and setters
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
