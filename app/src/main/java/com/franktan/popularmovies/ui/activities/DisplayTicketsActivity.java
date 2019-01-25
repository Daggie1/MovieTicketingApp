package com.franktan.popularmovies.ui.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.franktan.popularmovies.R;
import com.franktan.popularmovies.model.TicketsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class DisplayTicketsActivity extends AppCompatActivity {
    public static final String MOVIE_NAME = "";
    public static final String QR_PATH="";
    private static final String MOVIE_NAME_CONST ="movie_Name_contatnt";
    private Uri photopath;
    private static final int PICK_PHOTO_REQUEST_CODE = 0700;
    Button addService;
    private Uri downloadUrl;
    TicketsModel ticketsModel;
    EditText serviceName,mintxt,maxtxt;
    RadioGroup availabilityRadiogroup;
    RadioButton availableradio,comingSoonRadio;
    ImageView addpicImageView;
String userEmail;
    //Query dbref;

   Query dbreference;
    ArrayList<TicketsModel> listitems = new ArrayList<>();
    RecyclerView MyRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        // initializeList();
        getUserInfo();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tickets);
if(userEmail.equals("")){
    Toast.makeText(getApplicationContext(), "Unkown user", Toast.LENGTH_LONG).show();
    return;
}

        dbreference=FirebaseDatabase.getInstance().getReference("Tickets").orderByChild("userThe").equalTo(userEmail);
        //dbref= FirebaseDatabase.getInstance().getReference("Movies").orderByChild("availability").equalTo(true);


        MyRecyclerView = (RecyclerView) findViewById(R.id.ticketsRecyclerView);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(this);
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (listitems.size() >=0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new MyAdapter(listitems));
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);

updateUI();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();

    }

    @Override
    public void onResume() {
        super.onResume();
updateUI();
    }





    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<TicketsModel> list;

        public MyAdapter(ArrayList<TicketsModel> Data) {
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tickets_recycler_items, parent, false);
            MyViewHolder holder = new MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            ticketsModel=listitems.get(position);

            holder.titleTextView.setText(ticketsModel.getQrname());
            final Uri uri=Uri.parse(ticketsModel.getQrpath());
            try {
                final InputStream inputStream=getContentResolver().openInputStream(uri);
                Bitmap selectedImage= BitmapFactory.decodeStream(inputStream);
                holder.coverImageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }






            //holder.coverImageView.setImageResource(list.get(position).getImageResourceId());
            //holder.coverImageView.setTag(list.get(position).getImageResourceId());
            //holder.likeImageView.setTag(R.drawable.trailerplay);


        }

        @Override
        public int getItemCount() {
            return list.size();
        }


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public ImageView coverImageView;



        public MyViewHolder(View v) {
            super(v);

            titleTextView = (TextView) v.findViewById(R.id.nontitleTextView);
            coverImageView = (ImageView) v.findViewById(R.id.noncoverImageViewnon);





            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getApplicationContext(),SelectedQr.class);
                    intent.putExtra(MOVIE_NAME,titleTextView.getText().toString());
                    intent.putExtra(QR_PATH,titleTextView.getText().toString());
                    startActivity(intent);
                }
            });




//new BookFragment().bookMovie(getActivity(),titleTextView.getText().toString());


                    //

                    /*Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                            "://" + getResources().getResourcePackageName(coverImageView.getId())
                            + '/' + "drawable" + '/' + getResources().getResourceEntryName((int)coverImageView.getTag()));


                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
                    shareIntent.setType("image/jpeg");
                    startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));*/



                }
            }

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
    }

    public void updateUI(){
        dbreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listitems.clear();
                for (DataSnapshot ticketseSnapshot : dataSnapshot.getChildren()) {

                    TicketsModel ticketsList = ticketseSnapshot.getValue(TicketsModel.class);
                    listitems.add(ticketsList);
                    new MyAdapter(listitems).notifyDataSetChanged();

                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

        }


































