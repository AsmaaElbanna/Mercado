package com.iti.mercado.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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
import com.iti.mercado.model.AppUser;
import com.iti.mercado.utilities.Constants;
import com.iti.mercado.utilities.UserFirebase;
import com.iti.mercado.widget.NewEmailDialog;
import com.iti.mercado.widget.NewNameDialog;
import com.iti.mercado.widget.NewPasswordDialog;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditAccountActivity extends AppCompatActivity implements NewPasswordDialog.NewPasswordDialogListener,
        NewEmailDialog.NewEmailDialogListener, NewNameDialog.NewNameDialogListener {
    private TextView usernameTextView;
    private TextView emailTextView;
    private ImageView editPasswordImageView;
    private ImageView editEmailImageView;
    private ImageView editUsernameImageView;
    private CircleImageView profilePictureCircleImageView;
    private Button saveButton;

    private AppUser appUser;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        // to  hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        profilePictureCircleImageView = findViewById(R.id.profile_image);
        usernameTextView = findViewById(R.id.user_name_TextView);
        emailTextView = findViewById(R.id.email_TextView);
        saveButton = findViewById(R.id.save_button);

        editUsernameImageView = findViewById(R.id.edit_user_name);
        editEmailImageView = findViewById(R.id.edit_email);
        editPasswordImageView = findViewById(R.id.edit_password_TextView);


        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());

        appUser = new AppUser();


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait few seconds...");
        progressDialog.setCancelable(false);
        //progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                appUser = snapshot.getValue(AppUser.class);
                if (appUser == null) {
                    Log.i("if", "onDataChange: not found");
                } else {
                    Log.i("if", "onDataChange:  found");
                    //progressDialog.dismiss();
                    getInfoFromUser();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }

        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        profilePictureCircleImageView.setOnClickListener(v -> {
            getProfilePicture();
        });

        editUsernameImageView.setOnClickListener(v -> {
            openNewNameDialog();
        });

        editPasswordImageView.setOnClickListener(v -> {
            openNewPasswordDialog();
        });
        editEmailImageView.setOnClickListener(v -> {
            openNewEmailDialog();
        });
        saveButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void openNewPasswordDialog() {
        NewPasswordDialog dialog = new NewPasswordDialog();
        dialog.show(getSupportFragmentManager(), "NewPasswordDialog");
    }

    private void openNewEmailDialog() {
        NewEmailDialog dialog = new NewEmailDialog();
        dialog.show(getSupportFragmentManager(), "NewEmailDialog");
    }

    private void openNewNameDialog() {
        NewNameDialog dialog = new NewNameDialog();
        dialog.show(getSupportFragmentManager(), "NewNameDialog");
    }

    private void getInfoFromUser() {

        if (currentUser != null) {

            Log.i("if", "getInfoFromUser: 1= " + currentUser.getPhotoUrl());

            if (currentUser.getPhotoUrl() == null) { // the currentUser is lodged by email and password
//                if (currentUser.isEmailVerified()) {
//                    Toast.makeText(this, "isEmailVerified()  -> True", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "isEmailVerified()  -> False", Toast.LENGTH_SHORT).show();
//                }
                if (appUser != null) {
                    usernameTextView.setText(appUser.getUsername());
                    //profilePictureCircleImageView.setImageURI(Uri.parse(appUser.getProfilePicture()));

                    if (appUser.getProfilePicture() != null) {
                        Glide.with(getApplicationContext())
                                .load(Uri.parse(appUser.getProfilePicture()))
                                //.apply(new RequestOptions().override(100,100))
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_baseline_account_circle_24)
                                .into(profilePictureCircleImageView);
                    }

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

                        Glide.with(getApplicationContext())
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

                        Glide.with(getApplicationContext())
                                .load(Uri.parse(appUser.getProfilePicture()))
                                //.apply(new RequestOptions().override(100,100))
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_baseline_account_circle_24)
                                .into(profilePictureCircleImageView);

                    }
                }

            }
            if (appUser != null) {
                appUser.setUserEmail(currentUser.getEmail());
                emailTextView.setText(appUser.getUserEmail());
            }

        }

    }

    private void addImageToFirebase(@Nullable @org.jetbrains.annotations.Nullable Intent data) {
        progressDialog.show();
        if (data != null) {
            appUser.setProfilePicture(data.getData().toString());
        }


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
                    Log.e("TAG", "addImageToFirebase: Handle failures");

                }
            });
        });

        uploadTask.addOnFailureListener(e -> {
            Log.i("profilePicture", "onFailure: unsuccessful ");
        });

        Log.i("profilePicture", "onActivityResult: " + appUser.getProfilePicture());


        Glide.with(getApplicationContext())
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

    @Override // get the password and re-authenticate and change the password
    public void changePassword(String oldPassword, String newPassword) {

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), oldPassword);

        // Prompt the user to re-provide their sign-in credentials
        currentUser.reauthenticate(credential).addOnCompleteListener(task -> {
            Log.d("TAG", "User re-authenticated.");

            currentUser.updatePassword(newPassword).addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    Log.d("TAG", "User password updated.");
                }
            });
        });
    }

    @Override // get the email and password and re-authenticate and change user email
    public void changeEmail(String email, String password) {

        AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), password);

        currentUser.reauthenticate(credential).addOnCompleteListener(task -> {
            Log.d("TAG", "User re-authenticated.");

            currentUser.updateEmail(email).addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    Log.d("TAG", "User email updated.");
                    appUser.setUserEmail(email);
                    databaseReference.setValue(appUser).addOnCompleteListener(task2 -> {
                        emailTextView.setText(currentUser.getEmail());
                    });
                }
            });
        });
    }

    @Override
    public void changeName(String name) {
        appUser.setUsername(name);
        databaseReference.setValue(appUser).addOnCompleteListener(task -> {
            usernameTextView.setText(name);
        });

    }
}