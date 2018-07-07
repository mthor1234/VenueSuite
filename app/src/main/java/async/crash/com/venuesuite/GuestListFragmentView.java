package async.crash.com.venuesuite;

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
import java.util.zip.Inflater;

/**
 * Created by mitchthornton on 7/7/18.
 */

public class GuestListFragmentView extends Fragment {

    ArrayList<Guest> mGuests;
    ListView listView;

    private static GuestListCustomAdapter adapter;

    private User user1 = new User("MJ", "860 878 5445", "Busser");
    private User user2 = new User("TESTES", "860 878 5445", "Busser");

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGuests = new ArrayList<Guest>();

        mGuests.add(new Guest("John", "Smith", user1, 4));
        mGuests.add(new Guest("John2", "Smith2", user2, 6));
        mGuests.add(new Guest("John", "Smith", user1, 4));
        mGuests.add(new Guest("John", "Smith", user1, 4));
        mGuests.add(new Guest("John", "Smith", user1, 4));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_guestlist_view, container, true);


        listView = (ListView) v.findViewById(R.id.fragment_guestlist_view_lv);

        adapter = new GuestListCustomAdapter(mGuests, getActivity().getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Guest guest = mGuests.get(position);

                Snackbar.make(container, "Clicked", Snackbar.LENGTH_SHORT).show();
            }
        });



        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
