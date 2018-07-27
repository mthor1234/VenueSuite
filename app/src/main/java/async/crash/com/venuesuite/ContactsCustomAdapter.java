package async.crash.com.venuesuite;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mitchthornton on 7/3/18.
 */

/*TODO: ERROR java.lang.NullPointerException: Attempt to read from field 'android.widget.TextView async.crash.com.venuesuite.ContactsCustomAdapter$ViewHolder.tv_contactName' on a null object reference
        at async.crash.com.venuesuite.ContactsCustomAdapter.getView(ContactsCustomAdapter.java:114)
        This Occurred after attaching the contacts fragment, playing around with the fragment, then navigating away, then reattaching the fragment
        Fragment does not appear when clicked twice. Probably related to attachToRoot or navigating away*/


public class ContactsCustomAdapter extends ArrayAdapter<User> implements
        View.OnClickListener{

    private ArrayList<User> dataSet;
    private Context mContext;
    private int lastPosition = -1;

    // View lookup cache
    private static class ViewHolder{
        TextView tv_contactName, tv_phoneNumber, tv_jobRole;
        ImageView iv_profilePic;
    }

    public ContactsCustomAdapter(ArrayList<User> data, Context context){
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext = context;
    }


    @Override
    public void onClick(View v) {

        int position = (Integer)v.getTag();
        Object object = getItem(position);
        User user = (User) object;
//
//        if(user.getPhoneNumber().length() > 6) {
//
//            final Snackbar snackbar = Snackbar.make(v, user.getName(), Snackbar.LENGTH_LONG);
//                snackbar.setAction("Text or call",  new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            snackbar.dismiss();
//
//                            Intent intent = new Intent(Intent.ACTION_MAIN);
//                            intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
//                            mContext.startActivity(intent);
//                        }
//                    });
//                                snackbar.show();
//        }
//        else{
//            final Snackbar snackbar = Snackbar.make(v, "No phone number registered", Snackbar.LENGTH_LONG);
//                snackbar.setAction("Dismiss", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            snackbar.dismiss();
//                        }
//                    });
//        }

    }

    // Method to trigger the text message window to the selected contact from the contact list
    public void sendText(){

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get data item for this position
        User user = getItem(position);

        // Check if an existing view is being reuse it, otherwise inflate it

        ViewHolder viewHolder; // View lookup for cache stored in tag

        final View result;

        if(convertView == null){

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);

            viewHolder.tv_contactName = (TextView) convertView.findViewById(R.id.row_item_tv_name);
            viewHolder.tv_jobRole = (TextView) convertView.findViewById(R.id.row_item_tv_role);

            result = convertView;

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;

        }
        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition)
                ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.tv_contactName.setText(user.getName());
        viewHolder.tv_jobRole.setText(user.getJobRole());

        return convertView;

    }
}
