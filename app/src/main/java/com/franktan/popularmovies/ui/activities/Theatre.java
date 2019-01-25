package com.franktan.popularmovies.ui.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.franktan.popularmovies.R;
import com.franktan.popularmovies.ui.fragments.MovieDetailFragment;
import com.squareup.picasso.Picasso;

public class Theatre extends AppCompatActivity implements View.OnClickListener {
DatePicker datePicker;
    String movieNme,posterPath="";
    String mHallName;
    String mTime;
    String mticketsNo;
    String mposterPAth;
    String mtotalCash;
    String mmovieName;
    TextView ticketsNotxt,Totaltxt;
    ImageButton addbtn,subtractbtn;
    Button AMC2,AMC3,AMC4,AMC5,RC1,RC2,RC3,RC4,LMC1,LMC2,LMC3,LMC4,LMC5;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theatre);

        Bundle b = getIntent().getExtras();
        posterPath = b.getString(MovieDetailFragment.SELECTED_MOVIE_PATH);
        movieNme = b.getString(MovieDetailFragment.SELECTED_MOVIE_NAME);
        datePicker=(DatePicker) findViewById(R.id.theatredatePicker) ;

Totaltxt=(TextView) findViewById(R.id.totaltxt);
        ticketsNotxt =(TextView) findViewById(R.id.ticketsNotxt);
        TextView realMovieName=(TextView) findViewById(R.id.realMovieNametetxt);
        realMovieName.setText(movieNme);
        Button nxtBtn = (Button) findViewById(R.id.GotoPay);
         AMC2 = (Button) findViewById(R.id.AMC2);//"02.15 PM")
        AMC3 = (Button) findViewById(R.id.AMC3);//"05.30 PM"
        AMC4 = (Button) findViewById(R.id.AMC4);//"07.15 PM"
         AMC5 = (Button) findViewById(R.id.AMC5);//09.15 PM"

         RC1 = (Button) findViewById(R.id.RC1);//"12.05 PM"
         RC2 = (Button) findViewById(R.id.RC2);//"04.15 PM"
        RC3 = (Button) findViewById(R.id.RC3);//"07.05 PM"
        RC4 = (Button) findViewById(R.id.RC4);//9.15 PM"

         LMC1 = (Button) findViewById(R.id.LMC1);//"11.30 PM"
         LMC2 = (Button) findViewById(R.id.LMC2);//"02.45 PM"
         LMC3 = (Button) findViewById(R.id.LMC3);//"04.30 PM"
         LMC4 = (Button) findViewById(R.id.LMC4);//"07.05 PM"
         LMC5 = (Button) findViewById(R.id.LMC5);//"09.15 PM"

        subtractbtn=(ImageButton)findViewById(R.id.subtractTicketsBtn);
        addbtn=(ImageButton) findViewById(R.id.addTicketsBtn);
        ImageView posterImageView = (ImageView) findViewById(R.id.movie_poster_real_imageview);
        Picasso.with(getApplicationContext()).load(posterPath).placeholder(getDrawable(R.drawable.backdrop_loading_placeholder)).fit().centerCrop().into(posterImageView);
       // a1.setOnClickListener(this);
        //Chania East Theatre Hall
        AMC2.setOnClickListener(this);
        AMC3.setOnClickListener(this);
        AMC4.setOnClickListener(this);
        AMC5.setOnClickListener(this);
//Chania West Theatre Hall
        RC1.setOnClickListener(this);
        RC2.setOnClickListener(this);
        RC3.setOnClickListener(this);
        RC4.setOnClickListener(this);
//Chania East Theatre Hall
        LMC1.setOnClickListener(this);
        LMC2.setOnClickListener(this);
        LMC3.setOnClickListener(this);
        LMC4.setOnClickListener(this);
        LMC5.setOnClickListener(this);
View v=LMC1;
        addbtn.setOnClickListener(this);
        subtractbtn.setOnClickListener(this);
nxtBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String tickets;
        String valueinTickets=ticketsNotxt.getText().toString().trim();
        switch (id) {
            case R.id.AMC2:

                if(valueinTickets.equals("0")){
                    ticketsNotxt.setText("1");
                    Totaltxt.setText("10");
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                else{
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                mHallName=getString(R.string.chania_east_theatre_hall);
                mTime="2:15 PM";

                mposterPAth=posterPath;
                mmovieName=movieNme;
                settingColor(AMC2);
                break;
            case R.id.AMC3:

                mHallName=getString(R.string.chania_east_theatre_hall);
                mTime="05.30 PM";
                if(valueinTickets.equals("0")){
                    ticketsNotxt.setText("1");
                    Totaltxt.setText("10");
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                else{
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                mposterPAth=posterPath;
                mmovieName=movieNme;
                settingColor(AMC3);
                break;
            case R.id.AMC4:

                mHallName=getString(R.string.chania_east_theatre_hall);
                mTime="07.15 PM";
                if(valueinTickets.equals("0")){
                    ticketsNotxt.setText("1");
                    Totaltxt.setText("10");
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                else{
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                mposterPAth=posterPath;
                mmovieName=movieNme;
                startPayActivity(getString(R.string.chania_east_theatre_hall),"07.15 PM","",posterPath,movieNme);
                settingColor(AMC4);
                break;
            case R.id.AMC5:

                mHallName=getString(R.string.chania_east_theatre_hall);
                mTime="9:15 PM";
                if(valueinTickets.equals("0")){
                    ticketsNotxt.setText("1");
                    Totaltxt.setText("10");
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                else{
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                mposterPAth=posterPath;
                mmovieName=movieNme;
                settingColor(AMC5);
                break;
            case R.id.RC1:

                mHallName=getString(R.string.chania_west_theatre_hall);
                mTime="12.05 PM";
                if(valueinTickets.equals("0")){
                    ticketsNotxt.setText("1");
                    Totaltxt.setText("10");
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                else{
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                mposterPAth=posterPath;
                mmovieName=movieNme;
                settingColor(RC1);
                break;
            case R.id.RC2:

                mHallName=getString(R.string.chania_west_theatre_hall);
                mTime="04.15 PM";
                if(valueinTickets.equals("0")){
                    ticketsNotxt.setText("1");
                    Totaltxt.setText("10");
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                    settingColor(RC2);
                }
                else{
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                mposterPAth=posterPath;
                mmovieName=movieNme;
                settingColor(RC2);
                break;
            case R.id.RC3:

                mHallName=getString(R.string.chania_west_theatre_hall);
                mTime="07.05 PM";
                if(valueinTickets.equals("0")){
                    ticketsNotxt.setText("1");
                    Totaltxt.setText("10");
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                else{
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                mposterPAth=posterPath;
                mmovieName=movieNme;
                settingColor(RC3);
                break;
            case R.id.RC4:

                mHallName=getString(R.string.chania_west_theatre_hall);
                mTime="9.15 PM";
                if(valueinTickets.equals("0")){
                    ticketsNotxt.setText("1");
                    Totaltxt.setText("10");
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                else{
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                mposterPAth=posterPath;
                mmovieName=movieNme;
                settingColor(RC4);
                break;
            case R.id.LMC1:

                mHallName=getString(R.string.chania_north_theatre_hall);
                mTime="11.30 PM";
                if(valueinTickets.equals("0")){
                    ticketsNotxt.setText("1");
                    Totaltxt.setText("10");
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                else{
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                mposterPAth=posterPath;
                mmovieName=movieNme;
                settingColor(LMC1);
                break;
            case R.id.LMC2:

                mHallName=getString(R.string.chania_north_theatre_hall);
                mTime="02.45 PM";
                if(valueinTickets.equals("0")){
                    ticketsNotxt.setText("1");
                    Totaltxt.setText("10");
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                else{
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                mposterPAth=posterPath;
                mmovieName=movieNme;
                settingColor(LMC2);
                break;
            case R.id.LMC3:

                mHallName=getString(R.string.chania_north_theatre_hall);
                mTime="04.30 PM";
                if(valueinTickets.equals("0")){
                    ticketsNotxt.setText("1");
                    Totaltxt.setText("10");
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                else{
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                mposterPAth=posterPath;
                mmovieName=movieNme;
                settingColor(LMC3);
                break;
            case R.id.LMC4:

                mHallName=getString(R.string.chania_north_theatre_hall);
                mTime="07.05 PM";
                if(valueinTickets.equals("0")){
                    ticketsNotxt.setText("1");
                    Totaltxt.setText("10");
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                else{
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                mposterPAth=posterPath;
                mmovieName=movieNme;
                settingColor(LMC4);
                break;
            case R.id.LMC5:

                mHallName=getString(R.string.chania_north_theatre_hall);
                mTime="09.15 PM";
                if(valueinTickets.equals("0")){
                    ticketsNotxt.setText("1");
                    Totaltxt.setText("10");
                }
                else{
                    mticketsNo=ticketsNotxt.getText().toString().trim();
                }
                mposterPAth=posterPath;
                mmovieName=movieNme;
                settingColor(LMC5);
                break;
            case R.id.addTicketsBtn:
                int ticketvalue=Integer.parseInt(ticketsNotxt.getText().toString().trim());
                int total_cash=Integer.parseInt(Totaltxt.getText().toString().trim());

                ticketvalue=ticketvalue+1;
                total_cash=total_cash+10;
                Totaltxt.setText(String.valueOf(total_cash));
                mtotalCash=String.valueOf(total_cash);
                ticketsNotxt.setText(String.valueOf(ticketvalue));
                mticketsNo=String.valueOf(ticketvalue);
                break;

                case R.id.subtractTicketsBtn:
                    if (!ticketsNotxt.getText().toString().equals("0")) {

                        int total_cash2=Integer.parseInt(Totaltxt.getText().toString());
                    int ticketvalue2=Integer.parseInt(ticketsNotxt.getText().toString());
                    ticketvalue2=ticketvalue2-1;
                        total_cash2=total_cash2-10;
                        Totaltxt.setText(String.valueOf(total_cash2));
                        mtotalCash=String.valueOf(total_cash2);
                    ticketsNotxt.setText(String.valueOf(ticketvalue2));
                    mticketsNo=String.valueOf(ticketvalue2);}
            break;
            case R.id.GotoPay:
                startPayActivity(mHallName,mTime,mticketsNo,posterPath,movieNme);
        }


    }
    public void startPayActivity(String HallName,String Time,String ticketsNo,String posterPAth,String movieName){
        String date=datePicker.getDayOfMonth()+"";
        String month=datePicker.getMonth()+"";
        String realdate=date+" /"+month;
        if (!TextUtils.isEmpty(mTime)&& !TextUtils.isEmpty(date)){
            mtotalCash=Totaltxt.getText().toString();
            Intent newIntent= new Intent(Theatre.this,LNMPesaActivity.class);
            newIntent.putExtra("hallName",HallName);
            newIntent.putExtra("Time",Time);
            newIntent.putExtra("ticketsNumber",ticketsNo);
            newIntent.putExtra("posterPath",posterPAth);
            newIntent.putExtra("moviename",movieName);
            newIntent.putExtra("payTotal",mtotalCash);
            newIntent.putExtra("date",realdate);
            startActivity(newIntent);

        }
        if (TextUtils.isEmpty(mTime)){
            Toast.makeText(getApplicationContext(), "Ensure You Select A Hall and Time before Proceeding to pay", Toast.LENGTH_SHORT).show();
           }
    }

    public void settingColor(View v){
        Button [] buttonsArray={AMC2,AMC3,AMC4,AMC5,RC1,RC2,RC3,RC4,LMC1,LMC2,LMC3,LMC4,LMC5};
        for (int i=0;i<=(buttonsArray.length-1);i++){
            int color = ((ColorDrawable) v.getBackground()).getColor();

            if(v.equals(buttonsArray[i])){
                //int color = ((ColorDrawable) v.getBackground()).getColor();
                if(((ColorDrawable) v.getBackground()).getColor()==getResources().getColor(R.color.popularmovies_primary)){
                    v.setBackgroundColor(getResources().getColor(R.color.green));

                    return;
                } else{
                    v.setBackgroundColor(getResources().getColor(R.color.popularmovies_primary));
                   // v.setBackgroundColor(getResources().getColor(R.color.green));

                    mTime="";
                }

            }
           // Toast.makeText(this,color+"",Toast.LENGTH_LONG).show();
           // v.setBackgroundColor(getResources().getColor(R.color.popularmovies_primary));
        }

    }
}