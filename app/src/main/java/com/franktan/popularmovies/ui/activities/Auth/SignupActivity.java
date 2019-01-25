package com.franktan.popularmovies.ui.activities.Auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.franktan.popularmovies.R;
import com.franktan.popularmovies.ui.activities.MovieGridActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String TAG="";
    ProgressBar progressBar;

    TextView pwdtxt,emailtxt,logintxt;
    Button signUpBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailtxt=(TextView) findViewById(R.id.signupemailedittxt);
        pwdtxt=(TextView) findViewById(R.id.signuppwdedittxt);
       progressBar=(ProgressBar) findViewById(R.id.signupProgressbar);
        logintxt=(TextView) findViewById(R.id.signuplogintxt);
        signUpBtn=(Button) findViewById(R.id.signupBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpNewUser();
            }
        });
        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }

    public void signUpNewUser(){
        String email=emailtxt.getText().toString();
        String pwd=pwdtxt.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd) ){
            Toast.makeText(getApplicationContext(),"fill all the fields",Toast.LENGTH_LONG).show();
            return;
        }
        else{

progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {
                                startActivity(new Intent(SignupActivity.this,Login.class));

                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");


                            } else {
                               progressBar.setVisibility(View.INVISIBLE);
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), " failed to create your account.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }

                        // ...

                    });
        }
    }

}