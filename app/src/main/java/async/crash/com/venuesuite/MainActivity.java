package async.crash.com.venuesuite;

import android.os.Bundle;
import android.support.v4.app.Fragment;

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
        return MainFragment.newInstance();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
