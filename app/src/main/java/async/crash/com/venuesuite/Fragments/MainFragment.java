package async.crash.com.venuesuite.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import async.crash.com.venuesuite.Activities.LoginActivity;
import async.crash.com.venuesuite.R;

/**
 * Created by mitchthornton on 6/27/18.
 */

public class MainFragment extends Fragment {

    TextView textView, tv_Header;
    Button btnLogout;
    FirebaseAuth firebaseAuth;

    private FirebaseAuth.AuthStateListener  authStateListener;

    public static Fragment newInstance(){
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        final LayoutInflater tempInflater =  getLayoutInflater();

        View temp = inflater.inflate(R.layout.fragment_activity, null);


        textView = (TextView) v.findViewById(R.id.fragment_main_tv_start);
        btnLogout = (Button) v.findViewById(R.id.fragment_main_btn_logout);


        //TODO: Use this bit of code for an option to delete a user on an admin page

//        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(user != null){
//                    user.delete()
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if(task.isSuccessful()){
//                                        Toast.makeText(getActivity().getApplicationContext(), "User Deleted!", Toast.LENGTH_SHORT).show();
//                                        //TODO: Make RegisterActivity class, layout, and add to Manifest
//                                        //TODO: Add NewPassActivity class to the manifest
//                                        startActivity(new Intent(getActivity().getApplicationContext(), RegisterActivity.class));
//                                        getActivity().finish();
//                                    }else {
//                                        Toast.makeText(getActivity().getApplicationContext(), "Error deleting user!", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
//            }
//        });

        btnLogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                // TODO: Error when clicking around on the 'Security' fragment. It prompted pressing the 'logout' button which appears on the main fragment
                // This means that Mainfragment is still accessible even though 'Security' fragment overlays it
                //java.lang.NullPointerException: Attempt to invoke virtual method 'android.content.Context android.support.v4.app.FragmentActivity.getApplicationContext()' on a null object reference

                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                getActivity().finish();
            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
