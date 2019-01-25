package com.franktan.popularmovies.ui.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.franktan.popularmovies.R;


import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.rilixtech.CountryCodePicker;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
public class LNMPesaActivity extends AppCompatActivity {
    @BindView(R.id.editTextPhone)
    AppCompatEditText editTextPhoneNumber;
    @BindView(R.id.sendButton)
    Button sendButton;
    @BindView(R.id.ccp)
   CountryCodePicker ccp;
    @BindView(R.id.mymovie_name)
    TextView movienametxt;
    @BindView(R.id.mypayment)
    TextView movietotalPaytxt;
    @BindView(R.id.mytickets_no)
    TextView ticketsNotxt;
    @BindView(R.id.mymovieTime)
    TextView movieTimetxt;
    @BindView(R.id.myhallname)
    TextView movieHalltxt;
    @BindView(R.id.myImageposter)
    ImageView movieposterImage;
@BindView(R.id.mydate)
    TextView mydate
    ;
    //Declare Daraja :: Global Variable
    Daraja daraja;

    String phoneNumber;
    String mMovieName,mposterpath,mtime,mHall,mticketsNo,mpay,mdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
 mMovieName=bundle.getString("moviename");
         mposterpath=bundle.getString("posterPath");
         mHall=bundle.getString("hallName");
         mticketsNo=bundle.getString("ticketsNumber");
         mtime=bundle.getString("Time");
         mpay=bundle.getString("payTotal");
         mdate=bundle.getString("date");
        //editTextPhoneNumber=(AppCompatEditText) findViewById(R.id.editTextPhoneNumber);




        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lnmpesa);
        ButterKnife.bind(this);
        editTextPhoneNumber.setHint("7XXXXXXXXX");
        ccp.registerPhoneNumberTextView(editTextPhoneNumber);

        ticketsNotxt.setText(mticketsNo);
        movieHalltxt.setText(mHall);
        movienametxt.setText(mMovieName);
        movieTimetxt.setText(mtime);
        movietotalPaytxt.setText(mpay);
        mydate.setText(mdate);
        Picasso.with(getApplicationContext()).load(mposterpath).fit().centerCrop().into(movieposterImage);
        //Init Daraja
        //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
        daraja = Daraja.with("YTZZTAUnMdOccHYU6ipgdCBmRHMHPDqA", "NKTmGMpbMCuo1pQc", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.i(LNMPesaActivity.this.getClass().getSimpleName(), accessToken.getAccess_token());
                Toast.makeText(LNMPesaActivity.this, "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(LNMPesaActivity.this.getClass().getSimpleName(), error);
            }
    });


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber=ccp.getFullNumber().trim();
                if(TextUtils.isEmpty(phoneNumber)) {
                    editTextPhoneNumber.setError("Please Provide a Phone Number");
                }
                    else if(TextUtils.indexOf(phoneNumber,"254",0)==254){
                }
                Toast.makeText(LNMPesaActivity.this,phoneNumber,Toast.LENGTH_LONG).show();
                final LNMExpress lnmExpress=new LNMExpress(
                        "174379",
                        "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                        mpay,
                        "254708374149",
                        "174379",
                        phoneNumber,
                        "http://mycallbackurl.com/checkout.php",
                        "testapi0287",
                        "Goods Payment"
                );

                daraja.requestMPESAExpress(lnmExpress, new DarajaListener<LNMResult>() {
                    @Override
                    public void onResult(@NonNull LNMResult lnmResult) {
Toast.makeText(getApplicationContext(),lnmResult.CheckoutRequestID,Toast.LENGTH_LONG).show();
if(lnmResult.CustomerMessage!=""){
    startGenerationActivity();
}
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(LNMPesaActivity.this, "got error:"+error, Toast.LENGTH_LONG).show();
                        Log.e(LNMPesaActivity.this.getClass().getSimpleName(), error);
                    }
                });
            }
        });
    }
    public void startGenerationActivity(){
        Intent newIntent= new Intent(LNMPesaActivity.this,QrCodeGen.class);
        newIntent.putExtra("hallName",mHall);
        newIntent.putExtra("Time",mtime);
        newIntent.putExtra("ticketsNumber",mticketsNo);
        newIntent.putExtra("posterPath",mposterpath);
        newIntent.putExtra("moviename",mMovieName);
        newIntent.putExtra("payTotal",mpay);
        newIntent.putExtra("date",mdate);
        startActivity(newIntent);
    }

}
