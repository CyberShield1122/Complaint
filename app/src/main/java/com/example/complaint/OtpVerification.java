package com.example.complaint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OtpVerification extends AppCompatActivity {
    Button button;
    TextView textView;
    TextView textView2;

    private String verificationId;
    private String countryname;
    private String cityname;
    private String username;
    private String phonenumber;

    DatabaseReference userDb ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyotp);
        getSupportActionBar().hide();
        final EditText et= findViewById(R.id.otp);
        verificationId =getIntent().getStringExtra("verificationId");
        countryname =getIntent().getStringExtra("countryname");
        cityname =getIntent().getStringExtra("cityname");
        username =getIntent().getStringExtra("username");
        phonenumber =getIntent().getStringExtra("phonenumber");
        button= findViewById(R.id.buttonverify);
        textView= findViewById(R.id.resend);

        //setContentView(R.id.change);
        TextView tv1 = (TextView)findViewById(R.id.change);
        tv1.setText("+92 "+phonenumber);

        textView2= findViewById(R.id.edit);
        userDb=FirebaseDatabase.getInstance().getReference().child("Users");

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent A= new Intent(OtpVerification.this,UserRegistration.class);
                startActivity(A);
                finish();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OtpVerification.this, "Resend wait", Toast.LENGTH_SHORT).show();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+92" + phonenumber, 60, TimeUnit.SECONDS, OtpVerification.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                               // Toast.makeText(UserRegistration.this,"oan",Toast.LENGTH_LONG);
                            }
                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                            }
                            @Override
                            public  void onCodeSent(@NonNull String verificationId ,
                                                    @NonNull PhoneAuthProvider.ForceResendingToken
                                                            forceResendingToken){
                                super.onCodeSent(verificationId,forceResendingToken);

                            }
                        }
                );
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificationId!=null){
                    PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(
                            verificationId,et.getText().toString()
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

                                        String UID=currentFirebaseUser.getUid();
                                        Usermodel users=new Usermodel(countryname,cityname,username,phonenumber,UID);
                                        userDb.child(UID).setValue(users);


                                        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
            }
        });
    }
}