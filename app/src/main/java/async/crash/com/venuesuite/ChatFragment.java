package async.crash.com.venuesuite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mitchthornton on 7/24/18.
 */

public class ChatFragment extends Fragment {
    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<ChatMessage> adapter;
    FloatingActionButton fab;
    ListView listOfMessage;

    public static Fragment newInstance(){
        return new ChatFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, true);

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        listOfMessage = (ListView) v.findViewById(R.id.list_of_messages);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = (EditText) v.findViewById(R.id.input);
                FirebaseDatabase.getInstance().getReference().push().setValue(new ChatMessage(input.getText().toString(),
                        FirebaseAuth.getInstance().getCurrentUser().getEmail()));


                input.setText("");
            }
        });


        displayChatMessage();

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
