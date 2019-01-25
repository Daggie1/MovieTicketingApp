package com.franktan.popularmovies.ui.activities.Auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.franktan.popularmovies.R;
import com.franktan.popularmovies.ui.activities.LandingActivity;
import com.franktan.popularmovies.ui.activities.MovieGridActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String TAG="";
    TextView pwdtxt,emailtxt,signuptxt;
    Button loginBtn;

    ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailtxt=(TextView) findViewById(R.id.emailedittxt);
        pwdtxt=(TextView) findViewById(R.id.pwdedittxt);
progressBar=(ProgressBar) findViewById(R.id.loginProgressbar);
        signuptxt=(TextView) findViewById(R.id.loginsignuptxt);
        loginBtn=(Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginExistinguser();
            }
        });
        signuptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignupActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){

        }
    }


    public void loginExistinguser(){
        String email=emailtxt.getText().toString();
        String pwd=pwdtxt.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pwd)){
            Toast.makeText(getApplicationContext(),"fill all the fields",Toast.LENGTH_LONG).show();
            return;
        }
        else{
            progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            startActivity(new Intent(getApplicationContext(), LandingActivity.class));

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                           progressBar.setVisibility(View.INVISIBLE);

                        }

                        // ...
                    }
                });
    }}
}