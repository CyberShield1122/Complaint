package com.example.complaint;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FragmentDailogBox extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        String [] AddPicture= getActivity().getResources().getStringArray(R.array.AddPicture);


        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Picture");
        builder.setItems(AddPicture, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (AddPicture[i].matches("Take Picture")) {
                    Intent iCamera= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    getActivity().startActivityForResult(iCamera,100);


                } else if (AddPicture[i].matches("Choose From Gallery")) {
                    Intent iGallery= new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getActivity().startActivityForResult(iGallery,1000);
                }

            }
        });



        return builder.create();



    }
}
