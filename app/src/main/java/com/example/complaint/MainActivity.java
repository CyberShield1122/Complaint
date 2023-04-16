package com.example.complaint;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;
public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mfirebase;
    String[] items={"English","اردو"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    LinearLayout linearLayout,linearLayout2,linearLayout3,linearLayout7,linearLayout8,linearLayout9;
    ImageView imageView,fbicon,twicon,signout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mfirebase=FirebaseAuth.getInstance();
        loadlocal();
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        autoCompleteTxt=findViewById(R.id.spinner);
        linearLayout=findViewById(R.id.one);
        linearLayout2=findViewById(R.id.Two);
        linearLayout3=findViewById(R.id.Three);
        linearLayout7=findViewById(R.id.seven);
        linearLayout8=findViewById(R.id.eight);
        linearLayout9=findViewById(R.id.nine);
        imageView=findViewById(R.id.whatsappicon);
        fbicon=findViewById(R.id.fbicon);
        twicon=findViewById(R.id.twicon);
        signout=findViewById(R.id.signout);
        adapterItems= new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setThreshold(100);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user=mfirebase.getCurrentUser();
                startActivity(new Intent(MainActivity.this,UserRegistration.class));
                mfirebase.signOut();
                finish();
                Toast.makeText(MainActivity.this, "mm", Toast.LENGTH_SHORT).show();
            }
        });
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, One.class);
                startActivity(intent);
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, Two.class);
                startActivity(intent);
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, Three.class);
                startActivity(intent);
            }
        });
        linearLayout7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, Seven.class);
                startActivity(intent);
            }
        });linearLayout8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, Eight.class);
                startActivity(intent);
            }
        });linearLayout9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, Nine.class);
                startActivity(intent);
            }
        });
        twicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String wpurl="https://twitter.com/i/flow/login?input_flow_data=%7B%22requested_variant%22%3A%22eyJsYW5nIjoiZW4ifQ%3D%3D%22%7D";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wpurl));
                startActivity(intent);
            }
        });
        fbicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String wpurl="https://www.facebook.com/";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wpurl));
                startActivity(intent);

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wpurl="https://wa.me/+923078634655?";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wpurl));
                startActivity(intent);
            }
        });
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selectlanguage= adapterView.getItemAtPosition(position).toString();
                if (selectlanguage.equals("English")) {
                    setLocal(MainActivity.this,"en");
                    recreate();
                } else if (selectlanguage.equals("اردو")) {
                    setLocal(MainActivity.this,"ur");
                    recreate();
                }if (selectlanguage.equals("سندھی")) {
                    setLocal(MainActivity.this,"sd");
                    recreate();
                } else if (selectlanguage.equals("پنجابی")) {
                    setLocal(MainActivity.this,"pa");
                    recreate();
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
        setLocal(MainActivity.this,lang);
    }
}