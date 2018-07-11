package async.crash.com.venuesuite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by mitchthornton on 7/2/18.
 */

//TODO: Ability to text out guestlist form and users that sign up are credited to the employee
    // Who sent out the text
    //TODO: Create a database of saved users so they popup as suggested text as you type out the names
    //TODO: Button that calls a DialogFragment that holds a Datepicker
    //TODO: Allow for a range of dates to be selected datepicker
    //TODO: Make required fields
    //TODO: Limit the number of persons fields, prevent text, and negatives
    //TODO: Date Textview  with button next or clickable textview that launches the set date dialog and then sets the
    // Textview to the selected date
    // TODO: Should make the main screen for guestlist to see all of the added guests AKA the actual guestlist,
    // Then add a Floating Action Bar to add people to the guestlist
    //TODO: Have an added by field, so it can be tracked by which employee added

public class GuestListFragment extends Fragment implements MyContract {

    private EditText et_firstName, et_lastName, et_email, et_phone;
    private Button btn_datePicker, btn_submit;
    private String passed_in_date;
    private Spinner spinner;
    private static int REQUEST_CODE = 0;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    private String addedBy;                 // Keeps track of which employee has added the guest



    public static Fragment newInstance(){
        return new GuestListFragment();
    }

    private String[] arraySpinner = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10+"
    };


    DialogFragment dialogFragment = new DatePickerFragment();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_guestlist, container, true);

        et_firstName = (EditText) v.findViewById(R.id.fragment_guestlist_et_firstname);
        et_lastName = (EditText) v.findViewById(R.id.fragment_guestlist_et_lastname);
        et_phone = (EditText) v.findViewById(R.id.fragment_guestlist_et_phone);
        et_email = (EditText) v.findViewById(R.id.fragment_guestlist_et_email);

        btn_datePicker = (Button) v.findViewById(R.id.fragment_guestlist_btn_datepicker);
        btn_submit = (Button) v.findViewById(R.id.fragment_guestlist_btn_submit);

        // Spinner Code
        spinner = (Spinner) v.findViewById(R.id.fragment_guestlist_spinner_guests);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, arraySpinner);
        spinner.setAdapter(adapter);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");

        btn_datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.setValue(et_firstName.getText().toString());
            }
        });


        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void showDatePickerDialog(View view) {
//        Intent intent = new Intent();
//        intent.putExtra("STRING_RESULT", str);

        FragmentManager fm = getActivity().getSupportFragmentManager();

        DialogFragment dialogFragment = new DatePickerFragment();

        dialogFragment.setTargetFragment(this, REQUEST_CODE);
        dialogFragment.show(fm, "DateFragment");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Make sure fragment code matches up
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == DatePickerFragment.REQUEST_CODE){
            String editTextString = data.getStringExtra(
                    DatePickerFragment.EDIT_TEXT_BUNDLE_KEY);
        }
    }

    @Override
    public void passDate(Calendar calendar, int year, int month, int day) {
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        String now = df.format(new Date());

        // Formatting the date so it outputs as MM-dd-YYYY
        SimpleDateFormat format1 = new SimpleDateFormat("MM-dd-yyyy");
        String formatted = format1.format(calendar.getTime());

        btn_datePicker.setText(formatted);
    }
}

