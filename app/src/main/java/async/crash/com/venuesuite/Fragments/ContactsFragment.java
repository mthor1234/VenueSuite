package async.crash.com.venuesuite.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import async.crash.com.venuesuite.Models.ContactsCustomAdapter;
import async.crash.com.venuesuite.R;
import async.crash.com.venuesuite.Models.User;

/**
 * Created by mitchthornton on 7/2/18.
 */

public class ContactsFragment extends Fragment {

    ArrayList<User> mUsers;
    ListView listView;
    private static ContactsCustomAdapter adapter;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();

    ChatInformation mCallback;


    public interface ChatInformation{
        public void startChat(String db_id, String receivingUser);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCallback = (ChatInformation) getActivity();

        mUsers = new ArrayList<User>();

        mUsers.add(new User("MJ Thornton", "860 878 5445", "Busser"));
        mUsers.add(new User("MJ Test", "860 878 5445", "Busser"));
        mUsers.add(new User("MJ TESTING", "860 878 5445", "Busser"));
        mUsers.add(new User("MJ YEAH", "860 878 5445", "Busser"));
        mUsers.add(new User("MJ RUSS", "Busser"));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts, container, true);

        listView = (ListView) v.findViewById(R.id.fragment_contacts_lv);

        adapter = new ContactsCustomAdapter(mUsers, getActivity().getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final User user = mUsers.get(position);

//                if (user.getPhoneNumber() != null && user.getPhoneNumber().length() > 6) {
//                    final Snackbar snackbar = Snackbar.make(view, user.getName(), Snackbar.LENGTH_INDEFINITE);
//                    snackbar.setAction("Text or call", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            snackbar.dismiss();
//
//                            Intent intent = new Intent(Intent.ACTION_MAIN);
//                            intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
//                            startActivity(intent);
//                        }
//                    });
//                    snackbar.show();
//                } else {
//                    final Snackbar snackbar = Snackbar.make(view, "No phone number registered", Snackbar.LENGTH_INDEFINITE);
//                    snackbar.setAction("Dismiss", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            snackbar.dismiss();
//                        }
//                    });
//                    snackbar.show();
//                }


                // Show an alert dialog to start a chat with a user when a contact is clicked
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Start a Chat?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put(user.getName(), "");
                        root.updateChildren(map);

                        mCallback.startChat(root.getKey(), user.getName());


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


                builder.show();


            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDetach() {
        super.onDetach();

        // Gets rid of reference to activity to avoid memory leak
        mCallback = null;
    }
}