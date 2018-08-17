package async.crash.com.venuesuite.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import async.crash.com.venuesuite.Fragments.DatePickerFragment;
import async.crash.com.venuesuite.Fragments.GuestViewFragment;
import async.crash.com.venuesuite.Interfaces.MyContract;
import async.crash.com.venuesuite.Models.Guest;
import async.crash.com.venuesuite.Models.GuestListCustomAdapter;
import async.crash.com.venuesuite.Models.User;
import async.crash.com.venuesuite.R;

/**
 * Created by mitchthornton on 7/7/18.
 */


public class GuestListFragmentView extends Fragment implements MyContract {

    ArrayList<Guest> mGuests;

    TextView tv_totalCount;
    Button btn_date;
    ListView listView;
    private SearchView mSearchView;

    private static int REQUEST_CODE = 0;

    private FragmentActivity myContext;
    private EditText theFilter;


    private static GuestListCustomAdapter adapter;
//    private static GuestListCustomAdapter adapter;

    private User user1 = new User("MJ", "860 878 5445", "Busser");
    private User user2 = new User("TESTES", "860 878 5445", "Busser");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGuests = new ArrayList<Guest>();

        mGuests.add(new Guest("John", "Smith", user1, 4));
        mGuests.add(new Guest("John2", "Smith2", user2, 6));
        mGuests.add(new Guest("Bill", "Smith", user1, 4));
        mGuests.add(new Guest("Frank", "Smith", user1, 4));
        mGuests.add(new Guest("Johnny", "Smith", user1, 4));
        mGuests.add(new Guest("Nicki", "Smith", user1, 4));
        mGuests.add(new Guest("Test", "Smith", user1, 4));
        mGuests.add(new Guest("MJ", "Smith", user1, 4));
        mGuests.add(new Guest("Rodrigo", "Smith", user1, 4));
        mGuests.add(new Guest("Shelby", "Smith", user1, 4));
        mGuests.add(new Guest("Chelsie", "Smith", user1, 4));

    }

    @Override
    public void onAttach(Context context) {
        myContext = (FragmentActivity) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_guestlist_view, container, true);

        tv_totalCount = (TextView) v.findViewById(R.id.fragment_guestlist_view_tv_totalcount);
        listView = (ListView) v.findViewById(R.id.fragment_guestlist_view_lv);
        btn_date = (Button) v.findViewById(R.id.fragment_guestlist_view_btn);

        theFilter = (EditText) v.findViewById(R.id.fragment_guestlist_view_et_filter);

//        mSearchView = (SearchView) v.findViewById(R.id.fragment_guestlist_view_searchview);


                adapter = new GuestListCustomAdapter(mGuests, getActivity().getApplicationContext());
//        adapter = new GuestListCustomAdapter(mGuests, getActivity().getApplicationContext());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Guest guest = mGuests.get(position);



                // If I want to launch another fragment. Probably use this idea for the Floating Action Bar
                Fragment fragment = (Fragment) GuestViewFragment.newInstance();
                Bundle args = new Bundle();

                args.putString("name", guest.getName());
                args.putString("added", guest.getAddedBy().getName());
                args.putString("party_size", Integer.toString(guest.getPartySize()));

                if(guest.getEmail() != null){
                    args.putString("email", guest.getEmail());
                }

                if(guest.getPhone() != null){
                    args.putString("phone", guest.getPhone());
                }


                fragment.setArguments(args);

                System.out.println("Clicking item");

              //TODO:  java.lang.NullPointerException: Attempt to invoke virtual method 'android.support.v4.app.FragmentManager android.support.v4.app.FragmentActivity.getSupportFragmentManager()' on a null object reference
//                FragmentManager fragmentManager = myContext.getSupportFragmentManager();

                FragmentManager fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                fragmentTransaction.hide(GuestListFragmentView.this);
                fragmentTransaction.add(R.id.flContent, fragment);
                fragmentTransaction.commit();
            }
        });

        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//                (GuestListFragmentView.this).adapter.getFilter().filter(charSequence);
                int textLength = charSequence.length();
                ArrayList<Guest> tempArrayList = new ArrayList<Guest>();
                for(Guest guest: mGuests){
                    if( textLength <= guest.getName().length() ){
                        if(guest.getName().toLowerCase().contains(charSequence.toString())){
                            tempArrayList.add(guest);
                        }
                    }
                }
                adapter = new GuestListCustomAdapter(tempArrayList, myContext);
                listView.setAdapter(adapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void showDatePickerDialog(View v) {
        FragmentManager fm = myContext.getSupportFragmentManager();

        DialogFragment dialogFragment = new DatePickerFragment();

        dialogFragment.setTargetFragment(this, REQUEST_CODE);
        dialogFragment.show(fm, "DateFragment");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == DatePickerFragment.getRequestCode()){
            String editTextString = data.getStringExtra(
                    DatePickerFragment.getEditTextBundleKey());
        }
    }

    @Override
    public void passDate(Calendar calendar, int year, int month, int day) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String now = df.format(new Date());

        SimpleDateFormat df1 = new SimpleDateFormat("MM-dd-yyyy");
        String formattedDate = df1.format(calendar.getTime());

        btn_date.setText(formattedDate);
    }
}