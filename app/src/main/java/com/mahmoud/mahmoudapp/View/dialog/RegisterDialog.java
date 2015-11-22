package com.mahmoud.mahmoudapp.View.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mahmoud.mahmoudapp.Entity.RegisterEntity;
import com.mahmoud.mahmoudapp.Entity.RegisterResponse;
import com.mahmoud.mahmoudapp.Interface.RegisterApi;
import com.mahmoud.mahmoudapp.R;
import com.mahmoud.mahmoudapp.StaticField;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegisterDialog extends Dialog implements View.OnClickListener {
    private Button register;
    private EditText password;
    private EditText email;
    private EditText last_name;
    private EditText cell;
    private ProgressBar progressBar;
    private Context context;

    public RegisterDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        setContentView(R.layout.dialog_register);
        register = (Button) findViewById (R.id.register);
        password = (EditText) findViewById (R.id.password);
        email = (EditText) findViewById (R.id.email);
        last_name = (EditText) findViewById (R.id.last_name);
        cell = (EditText) findViewById (R.id.cell);
        progressBar = (ProgressBar) findViewById(R.id.loading);

        register.setOnClickListener(this);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
            register.setEnabled(false);
            password.setEnabled(false);
            last_name.setEnabled(false);
            cell.setEnabled(false);
            email.setEnabled(false);

            progressBar.setVisibility(View.VISIBLE);

            String pass = password.getText().toString();
            String mail = email.getText().toString();
            String last = last_name.getText().toString();
            String cel = cell.getText().toString();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(StaticField.DomainURL)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();

            RegisterApi registerApi = restAdapter.create(RegisterApi.class);
            registerApi.register(
                    new RegisterEntity(mail, pass, last, cel),
                    new Callback<RegisterResponse>() {

                        @Override
                        public void success(RegisterResponse registerResponse, Response response) {
                            if (response.getStatus() == 201) {
                                Toast.makeText(context,
                                        registerResponse.getEmail()
                                                + " "
                                                + "ثبت شد",
                                        Toast.LENGTH_LONG).show();

                                dismiss();
                            } else {
                                faild();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            faild();
                        }
                    });

    }

    private void faild() {
        register.setEnabled(true);
        password.setEnabled(true);
        last_name.setEnabled(true);
        cell.setEnabled(true);
        email.setEnabled(true);

        progressBar.setVisibility(View.GONE);
        Toast.makeText(context, "error",
                Toast.LENGTH_LONG).show();
    }
}
