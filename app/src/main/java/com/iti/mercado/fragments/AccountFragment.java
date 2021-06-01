package com.iti.mercado.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iti.mercado.R;
import com.iti.mercado.activity.LoginActivity;
import com.iti.mercado.model.AppUser;
import com.iti.mercado.utilities.Constants;
import com.iti.mercado.utilities.UserFirebase;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private TextView usernameTextView;
    private TextView emailTextView;
    private CircleImageView profilePictureCircleImageView;
    private AppUser appUser;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        //getInfoFromUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameTextView = view.findViewById(R.id.profile_name);
        profilePictureCircleImageView = view.findViewById(R.id.profile_image);
        emailTextView = view.findViewById(R.id.profile_email);
        Button logoutButton = view.findViewById(R.id.logout_button);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());

        //Log.i("databaseReference", "onViewCreated: databaseReference = " + databaseReference);
        appUser = new AppUser();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Wait few seconds...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                appUser = snapshot.getValue(AppUser.class);
                if (appUser == null) {
                    Log.i("if", "onDataChange: not found");
                } else {
                    Log.i("if", "onDataChange:  found");
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });

        new Handler().postDelayed(() -> {
            progressDialog.dismiss();
            getInfoFromUser();

            logoutButton.setOnClickListener(v -> {

                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finishAffinity();
            });

            profilePictureCircleImageView.setOnClickListener(v -> {
                getProfilePicture();
            });

        }, Constants.TIME_SPLASH);
    }

    private void getInfoFromUser() {

        if (currentUser != null) {

            Log.i("if", "getInfoFromUser: 1= " + currentUser.getPhotoUrl());

            if (currentUser.getPhotoUrl() == null) { // the currentUser is lodged by email and password
                /*databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        //usernameTextView.setText(snapshot.getValue(AppUser.class).getUsername());

                        appUser = snapshot.getValue(AppUser.class);

                        if (appUser != null) {
                            usernameTextView.setText(appUser.getUsername());
                            //profilePictureCircleImageView.setImageURI(Uri.parse(appUser.getProfilePicture()));
                            Glide.with(getContext())
                                    .load(Uri.parse(appUser.getProfilePicture()))
                                    //.apply(new RequestOptions().override(100,100))
                                    .placeholder(R.drawable.ic_launcher_background)
                                    .error(R.drawable.ic_baseline_account_circle_24)
                                    .into(profilePictureCircleImageView);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });*/

                if (appUser != null) {
                    usernameTextView.setText(appUser.getUsername());
                    //profilePictureCircleImageView.setImageURI(Uri.parse(appUser.getProfilePicture()));
                    Glide.with(getContext())
                            .load(Uri.parse(appUser.getProfilePicture()))
                            //.apply(new RequestOptions().override(100,100))
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_baseline_account_circle_24)
                            .into(profilePictureCircleImageView);
                }

            } else { // the currentUser is lodged by google account

                Log.i("if", "getInfoFromUser: 4= " + appUser);

                if (appUser == null) { // == null user don't have saved profilePicture

                    Log.i("if", "getInfoFromUser: true == null 'not exist'");
                    appUser = new AppUser();
                    appUser.setUsername(currentUser.getDisplayName());
                    usernameTextView.setText(appUser.getUsername());
                    appUser.setProfilePicture(currentUser.getPhotoUrl().toString());

                    if (appUser.getProfilePicture() != null) {
                        //profilePictureCircleImageView.setImageURI(appUser.getProfilePicture());
                        Log.i("if", "getInfoFromUser: 5=" + appUser.getProfilePicture());
                        Glide.with(getContext())
                                .load(Uri.parse(appUser.getProfilePicture()))
                                //.apply(new RequestOptions().override(100,100))
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_baseline_account_circle_24)
                                .into(profilePictureCircleImageView);
                    }

                } else { //  != null user have saved profilePicture

                    Log.i("if", "getInfoFromUser: false != null 'exist'");

                    if (appUser != null) {
                        usernameTextView.setText(appUser.getUsername());
                        //profilePictureCircleImageView.setImageURI(Uri.parse(appUser.getProfilePicture()));
                        Glide.with(getContext())
                                .load(Uri.parse(appUser.getProfilePicture()))
                                //.apply(new RequestOptions().override(100,100))
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_baseline_account_circle_24)
                                .into(profilePictureCircleImageView);
                    }
                }

            }

            appUser.setUserEmail(currentUser.getEmail());
            emailTextView.setText(appUser.getUserEmail());
        }

    }

    private void addImageToFirebase(@Nullable @org.jetbrains.annotations.Nullable Intent data) {
        progressDialog.show();

        appUser.setProfilePicture(data.getData().toString());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("users").child(currentUser.getUid());
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpg")
                .build();

        UploadTask uploadTask = storageReference.putFile(Uri.parse(appUser.getProfilePicture()), metadata);

        uploadTask.addOnSuccessListener(taskSnapshot -> {

            Log.i("profilePicture", "onSuccess: successful ");

            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                Log.i("profilePicture", "onActivityResult: - return");
                return storageReference.getDownloadUrl();

            }).addOnCompleteListener(task -> {

                Log.i("profilePicture", "onActivityResult: + return");

                if (task.isSuccessful()) {

                    Uri downloadUri = task.getResult();
                    Log.i("profilePicture", "onActivityResult: uri = " + downloadUri);

                    appUser.setProfilePicture(downloadUri.toString());
                    databaseReference.setValue(appUser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.i("profilePicture", "onSuccess: add Success");
                                }
                            });

                    Log.i("profilePicture", "onActivityResult: done ");
                    progressDialog.dismiss();

                } else {
                    // Handle failures
                    // ...
                }
            });
        });

        uploadTask.addOnFailureListener(e -> {
            Log.i("profilePicture", "onFailure: unsuccessful ");
        });

        Log.i("profilePicture", "onActivityResult: " + appUser.getProfilePicture());

        Glide.with(getContext())
                .load(appUser.getProfilePicture())
                //.apply(new RequestOptions().override(100,100))
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(profilePictureCircleImageView);
        //profilePictureCircleImageView.setImageURI(imageUri);
    }

    private void getProfilePicture() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, Constants.PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == Constants.PICK_IMAGE) {

            addImageToFirebase(data);
        }
    }
}