package async.crash.com.venuesuite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by mitchthornton on 6/29/18.
 *
 * Used as a screen when a user 'forgets their password'
 */

public class NewPassActivity extends AppCompatActivity{

    EditText email;
    Button btnNewPass;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_activity);
        email = (EditText) findViewById(R.id.newpass_et_email);
        btnNewPass = (Button) findViewById(R.id.newpass_btn_newpass);

        // Getting the instance of Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        btnNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = email.getText().toString();

                if(username.length() == 0){
                    Toast.makeText(getApplicationContext(), "E-mail field must not be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.sendPasswordResetEmail(username)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Password reset link was sent your email address",Toast.LENGTH_SHORT).show();
                            }else{
                                    Toast.makeText(getApplicationContext(), "Mail sending error, please try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
}