package com.example.smartandhra;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartandhra.databinding.ActivityYourproblemBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class Yourproblem extends AppCompatActivity {
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ActivityYourproblemBinding binding;
    Uri uri;
    TextView tv;
    FusedLocationProviderClient locationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* setContentView(R.layout.activity_main);*/
        binding = DataBindingUtil.setContentView(this,R.layout.activity_yourproblem);
        databaseReference = FirebaseDatabase.getInstance().getReference("UserData");
        storageReference = FirebaseStorage.getInstance().getReference()
                .child("Images/"+ UUID.randomUUID().toString());
        tv = findViewById(R.id.textView);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        ActivityResultLauncher<String> mgetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                uri = result;
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    binding.iv.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        binding.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mgetContent.launch("image/*");
            }
        });
    }
    public void getDeviceLocationDetail(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location=task.getResult();
                Geocoder geocoder=new Geocoder(Yourproblem.this, Locale.getDefault());
                try {
                    List<Address> addresses=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    String locality=String.valueOf(addresses.get(0).getLocality());
                    String address=String.valueOf(addresses.get(0).getAddressLine(0));
                    String postalCode=String.valueOf(addresses.get(0).getPostalCode());
                    tv.setText("Locality :"+locality+"\n"+"Address :"+address+"\n"+"Postal Code :"+postalCode);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void register(View view) {
        storageReference.putFile(uri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String url = uri.toString();
                        String name = binding.name.getText().toString();
                        String problem = binding.problem.getText().toString();
                        String num = binding.number.getText().toString();
                        String textview = binding.textView.getText().toString();
                        MyModel myModel = new MyModel(url,name,problem,num,textview);

                        /*  String id = databaseReference.push().getKey();*/
                        databaseReference.child(num).setValue(myModel);
                        Toast.makeText(Yourproblem.this, "Data Inserted",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Yourproblem.this,SuccessfulRegistration.class));
                        finish();

                    }
                });
            }
        });
    }

}