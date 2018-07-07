package async.crash.com.venuesuite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by mitchthornton on 7/1/18.
 */

    //TODO: Add the ability to add a total count via keyboard entry... It's a pain to press the up button to get to 100 total count
    //TODO: Set max capacity and warnings for it
    //TODO: Add multiple door counts
    //TODO: Need to make the door counts / door count text views dynamic so the management can add the correct amount of doors based on the venue

public class SecurityFragment extends Fragment {

    private TextView tv_myCount, tv_totalCount;
    private ImageButton imgbtn_up, imgbtn_down;
    private ConstraintLayout mRoot;

    private int currentCount = 0;
    // Adding + 200 as a dummy value to reflect a simulated total count
    private int totalCount = currentCount + 200;

    // Using a dummy value for the max capacity. Management should be able to set this value
    private int maxCapacity = 300;
    private Snackbar snack;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_security, container, true);

        tv_myCount = (TextView) v.findViewById(R.id.fragment_security_tv_doorcount);
        tv_totalCount = (TextView) v.findViewById(R.id.fragment_security_tv_total_count);
        imgbtn_up = (ImageButton) v.findViewById(R.id.fragment_security_imgbtn_up);
        imgbtn_down = (ImageButton) v.findViewById(R.id.fragment_security_imgbtn_down);

        mRoot = v.findViewById(R.id.container_layout);

        tv_myCount.setText(String.valueOf(currentCount));

        snack = Snackbar.make(container, "", Snackbar.LENGTH_INDEFINITE);


        imgbtn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCount++;
                totalCount++;
                tv_myCount.setText(String.valueOf(currentCount));
                tv_totalCount.setText(String.valueOf(totalCount));
                maxCapacityCheck(container);
            }
        });

        imgbtn_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentCount == 0){
                    Snackbar.make(mRoot, "Current count can not be lower than 0", Snackbar.LENGTH_LONG).show();
                }else{
                    currentCount--;
                    totalCount--;

                }
                tv_myCount.setText(String.valueOf(currentCount));
                tv_totalCount.setText(String.valueOf(totalCount));
                maxCapacityCheck(container);

            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private boolean maxCapacityCheck(View view){

//        snack = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE);
        System.out.println("Percent Capacity: " + Math.round( (totalCount / maxCapacity) * 100));

        // If the total count has hit max capacity, display a popup message and
        if(totalCount == maxCapacity){
            snack.setText("At max capacity");
            snack.setDuration(Snackbar.LENGTH_INDEFINITE);
            snack.show();

//            snack.make(view, "At max capacity", Snackbar.LENGTH_INDEFINITE).show();
            return true;
        }else if( totalCount > maxCapacity){
            snack.setText("Over max capacity");
            snack.setDuration(Snackbar.LENGTH_INDEFINITE);
            snack.show();
//            snack.make(view, "Over max capacity", Snackbar.LENGTH_INDEFINITE).show();
            return true;
        }
        else if( Math.round( (totalCount / maxCapacity) * 100) <= 77 &&  Math.round( (totalCount / maxCapacity) * 100) >= 73){
            snack.setText("About 75% capacity");
            snack.setDuration(Snackbar.LENGTH_INDEFINITE);
            snack.show();
//            snack.make(view, "At 75% capacity", Snackbar.LENGTH_LONG).show();
            return false;
        }else{
            snack.dismiss();
            return false;
        }

    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }
}
