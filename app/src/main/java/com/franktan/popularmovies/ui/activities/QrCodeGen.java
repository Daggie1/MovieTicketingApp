package com.franktan.popularmovies.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.franktan.popularmovies.R;
import com.franktan.popularmovies.model.HistoryModel;
import com.franktan.popularmovies.model.TicketsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;

import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class QrCodeGen extends AppCompatActivity {

    String TAG = "GenerateQRCode";
DatabaseReference databaseReference;
    DatabaseReference databaseReferenceForHistory;
    ImageView qrImage;
Button gotoHomebtn,gotoTicketsBtn;
    String inputValue,userEmail;
    String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String mpathFull;
     String mMovieName;
     String mposterpath;
     String  mHall;
     String mticketsNo;
     String mtime;
     String  mpay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getUserInfo();
        databaseReference= FirebaseDatabase.getInstance().getReference("Tickets");
        databaseReferenceForHistory=FirebaseDatabase.getInstance().getReference("History");
        setContentView(R.layout.activity_qr_code_gen);
gotoHomebtn=(Button) findViewById(R.id.backtoHomeBtn);
        gotoTicketsBtn=(Button) findViewById(R.id.gotoTicketsBtn);

        gotoTicketsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(new Intent(getApplicationContext(),DisplayTicketsActivity.class));
            }
        });
        gotoHomebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(new Intent(getApplicationContext(),MovieGridActivity.class));
            }
        });
        qrImage = (ImageView) findViewById(R.id.QR_Image);
        Bundle bundle=getIntent().getExtras();
        mMovieName=bundle.getString("moviename");
        mposterpath=bundle.getString("posterPath");
        mHall=bundle.getString("hallName");
        mticketsNo=bundle.getString("ticketsNumber");
        mtime=bundle.getString("Time");
        mpay=bundle.getString("payTotal");
generateTicket();
saveTickets();

        }
        public void generateTicket(){
            inputValue ="Chania cinemas Qr Results"+"\n\n\n"+"Movie Name="+mMovieName+"\n"+"Selected Hall="+mHall+"\n"+"At="+mtime+"\n"+"No of tickets="+mticketsNo+"\n Payed via Mpesa";
            if (inputValue.length() > 0) {
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3 / 4;
                Bundle args=new Bundle();
                args.putString("hallNme",mHall);
                args.putString("MovieTime",mtime);
                args.putString("TicketsNo",mticketsNo);
                args.putString("PosterPath",mposterpath);
                qrgEncoder = new QRGEncoder(
                        inputValue, args,
                        QRGContents.Type.TEXT,
                        smallerDimension);
                try {
                    bitmap = qrgEncoder.encodeAsBitmap();
                    qrImage.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    Log.v(TAG, e.toString());
                }
            } else {
                Toast.makeText(this, "Movie Name is Empty", Toast.LENGTH_LONG).show();
            }
        }


    public void saveTickets(){
        boolean save;
        String result;
        Date date=new Date();
        mpathFull=mMovieName+userEmail+date.toString();
        try {
            save = QRGSaver.save(savePath,mpathFull, bitmap, QRGContents.ImageType.IMAGE_JPEG);

            result = save ? "Image Saved" : "Image Not Saved";
            if(result.equals("Image Saved")){ addTickets();addTohistory();}
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void addTickets() {
String realpath=savePath+mpathFull;


        if(!TextUtils.isEmpty(realpath) && !TextUtils.isEmpty(mMovieName) && !TextUtils.isEmpty(userEmail) ){

            String id=databaseReference.push().getKey();
            TicketsModel newticket=new TicketsModel(id,realpath,mMovieName,userEmail);
            databaseReference.child(id).setValue(newticket);
            Toast.makeText(this,"added successfully",Toast.LENGTH_LONG).show();
        return;}

        else{
            Toast.makeText(this,"Opps!! something happend ,not added",Toast.LENGTH_LONG).show();
        }}
    public void getUserInfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            userEmail  = user.getEmail();

            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
        else{
            userEmail="null";
        }
    }

    private void addTohistory() {



        if(!TextUtils.isEmpty(mMovieName) ){

            String id=databaseReference.push().getKey();
            HistoryModel newhistory=new HistoryModel(mMovieName);
            databaseReferenceForHistory.child(id).setValue(newhistory);
            Toast.makeText(this,"added successfully",Toast.LENGTH_LONG).show();
            return;}

        else{
            Toast.makeText(this,"Opps!! something happend ,not added",Toast.LENGTH_LONG).show();
        }}
}
