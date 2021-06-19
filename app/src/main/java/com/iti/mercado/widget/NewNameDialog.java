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

public class NewNameDialog extends AppCompatDialogFragment {

    private TextInputLayout nameInputLayout;

    private NewNameDialogListener listener;

    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_new_name, null);
        builder.setView(view)
                //              DialogInterface dialog, int which
                .setNegativeButton("cancel", (dialog, which) -> {

                })
                .setPositiveButton("Save", (dialog, which) -> {
                    String name = nameInputLayout.getEditText().getText().toString();

                    listener.changeName(name);
                });

        nameInputLayout = view.findViewById(R.id.user_name_EditText);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (NewNameDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement NewNameDialogListener");
        }
    }

    public interface NewNameDialogListener {
        void changeName(String name);
    }


}
