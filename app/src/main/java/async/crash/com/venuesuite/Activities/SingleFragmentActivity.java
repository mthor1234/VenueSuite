package async.crash.com.venuesuite.Activities;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import async.crash.com.venuesuite.Fragments.CalendarFragment;
import async.crash.com.venuesuite.Fragments.ChatFragment;
import async.crash.com.venuesuite.Fragments.ContactsFragment;
import async.crash.com.venuesuite.Fragments.MainFragment;
import async.crash.com.venuesuite.Fragments.ScheduleFragment;
import async.crash.com.venuesuite.Fragments.SecurityFragment;
import async.crash.com.venuesuite.R;

/**
 * Created by mitchthornton on 6/26/18.
 */

//TODO: Fragments are overlaying the activity but the user can still select buttons from the SingleFragmentActivity
    // Probably has something to do with setting attachtoroot=true in the FM

public abstract class SingleFragmentActivity extends AppCompatActivity
                    implements ContactsFragment.ChatInformation{

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private String mCurrentFragment = "Home";

    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle drawerToggle;

    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener  authStateListener;

    protected abstract Fragment createFragment();


    protected int getLayoutResId() {
        return R.layout.fragment_activity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nView);
        View headerView = navigationView.getHeaderView(0);
        final TextView navUsername = (TextView) headerView.findViewById(R.id.nav_header_tv);


        drawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close) {


            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mCurrentFragment);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
//        session = new SessionManager(getApplicationContext());
//        user = session.getUserDetails();
//        profilepic.setImageBitmap(StringToBitMap(user.get(SessionManager.KEY_PROFILEPIC)));
//        name.setText(user.get(SessionManager.KEY_NAME));
//        lastsynced.setText(lastsynced());
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }


        };


        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nView);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.flContent);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.flContent, fragment)
                    .addToBackStack(null)
                    .commit();
        }

        // Setup drawer view
        setupDrawerContent(nvDrawer);

        TextView tv_Header = (TextView) nvDrawer.findViewById(R.id.nav_header_tv);

        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                else{
                    // Set navHeaderUsername as the user's display name saved in the firebase auth database
                    navUsername.setText(user.getDisplayName());

                }
            }
        };


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return false;
                    }
                }
        );
    }


    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on the nav item clicked
        Fragment fragment = null;
        Class fragmentClass;

        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = MainFragment.class;
                mCurrentFragment = "Home";
                break;
            case R.id.nav_second_fragment:
                fragmentClass = SecurityFragment.class;
                mCurrentFragment = "Security";
                break;
            case R.id.nav_third_fragment:
                fragmentClass = ScheduleFragment.class;
                mCurrentFragment = "Schedule";
                break;
            case R.id.nav_fourth_fragment:
                fragmentClass = CalendarFragment.class;
                mCurrentFragment = "Calendar";
                break;
                case R.id.nav_fifth_fragment:
                fragmentClass = GuestListFragmentView.class;
                    mCurrentFragment = "Guestlist";
                    break;
                case R.id.nav_sixth_fragment:
                fragmentClass = ContactsFragment.class;
                    mCurrentFragment = "Contacts";
                    break;
            default:
                fragmentClass = MainFragment.class;
                mCurrentFragment = "Home";
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    // 'onPostCreate' called when activity start-up is complete after onStart();
    // NOTE 1: Make sure to override the method with only a single 'Bundle' argument
    // NOTE 2: Make sure you impletement the correct onPostCreate() method
    // There are 2 signatures and only 'onPostCreate(Bundle savedState) ' shows the hamburger icon

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);

    }


    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState is called
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }


    // Interface
    // Allows clicking on a "contact" from the "ContactsFragment" to start a chat and launch the "ChatFragment"
    @Override
    public void startChat(String db_id, String receivingUser) {
        Fragment fragment = ChatFragment.newInstance(db_id, receivingUser);
        getSupportFragmentManager().beginTransaction().replace(R.id.flContent,fragment).addToBackStack(null).commit();
    }
}