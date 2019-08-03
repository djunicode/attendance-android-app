package io.github.djunicode.attendanceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import io.github.djunicode.attendanceapp.TeacherSide.Models.deleteResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class ChangePasswordDialogFragment extends DialogFragment {

    private TextInputEditText oldPass, newPass, repeatPass;
    ImageButton saveChanges;
    ProgressBar tickPbar;
    private SharedPreferences spref;
    SharedPreferences.Editor edit;

    public interface OnPasswordChange {
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
        tickPbar = view.findViewById(R.id.tickPbar);
        spref = getActivity().getSharedPreferences("user", MODE_PRIVATE);
        edit = spref.edit();
        saveChanges = view.findViewById(R.id.save_changes);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPass.onEditorAction(EditorInfo.IME_ACTION_DONE);
                saveChanges.setVisibility(View.INVISIBLE);
                tickPbar.setVisibility(View.VISIBLE);
                final String oldPassword = oldPass.getText().toString();
                String newPassword = newPass.getText().toString();
                String repeatPassword = repeatPass.getText().toString();

                if (newPassword.equals(repeatPassword)) {
                    changePassword changePassword = new changePassword(oldPassword, newPassword);
                    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://wizdem.pythonanywhere.com/Attendance/").addConverterFactory(GsonConverterFactory.create()).build();
                    RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);
                    Call<deleteResponse> pwdChange = retrofitInterface.changePassword("Token " + spref.getString("token", null), changePassword);
                    pwdChange.enqueue(new Callback<deleteResponse>() {
                        @Override
                        public void onResponse(Call<deleteResponse> call, Response<deleteResponse> response) {
                            deleteResponse deleteResponse = response.body();
                            if (deleteResponse == null) {
                                saveChanges.setVisibility(View.VISIBLE);
                                tickPbar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(), "Something went wrong. Please try again", Toast.LENGTH_LONG).show();

                            } else {
                                if (deleteResponse.getSuccess() == 1) {
                                    ChangePasswordDialogFragment.this.dismiss();
                                    edit.clear();
                                    edit.commit();
                                    Toast.makeText(getActivity(), "Password changed successfully. Please login again", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                    getActivity().finish();

                                } else {
                                    saveChanges.setVisibility(View.VISIBLE);
                                    tickPbar.setVisibility(View.INVISIBLE);
                                    oldPass.setText("");
                                    newPass.setText("");
                                    repeatPass.setText("");

                                    Toast.makeText(getContext(), "Old password did not match", Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<deleteResponse> call, Throwable t) {
                            Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
                            saveChanges.setVisibility(View.VISIBLE);
                            tickPbar.setVisibility(View.INVISIBLE);
                        }
                    });


                } else {
                    saveChanges.setVisibility(View.VISIBLE);
                    tickPbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Passwords Do Not Match", Toast.LENGTH_LONG).show();
                    oldPass.setText("");
                    newPass.setText("");
                    repeatPass.setText("");
                }
            }
        });

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnPasswordChange = ((OnPasswordChange) context);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
