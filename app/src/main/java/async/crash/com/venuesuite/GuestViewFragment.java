package async.crash.com.venuesuite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mitchthornton on 7/9/18.
 */

public class GuestViewFragment extends Fragment {

    private TextView tv_name, tv_addedBy, tv_phone, tv_email, tv_partySize;
    private ImageView img_profilePic;

    public static Fragment newInstance(){
        return new GuestViewFragment();
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_guest_view, container, true);

        tv_name = (TextView) v.findViewById(R.id.fragment_guest_view_tv_guestname);
        tv_addedBy = (TextView) v.findViewById(R.id.fragment_guest_view_tv_addedby);
        tv_phone = (TextView) v.findViewById(R.id.fragment_guest_view_tv_phone);
        tv_email = (TextView) v.findViewById(R.id.fragment_guest_view_tv_email);
        tv_partySize = (TextView) v.findViewById(R.id.fragment_guest_view_tv_partysize);



            System.out.println("Name: " + getArguments().get("name").toString() );
//            System.out.println("Added By: " + getArguments().get("added").toString() );
//            System.out.println("phone: " + getArguments().get("phone").toString() );

            tv_name.setText(getArguments().get("name").toString());
            tv_partySize.setText(getArguments().get("party_size").toString());
//            tv_addedBy.setText(getArguments().get("added").toString());
//            tv_phone.setText(getArguments().get("phone").toString());
//            tv_email.setText(getArguments().get("email").toString());

        return super.onCreateView(inflater, container, savedInstanceState);


    }
}
