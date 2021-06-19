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

public class NewPasswordDialog extends AppCompatDialogFragment {

    private TextInputLayout oldPasswordInputLayout;
    private TextInputLayout newPasswordInputLayout;
    private NewPasswordDialogListener listener;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_new_password, null);
        builder.setView(view)
                //              DialogInterface dialog, int which
                .setNegativeButton("cancel", (dialog, which) -> {

                })
                .setPositiveButton("Save", (dialog, which) -> {
                    String oldPassword = oldPasswordInputLayout.getEditText().getText().toString();
                    String newPassword = newPasswordInputLayout.getEditText().getText().toString();
                    listener.changePassword(oldPassword, newPassword);
                });

        oldPasswordInputLayout = view.findViewById(R.id.old_password_EditText);
        newPasswordInputLayout = view.findViewById(R.id.new_password_EditText);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (NewPasswordDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement NewPasswordDialogListener");
        }
    }

    public interface NewPasswordDialogListener {
        void changePassword(String oldPassword, String newPassword);
    }
}
