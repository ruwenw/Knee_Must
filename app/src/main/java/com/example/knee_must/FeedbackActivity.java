package com.example.knee_must;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {
Button submitfeba,btcamera,nextimg,previousimg,btnpickimg;
EditText feedback;
    Uri videouri;
    ProgressDialog progressDialog;
    ArrayList<Uri> imageUris;
    ImageSwitcher imagessw;
    Uri imageUri1;
    //Imageswitcher position
    int position=0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGES_CODE=0;
    SharedPreference sharedPref;
    StorageReference imagestorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPreference(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        submitfeba=findViewById(R.id.submitfeba);

        feedback=findViewById(R.id.etfeedback);
       /*
       imageUris =new ArrayList<>();
        //imageUris.add(getIntent().getIntExtra("uri",));
        imagessw=findViewById(R.id.imagessw);
        nextimg=findViewById(R.id.nextimg);
        previousimg=findViewById(R.id.previosimg);
        btnpickimg=findViewById(R.id.btnpickimg);
       btcamera=findViewById(R.id.btcamera);
        imagessw.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageview =new ImageView(getApplicationContext());
                return imageview;
            }
        });
        imagessw.setImageURI(imageUris.get(0));
        if(getIntent().getExtras()!=null)
            Toast.makeText(FeedbackActivity.this,getIntent().getExtras().toString(),Toast.LENGTH_SHORT).show();
        if(imageUris.isEmpty())
        {
            Toast.makeText(FeedbackActivity.this,"No images yet...",Toast.LENGTH_SHORT).show();
        }else
        {

            imagessw.setImageURI(imageUris.get(0));
        }*/

        btcamera.setOnClickListener(this);
        submitfeba.setOnClickListener(this);
        nextimg.setOnClickListener(this);
        previousimg.setOnClickListener(this);
        btnpickimg.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view==submitfeba)
        {
            //DataModel.patients.get(sharedPref.GetFirebaseNum()).setFeedback(feedback.getText().toString());
            DataModel.savePatients();
            Toast.makeText(getApplicationContext(), "Saved",
                    Toast.LENGTH_SHORT).show();
        }/*
        if(view==btcamera)
        {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (ActivityNotFoundException e) {
                // display error state to the user
            }
        }
        if(view==nextimg)
        {
            if(position<imageUris.size()-1){
                position++;
                imagessw.setImageURI(imageUris.get(position));
            }
            else
            {
                Toast.makeText(FeedbackActivity.this,"No more images...",Toast.LENGTH_SHORT).show();
            }
        }
        if(view==btnpickimg){
            pickImagesIntent();
        }
        if(view==previousimg){
            if(position>0){
                position--;
                imagessw.setImageURI(imageUris.get(position));

            }
            else{
                Toast.makeText(FeedbackActivity.this,"No previous images ...",Toast.LENGTH_SHORT).show();
            }

        }*/
    }
    /*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                imageUri1 = data.getData();
                imageUris.add(imageUri1);
                Toast.makeText(FeedbackActivity.this,imageUris.size(),Toast.LENGTH_SHORT).show();
                imagessw.setImageURI(imageUris.get(0));
                position=0;
                Intent i = new Intent(getApplicationContext(), FeedbackActivity.class);
                i.putExtra("uri",imageUri1);
                startActivity(i);
            }
        }

        public void pickImagesIntent()
        {
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            starttivityForResult(Intent.createChooser(intent,"Seclect images"),PICK_IMAGES_CODE);
        }/*
        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==PICK_IMAGES_CODE){
                if(resultCode== Activity.RESULT_OK){
                    if(data.getClipData()!=null){
                        //picked multible images
                        int cout=data.getClipData().getItemCount();//number of picked images
                        for(int i=0;i<cout;i++)
                        {
                            Uri imageUri=data.getClipData().getItemAt(i).getUri();
                            imageUris.add(imageUri);
                        }
                        imagessw.setImageURI(imageUris.get(0));
                        position=0;
                    }else{
                        //picked single image
                        Uri imageUri = data.getData();
                        imageUris.add(imageUri);
                        imagessw.setImageURI(imageUris.get(0));
                        position=0;
                    }
                }
            }
            if(resultCode==RESULT_OK&&requestCode==REQUEST_IMAGE_CAPTURE)
            {//not finished !!!
                //AlertDialog.Builder builder=new AlertDialog.Builder(this);
                imageUri1 = data.getData();
                imageUris.add(imageUri1);
                Toast.makeText(FeedbackActivity.this,imageUris.size(),Toast.LENGTH_SHORT).show();
                imagessw.setImageURI(imageUris.get(0));
                position=0;
                VideoView videoview=new VideoView(this);
                videoview.setVideoURI(data.getData());
                videoview.start();
                builder.setView(videoview).show();
                videouri = data.getData();
                //imageUris.add(videouri);
                progressDialog = new ProgressDialog(FeedbackActivity.this);

                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                uploadvideo();

            }
        }/*upload to fire base
         private String getfiletype(Uri videouri) {
            ContentResolver r = getContentResolver();
            // get the file type ,in this case its mp4
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(r.getType(videouri));
        }
        private void uploadvideo() {
            if (videouri != null) {
                // save the selected video in Firebase storage
                final StorageReference reference = FirebaseStorage.getInstance().getReference("Files/" + System.currentTimeMillis() + "." + getfiletype(videouri));
                reference.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        // get the link of video
                        String downloadUri = uriTask.getResult().toString();
                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Video");
                        HashMap<String, String> map = new HashMap<>();
                        map.put("videolink", downloadUri);
                        reference1.child("" + System.currentTimeMillis()).setValue(map);
                        // Video uploaded successfully
                        // Dismiss dialog
                        progressDialog.dismiss();
                        Toast.makeText(FeedbackActivity.this, "Video Uploaded!!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error, Image not uploaded
                        progressDialog.dismiss();
                        Toast.makeText(FeedbackActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    // Progress Listener for loading
                    // percentage on the dialog box
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        // show the progress bar
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    }
                });
            }
        }*/
    void restartapp() {
        Intent i = new Intent(getApplicationContext(), FeedbackActivity.class);
        i.putExtra("uri",imageUri1);
        startActivity(i);
        finish();
    }

}