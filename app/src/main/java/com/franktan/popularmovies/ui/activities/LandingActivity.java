package com.franktan.popularmovies.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.franktan.popularmovies.R;
import com.franktan.popularmovies.ui.activities.Auth.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class LandingActivity extends AppCompatActivity {
FirebaseAuth auth;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        progressBar=(ProgressBar) findViewById(R.id.loginProgressbar);
        startOtherActivity();
        setContentView(R.layout.activity_landing);

    }
    public void startOtherActivity(){
       String email=auth.getCurrentUser().getEmail();
        String username= auth.getCurrentUser().getDisplayName();

       if(email.equalsIgnoreCase("admin@gmail.com")){
           startActivity(new Intent(this,HistoryActivity.class));
       }
       else{
           startActivity(new Intent(this,MovieGridActivity.class));
       }



    }
    private void showAddItemDialog(Context c) {

        final EditText taskEditText = new EditText(c);
        final AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog))
                .setView(taskEditText)
                .setTitle("Add a UserName")
                .setPositiveButton(android.R.string.ok, null) //Set to null. We override the onclick
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                         String task = String.valueOf(taskEditText.getText());
                        if(!task.isEmpty()){

                            dialog.dismiss();};
                    }
                });
            }
        });
        dialog.show();
    }
    public void addinguserName(String theUsername)
    {
        FirebaseUser user = auth.getCurrentUser();
        progressBar.setVisibility(View.VISIBLE);
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(theUsername)
                .build();
        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "username added", Toast.LENGTH_LONG).show();
                    //FirebaseUser user = mAuth.getCurrentUser();
                    // updateUI(user);
                    startActivity(new Intent(getApplicationContext(), MovieGridActivity.class));
                }
            }
        });

        // Sign in success, update UI with the signed-in user's information



    }
    }
