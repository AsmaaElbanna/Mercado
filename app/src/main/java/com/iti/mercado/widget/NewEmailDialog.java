package com.iti.mercado.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputLayout;
import com.iti.mercado.R;

import org.jetbrains.annotations.NotNull;

public class NewEmailDialog extends AppCompatDialogFragment {

    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    private NewEmailDialogListener listener;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_new_email, null);
        builder.setView(view)
                //              DialogInterface dialog, int which
                .setNegativeButton("cancel", (dialog, which) -> {

                })
                .setPositiveButton("Save", (dialog, which) -> {
                    String email = emailInputLayout.getEditText().getText().toString();
                    String password = passwordInputLayout.getEditText().getText().toString();
                    listener.changeEmail(email, password);
                });

        emailInputLayout = view.findViewById(R.id.email_EditText);
        passwordInputLayout = view.findViewById(R.id.password_EditText);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (NewEmailDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement NewEmailDialogListener");
        }
    }

    public interface NewEmailDialogListener {
        void changeEmail(String email, String password);
    }

    /**
     How to use
     copy this class and create this method

     private void openNewEmailDialog() {
     NewEmailDialog dialog = new NewEmailDialog();
     dialog.show(getSupportFragmentManager(), "NewEmailDialog");
     }

     then implements the interface listener -> NewEmailDialogListener ,
     and the args her will be in the implemented method

     **/
}
