package async.crash.com.venuesuite.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import async.crash.com.venuesuite.Models.ChatMessage;
import async.crash.com.venuesuite.R;

/**
 * Created by mitchthornton on 7/24/18.
 */

public class ChatFragment extends Fragment {
    private static int SIGN_IN_REQUEST_CODE = 1;
//    private FirebaseListAdapter<ChatMessage> adapter;
    private FirebaseListAdapter<ChatMessage> adapter;

    private String db_ID, receiving_user;
//    private User receivingUser;
    private String temp_key;
    private EditText input;

    FloatingActionButton fab;
    ListView listOfMessage;

    private static final String TAG = "Chat Fragment";

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();


    public static Fragment newInstance(String db_ID, String receivingUser){
        ChatFragment fragment = new ChatFragment();

        Bundle args = new Bundle();
        args.putString("DB_ID", db_ID);
        args.putString("receiving_user", receivingUser);
        fragment.setArguments(args);
        Log.v(TAG, "New Chat Fragment");

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the database ID for this current Chat Room
        db_ID = getArguments().getString("DB_ID");
        receiving_user = getArguments().getString("receiving_user");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        listOfMessage = (ListView) v.findViewById(R.id.list_of_messages);
        input = (EditText) v.findViewById(R.id.input);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),
//                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));

                Map<String, Object> map = new HashMap<String, Object>();
                temp_key = root.push().getKey();
                root.updateChildren(map);

                DatabaseReference message_Root = root.child(temp_key);
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("name", receiving_user);
                map2.put("message", input.getText());

                message_Root.updateChildren(map2);


                input.setText("");
            }
        });


//        displayChatMessage();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void displayChatMessage() {

//        adapter = new FirebaseListAdapter<ChatMessage>(this.getActivity(), ChatMessage.class, R.layout.list_item, FirebaseDatabase.getInstance().getReference() )
        adapter = new FirebaseListAdapter<ChatMessage>(this.getActivity(), ChatMessage.class, R.layout.list_item, FirebaseDatabase.getInstance().getReference() )
        {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                TextView messageText, messageUser, messageTime;
                messageText = (TextView)v.findViewById(R.id.message_text);
                messageUser = (TextView)v.findViewById(R.id.message_user);
                messageTime = (TextView)v.findViewById(R.id.message_time);

                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss", model.getMessageTime()));
            }
        };
        listOfMessage.setAdapter(adapter);

    }
}
