package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class UserRegistration extends AppCompatActivity {
    String[] items={"Pakistan"};
    String[] items1={"Gujrat","Jhelum","Sheikhupura","lahore","Gujrat","Jhelum","Sheikhupura","lahore"
            ,"Gujrat","Jhelum","Sheikhupura","lahore"};
    String[] items2={"English","اردو"};
    AutoCompleteTextView autoCompleteTxt;
    AutoCompleteTextView autoCompleteTxt1;
    AutoCompleteTextView autoCompleteTxt2;
    Button buttonR;
    EditText username;
    EditText userphone;
    ArrayAdapter<String> adapterItems;
    ArrayAdapter<String> adapterItems1;
    ArrayAdapter<String> adapterItems2;
    String country="";
    String city="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        getSupportActionBar().hide();
        loadlocal();
        autoCompleteTxt=findViewById(R.id.auto_copmpete_txt);
        autoCompleteTxt1=findViewById(R.id.auto_copmpete_txt2);
        autoCompleteTxt2=findViewById(R.id.langu);
        username =findViewById(R.id.username1);
        userphone=findViewById(R.id.userphone1);
        buttonR=findViewById(R.id.btn_register);


        adapterItems= new ArrayAdapter<String>(this,R.layout.list_item,items);
        adapterItems1= new ArrayAdapter<String>(this,R.layout.list_item,items1);
        adapterItems2= new ArrayAdapter<String>(this,R.layout.list_item,items2);

        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt1.setAdapter(adapterItems1);
        autoCompleteTxt2.setAdapter(adapterItems2);
        autoCompleteTxt2.setThreshold(1000);


        final ProgressBar progressBar=findViewById(R.id.progressbar);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String item= adapterView.getItemAtPosition(position).toString();
                 country=item;
            }
        });
        autoCompleteTxt2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selectlanguage= adapterView.getItemAtPosition(position).toString();
                if (selectlanguage.equals("English")) {
                    setLocal(UserRegistration.this,"en");
                    recreate();
                } else if (selectlanguage.equals("اردو")) {
                    setLocal(UserRegistration.this,"ur");
                    recreate();
                }if (selectlanguage.equals("سندھی")) {
                    setLocal(UserRegistration.this,"sd");
                   recreate();
                } else if (selectlanguage.equals("پنجابی")) {
                    setLocal(UserRegistration.this,"pa");
                    recreate();
                }
            }
        });
        autoCompleteTxt1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String item= adapterView.getItemAtPosition(position).toString();
                city= item;
            }
        });
        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usename=username.getText().toString();
                String usephone=userphone.getText().toString();
                int l=usephone.length();
                if(country==""){
                    Toast.makeText(UserRegistration.this, "Select Country", Toast.LENGTH_SHORT).show();
                }else if(city==""){
                    Toast.makeText(UserRegistration.this, "Select City", Toast.LENGTH_SHORT).show();
                }else
                    if( usename.matches("")){
                    Toast.makeText(UserRegistration.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }else  if( usephone.matches("")){
                    Toast.makeText(UserRegistration.this, "Enter Phone number", Toast.LENGTH_SHORT).show();
                }else if(l==10) {



                        progressBar.setVisibility(view.VISIBLE);
                        buttonR.setVisibility(view.INVISIBLE);
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+92" + usephone, 60, TimeUnit.SECONDS, UserRegistration.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(UserRegistration.this,"oan",Toast.LENGTH_LONG);
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                            }
                            @Override
                            public  void onCodeSent(@NonNull String verificationId ,
                                                    @NonNull PhoneAuthProvider.ForceResendingToken
                                                            forceResendingToken){
                                super.onCodeSent(verificationId,forceResendingToken);
                                Intent intent= new Intent(getApplicationContext(), OtpVerification.class);
                                intent.putExtra("verificationId",verificationId);
                                intent.putExtra("countryname",country);
                                intent.putExtra("cityname",city);
                                intent.putExtra("username",usename);
                                intent.putExtra("phonenumber",usephone);
                                startActivity(intent);
                                finish();
                            }
                        }
                  );
                }
                    else{
                        Toast.makeText(UserRegistration.this, "Enter 10 character Phone number", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
    public void setLocal(Activity activity, String langCode){
        Locale local=new Locale(langCode);
        local.setDefault(local);
        Resources resources=activity.getResources();
        Configuration config =resources.getConfiguration();
        config.setLocale(local);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My Lang",langCode);
        editor.apply();
    }
    public void loadlocal(){
        SharedPreferences sharedPreferences= getSharedPreferences("Settings",Activity.MODE_PRIVATE);
        String lang=sharedPreferences.getString("My Lang","");
        setLocal(UserRegistration.this,lang);
    }
}