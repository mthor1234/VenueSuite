package async.crash.com.venuesuite;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

/**
 * Created by mitchthornton on 6/27/18.
 */

public class MainActivity extends SingleFragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createFragment();
    }

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
