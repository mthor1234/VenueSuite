package async.crash.com.venuesuite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by mitchthornton on 6/29/18.
 */

public class RegisterActivity extends AppCompatActivity {
    EditText et_email, et_firstName, et_lastName, et_phoneNumber;
    TextInputEditText et_password;
    Button btn_registerButton, btn_loginButton;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        et_email = (EditText) findViewById(R.id.register_activity_et_email);
        et_password = (TextInputEditText) findViewById(R.id.register_activity_et_password);
//        et_password = (EditText) findViewById(R.id.register_activity_et_password);

        et_firstName = (EditText) findViewById(R.id.register_activity_et_firstname);


        et_lastName = (EditText) findViewById(R.id.register_activity_et_lastname);
        et_phoneNumber = (EditText) findViewById(R.id.register_activity_et_phone);
        btn_registerButton = (Button) findViewById(R.id.register_activity_btn_register);
        btn_loginButton = (Button) findViewById(R.id.register_activity_btn_login);

        progressBar = (ProgressBar) findViewById(R.id.indeterminateBar);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();
                String firstName = et_firstName.getText().toString();
                String lastName = et_lastName.getText().toString();
                String phone = et_phoneNumber.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please fill in the required fields",Toast.LENGTH_SHORT).show();
                }

                if(password.length()<6){
                    Toast.makeText(getApplicationContext(),"Password must be at least 6 characters",Toast.LENGTH_SHORT).show();
                }

//                CreateRequest request = new CreateRequest()
//                        .setEmail(email)
//                        .setEmailVerified(false)
//                        .setPassword(password)
//                        .setPhoneNumber(phone)
//                        .setDisplayName(firstName + " " + lastName)
//                        .setDisabled(false);
//
//                UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
//                System.out.println("Successfully created new user: " + userRecord.getUid());


                firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {



                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //TODO: May need to handle this using the fragment manager
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Error Registering! Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        btn_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
}
