package async.crash.com.venuesuite.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import async.crash.com.venuesuite.Fragments.MainFragment;
import async.crash.com.venuesuite.R;

/**
 * Created by mitchthornton on 6/27/18.
 */

public class MainActivity extends SingleFragmentActivity {

//    FirebaseAuth firebaseAuth;
//    private FirebaseAuth.AuthStateListener  authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createFragment();

//        firebaseAuth = FirebaseAuth.getInstance();
//
//        authStateListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if(user == null){
//                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
////                    getActivity().finish();
//                }
//                else{
////                    textView.setText("Hi " + user.getEmail());
//                    //tv_Header.setText(user.getEmail());
//
//
//
////                    String users_name = getResources().getString(R.string.users_name, user.getDisplayName());
//
//                }
//            }
//        };


    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        firebaseAuth.addAuthStateListener(authStateListener);
//
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
////        if(authStateListener != null){
////            firebaseAuth.removeAuthStateListener(authStateListener);
////        }
//    }

    @Override
    protected Fragment createFragment() {
        System.out.println("Creating MainActivity Fragment");
        Fragment fragment = MainFragment.newInstance();
        if(fragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment).commit();
        }
        return fragment;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
