package async.crash.com.venuesuite;

import android.content.Context;
import android.support.annotation.AnimatorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mitchthornton on 7/7/18.
 */

public class GuestListCustomAdapter extends ArrayAdapter<Guest> implements
        View.OnClickListener {

    private ArrayList<Guest> dataSet;
    Context mContext;
    private int lastPosition = -1;

    private static class ViewHolder{
        TextView tv_Name, tv_addedBy, tv_guests;
        CheckBox cb_added;
    }

    public GuestListCustomAdapter(ArrayList<Guest> data, Context context){
        super(context, R.layout.guestlist_row_item, data);
        this.dataSet = data;
        this.mContext = context;
    }


    @Override
    public void onClick(View v) {

        int position = (Integer)v.getTag();
        Object object = getItem(position);

        Guest guest = (Guest) object;

        final Snackbar snack = Snackbar.make(v, guest.getFirstName(), Snackbar.LENGTH_SHORT);
        snack.show();

        System.out.println("Clicking inside of the custom adapter");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Getting guest data
        Guest guest = (Guest) getItem(position);

        // Check if an existing view is being reused, otherwise recycle it

        ViewHolder viewHolder;  // View lookup for cache stored in tag

        final View result;

        if(convertView == null){
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.guestlist_row_item, parent, false);

            viewHolder.tv_Name = (TextView) convertView.findViewById(R.id.guestlist_row_item_tv_name);
            viewHolder.tv_addedBy = (TextView) convertView.findViewById(R.id.guestlist_row_item_tv_addedby);
            viewHolder.tv_guests = (TextView) convertView.findViewById(R.id.guestlist_row_item_tv_number);
            viewHolder.cb_added = (CheckBox) convertView.findViewById(R.id.guestlist_row_item_cb);

            convertView.setTag(viewHolder);
            result = convertView;

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ?
                R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.tv_Name.setText(guest.getName());
        viewHolder.tv_addedBy.setText(guest.getAddedBy().getName());
        viewHolder.tv_guests.setText(Integer.toString(guest.getPartySize()));
        viewHolder.cb_added.setChecked(guest.hasEntered());

        return convertView;
    }
}
