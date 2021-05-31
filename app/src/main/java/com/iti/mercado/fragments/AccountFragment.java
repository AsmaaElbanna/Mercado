package com.iti.mercado.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.ValueEventListener;
import com.iti.mercado.R;
import com.iti.mercado.activity.LoginActivity;
import com.iti.mercado.model.AppUser;
import com.iti.mercado.utilities.UserFirebase;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountFragment extends Fragment {

    private TextView usernameTextView;
    private TextView emailTextView;
    private CircleImageView profilePictureCircleImageView;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        getInfoFromUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameTextView = view.findViewById(R.id.profile_name);
        profilePictureCircleImageView = view.findViewById(R.id.profile_image);
        emailTextView = view.findViewById(R.id.profile_email);
        Button logoutButton = view.findViewById(R.id.logout_button);

        //getInfoFromUser();

        logoutButton.setOnClickListener(v -> {

            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finishAffinity();
        });

        profilePictureCircleImageView.setOnClickListener(v -> {
            getProfilePicture();
        });
    }

    private void getInfoFromUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {

            if (user.getPhotoUrl() != null) {

                String profilePicture = user.getPhotoUrl().toString();
                profilePicture += "?type=large";

                Log.i("profilePicture", "onStart: profilePicture URL : " + profilePicture);

                Glide.with(getContext()).load(profilePicture)
                        //.apply(new RequestOptions().override(100,100))
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(profilePictureCircleImageView);
            }
            if (user.getDisplayName() != null && !user.getDisplayName().equals("")) {
                Log.i("appUsername", "getInfoFromUser: ---------------:" + user.getDisplayName());
                usernameTextView.setText(user.getDisplayName());

            } else {
                DatabaseReference ref = UserFirebase.getFirebaseDatabase().getReference("users").child(user.getUid());
                Log.i("appUsername", "getInfoFromUser: ++++++++++++++ ");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        usernameTextView.setText(snapshot.getValue(AppUser.class).getUsername());
                        Log.i("appUsername : ", "onDataChange: " + snapshot.getValue(AppUser.class).getUsername());
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }

            emailTextView.setText(user.getEmail());
        }
    }

    private void getProfilePicture() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            profilePictureCircleImageView.setImageURI(imageUri);
        }
    }
}