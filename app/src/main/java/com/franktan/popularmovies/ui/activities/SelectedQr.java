package com.franktan.popularmovies.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.franktan.popularmovies.R;
import com.franktan.popularmovies.model.TicketsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SelectedQr extends AppCompatActivity {
String mymovieName,userEmail;
ImageView showQrImageView;
TextView QrNameTxt;
Query dbrefqrpath;
Button backtoHomeqrBtn;
String qrurl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mymovieName = getIntent().getStringExtra(DisplayTicketsActivity.MOVIE_NAME);
        dbrefqrpath = FirebaseDatabase.getInstance().getReference("Tickets").child("qrname").orderByChild(mymovieName);
        //dbrefgetuer = FirebaseDatabase.getInstance().getReference("Tickets").child("qrname").orderByChild(mymovieName);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_qr);
        QrNameTxt = (TextView) findViewById(R.id.qrNametxt);
        backtoHomeqrBtn=(Button) findViewById(R.id.fromOrtoHomeBtn);
        QrNameTxt.setText(mymovieName);
        showQrImageView = (ImageView) findViewById(R.id.showqr_imageView);
        backtoHomeqrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MovieGridActivity.class));
            }
        });


        getMovieUrl();
    }

    public void getMovieUrl() {

        dbrefqrpath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              if(!TextUtils.isEmpty(mymovieName)){
                for(DataSnapshot ticketsSnapshot:dataSnapshot.getChildren()){
                    TicketsModel tcksModel=ticketsSnapshot.getValue(TicketsModel.class);
                    qrurl=tcksModel.getQrpath();
                    Picasso.with(getApplicationContext()).load(qrurl).placeholder(getResources().getDrawable(R.drawable.backdrop_loading_placeholder)).into(showQrImageView);

                }
              }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
