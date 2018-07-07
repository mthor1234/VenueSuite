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

    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataModels = new ArrayList<DataModel>();

        dataModels.add(new DataModel("MJ Thornton", "860 878 5445", "Busser"));
        dataModels.add(new DataModel("MJ Test", "860 878 5445", "Busser"));
        dataModels.add(new DataModel("MJ TESTING", "860 878 5445", "Busser"));
        dataModels.add(new DataModel("MJ YEAH", "860 878 5445", "Busser"));
        dataModels.add(new DataModel("MJ RUSS", "860 878 5445", "Busser"));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts, container, true);

        listView = (ListView) v.findViewById(R.id.fragment_contacts_lv);

        adapter= new CustomAdapter(dataModels, getActivity().getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);

                final Snackbar snackbar = Snackbar.make(view, dataModel.getName(), Snackbar.LENGTH_LONG);
                snackbar.setAction("Text or call",  new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
                        startActivity(intent);
                    }
                });
                snackbar.show();
            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}


