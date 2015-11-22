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

import com.mahmoud.mahmoudapp.Entity.LoginEntity;
import com.mahmoud.mahmoudapp.Entity.LoginResponse;
import com.mahmoud.mahmoudapp.Interface.LoginApi;
import com.mahmoud.mahmoudapp.R;
import com.mahmoud.mahmoudapp.StaticField;
import com.mahmoud.mahmoudapp.View.Fragment.ProfileFragment;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginDialog extends Dialog implements View.OnClickListener {
    private Button login;
    private EditText email;
    private EditText password;
    private ProgressBar progressBar;
    private Context context;
    private ProfileFragment profileFragment;

    public LoginDialog(Context context, ProfileFragment profileFragment) {
        super(context);
        this.context = context;
        this.profileFragment = profileFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        setContentView(R.layout.dialog_login);
        login = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.loading);

        login.setOnClickListener(this);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);

    }

    @Override
    public void onClick(View v) {
            login.setEnabled(false);
            email.setEnabled(false);
            password.setEnabled(false);

            progressBar.setVisibility(View.VISIBLE);

            String pass = password.getText().toString();
            String mail = email.getText().toString();

            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(StaticField.DomainURL)
                    .build();

            LoginApi loginApi = restAdapter.create(LoginApi.class);
            loginApi.login(
                    Prefs.getString(StaticField.PrefAcceptLanguage, StaticField.AcceptLanguageDefault),
                    new LoginEntity(mail, pass),
                    new Callback<LoginResponse>() {

                        @Override
                        public void success(LoginResponse loginResponse, Response response) {
                            if (response.getStatus() == 200) {
                                progressBar.setVisibility(View.GONE);
                                Prefs.putString(StaticField.PrefToken, loginResponse.getAuth_token());
                                Prefs.putBoolean(StaticField.PrefActiveUser, true);
                                profileFragment.getUserData();

                                dismiss();
                            } else {
                                login.setEnabled(true);
                                email.setEnabled(true);
                                password.setEnabled(true);

                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(context, "خطا",
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            login.setEnabled(true);
                            email.setEnabled(true);
                            password.setEnabled(true);

                            progressBar.setVisibility(View.GONE);

                            if (error.getKind() == RetrofitError.Kind.HTTP) {
                                if (error.getResponse().getStatus() == 403) {
                                    Toast.makeText(context, "خزا در شناسایی" ,
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(context, "خطا",
                                            Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(context, context.getString(R.string.error_connection),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

}
