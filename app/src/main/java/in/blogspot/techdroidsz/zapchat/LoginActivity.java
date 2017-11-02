package in.blogspot.techdroidsz.zapchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private EditText mLoginEmail;
    private EditText mLoginPassword;

    private Button mLogin_btn;

    private ProgressDialog mLogProgress;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();

        mToolbar=(Toolbar) findViewById(R.id.log_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mLogProgress=new ProgressDialog(this);


        mLoginEmail=(EditText) findViewById(R.id.log_email);
        mLogin_btn=(Button) findViewById(R.id.log_btn);
        mLoginPassword=(EditText)findViewById(R.id.log_pass);

        mLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mLoginEmail.getText().toString();
                String password=mLoginPassword.getText().toString();

                if(!TextUtils.isEmpty(email)||!TextUtils.isEmpty(password)){
                    mLogProgress.setTitle("Logging In");
                    mLogProgress.setMessage("Please wait while we check your credentials");
                    mLogProgress.setCanceledOnTouchOutside(false);
                    mLogProgress.show();

                    loginUser(email,password);
                }


            }
        });

    }


    private void loginUser(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    mLogProgress.dismiss();

                    Intent mainIntent=new Intent(LoginActivity.this,MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }else{
                    mLogProgress.hide();
                    Toast.makeText(LoginActivity.this,"Cannot Sign in.Please check the form and try again.",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
