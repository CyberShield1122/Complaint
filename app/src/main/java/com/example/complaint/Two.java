package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Two extends AppCompatActivity {

    ImageView imageView,imageViewcamera,imageViewgallery;
    DatabaseReference ChildFoundDB ;

    TextInputEditText textInputLayout,textInputLayout1,name,age,city,address,additionaladd,phonenumber,fathername,childcnic,fathercnic;
    AutoCompleteTextView autoCompleteTxtgender;
    Button submit;
    String gender="";
    Uri uri;
    FirebaseStorage firebaseStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ChildFoundDB= FirebaseDatabase.getInstance().getReference().child("Missing Child Information");
        String[] items={"Male","Female"};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        getSupportActionBar().hide();

        ArrayAdapter<String> adapterItems;
        autoCompleteTxtgender=findViewById(R.id.gender);
        adapterItems= new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxtgender.setAdapter(adapterItems);
        name=findViewById(R.id.name);
        fathername=findViewById(R.id.Fathername);
        childcnic=findViewById(R.id.childcnic);
        fathercnic=findViewById(R.id.fathercnic);
        age=findViewById(R.id.age);
        city=findViewById(R.id.city);
        address=findViewById(R.id.address);
        additionaladd=findViewById(R.id.additionalAdd);
        phonenumber=findViewById(R.id.phonenubmer);
        submit=findViewById(R.id.submit);
        //imageViewcamera=findViewById(R.id.imageCamera);
        imageView=findViewById(R.id.image);
        //imageViewgallery=findViewById(R.id.imageGallery);
        textInputLayout=findViewById(R.id.date);
        textInputLayout1=findViewById(R.id.time);
        textInputLayout1.setInputType(InputType.TYPE_NULL);
        textInputLayout.setInputType(InputType.TYPE_NULL);
        autoCompleteTxtgender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String item= adapterView.getItemAtPosition(position).toString();
                gender=item;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FragmentDailogBox().show(getSupportFragmentManager(),"fragmentDailog");



            }
        });

        textInputLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar co = Calendar.getInstance();
                int mHour = co.get(Calendar.HOUR_OF_DAY);
                int mMinute = co.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Two.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                textInputLayout1.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        textInputLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Two.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                textInputLayout.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String childname=name.getText().toString();
                String fathernam=fathername.getText().toString();
                String childcni=childcnic.getText().toString();
                String fathercni=fathercnic.getText().toString();
                String childage=age.getText().toString();
                String childgender=gender;
                String childdate=textInputLayout.getText().toString();
                String childtime=textInputLayout1.getText().toString();
                String childcity=city.getText().toString();
                String childaddress=address.getText().toString();
                String childadditionaladd=additionaladd.getText().toString();
                String phone=phonenumber.getText().toString();
                int l=phone.length();
                if(childname.matches("")){
                    Toast.makeText(Two.this, "Enter Child Name", Toast.LENGTH_SHORT).show();
                }

                else
                if( fathernam.matches("")){
                    Toast.makeText(Two.this, "Enter Father Name", Toast.LENGTH_SHORT).show();
                }else
                if( childcni.matches("")){
                    Toast.makeText(Two.this, "Enter Child CNIC", Toast.LENGTH_SHORT).show();
                }else
                if( fathercni.matches("")){
                    Toast.makeText(Two.this, "Enter Father CNIC", Toast.LENGTH_SHORT).show();
                } else
                if( childage.matches("")){
                    Toast.makeText(Two.this, "Enter Age", Toast.LENGTH_SHORT).show();
                }
                else  if( childgender.matches("")){
                    Toast.makeText(Two.this, "Select Gender", Toast.LENGTH_SHORT).show();
                }
                else  if( childdate.matches("")){
                    Toast.makeText(Two.this, "Select Date", Toast.LENGTH_SHORT).show();
                }
                else  if( childtime.matches("")){
                    Toast.makeText(Two.this, "Select Time", Toast.LENGTH_SHORT).show();
                }
                else  if( childcity.matches("")){
                    Toast.makeText(Two.this, "Select city", Toast.LENGTH_SHORT).show();
                }
                else  if( childaddress.matches("")){
                    Toast.makeText(Two.this, "Enter Address", Toast.LENGTH_SHORT).show();
                }
                else  if( childadditionaladd.matches("")){
                    Toast.makeText(Two.this, "Select Additional Address", Toast.LENGTH_SHORT).show();
                }
                else if(l==10) {


                    final StorageReference reference=firebaseStorage.getReference().child("images").child(System.currentTimeMillis()+"");
                    reference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {


                                    TwoModel users=new TwoModel(uri.toString(),childname,fathernam,childcni,fathercni,childage,childgender,childdate,
                                            childtime,childcity,childaddress,childadditionaladd,phone);
                                    ChildFoundDB.push().setValue(users);
                                    Toast.makeText(Two.this, "Report Submitted", Toast.LENGTH_SHORT).show();
                                    Intent A= new Intent(Two.this,MainActivity.class);
                                    startActivity(A);
                                    finish();

                                }
                            });
                        }
                    });


                }
                else{
                    Toast.makeText(Two.this, "Enter 10 character Phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){

            if (requestCode==1000){
                imageView.setImageURI(data.getData());
                uri=data.getData();
            }
            if (requestCode==100){
                Bitmap img = (Bitmap) (data.getExtras().get("data"));
                imageView.setImageBitmap(img);
                Uri tempUri = getImageUri(getApplicationContext(), img);
                uri=tempUri;
            }
        }


    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage,
                "Title", null);
        return Uri.parse(path);
    }
}