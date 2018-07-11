package async.crash.com.venuesuite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by mitchthornton on 7/2/18.
 */

public class ContactsFragment extends Fragment {

    ArrayList<User> mUsers;
    ListView listView;
    private static ContactsCustomAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

                User user = mUsers.get(position);

                if (user.getPhoneNumber() != null && user.getPhoneNumber().length() > 6) {
                    final Snackbar snackbar = Snackbar.make(view, user.getName(), Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Text or call", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();

                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
                            startActivity(intent);
                        }
                    });
                    snackbar.show();
                } else {
                    final Snackbar snackbar = Snackbar.make(view, "No phone number registered", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();
                }

            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}