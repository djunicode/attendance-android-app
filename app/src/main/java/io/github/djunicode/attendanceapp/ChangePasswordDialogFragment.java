package io.github.djunicode.attendanceapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

public class ChangePasswordDialogFragment extends DialogFragment implements View.OnClickListener{

    private TextInputEditText oldPass, newPass, repeatPass;
    ImageButton saveChanges;

    public interface OnPasswordChange{
        void onPasswordChange(String oldPassword, String newPassword);
    }

    private OnPasswordChange mOnPasswordChange;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_password_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oldPass = view.findViewById(R.id.old_pass_input);
        newPass = view.findViewById(R.id.new_pass_input);
        repeatPass = view.findViewById(R.id.repeat_pass_input);

        saveChanges = view.findViewById(R.id.save_changes);

    }

    @Override
    public void onClick(View v) {
        String oldPassword = oldPass.getText().toString();
        String newPassword = newPass.getText().toString();
        String repeatPassword = repeatPass.getText().toString();

        if (newPassword.equals(repeatPassword)){
            mOnPasswordChange.onPasswordChange(oldPassword, repeatPassword);
        } else {
            Toast.makeText(getContext(), "Passwords Do Not Match", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnPasswordChange = ((OnPasswordChange)context);
        } catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
