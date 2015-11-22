package com.mahmoud.mahmoudapp.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.mahmoud.mahmoudapp.Entity.UserResponse;
import com.mahmoud.mahmoudapp.Interface.GetUserApi;
import com.mahmoud.mahmoudapp.R;
import com.mahmoud.mahmoudapp.StaticField;
import com.mahmoud.mahmoudapp.Tools;
import com.mahmoud.mahmoudapp.View.Activity.MainActivity;
import com.mahmoud.mahmoudapp.View.dialog.LoginDialog;
import com.mahmoud.mahmoudapp.View.dialog.RegisterDialog;
import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private Tools tools;
    private LinearLayout notLogin;
    private ScrollView user;
    private TextView email;
    private TextView name;
    private TextView cell;
    private ImageView avatar;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.profile, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getStringArray(R.array.drawer_titles)[0]);

        notLogin = (LinearLayout) view.findViewById(R.id.not_login);
        user = (ScrollView) view.findViewById(R.id.user);
        Button login = (Button) view.findViewById(R.id.login);
        Button signUp = (Button) view.findViewById(R.id.sign_up);
        email = (TextView) view.findViewById(R.id.email);
        name = (TextView) view.findViewById(R.id.name);
        avatar = (ImageView) view.findViewById(R.id.avatar);
        TextView logout = (TextView) view.findViewById(R.id.log_out);
        cell = (TextView) view.findViewById(R.id.cell);

        login.setOnClickListener(this);
        signUp.setOnClickListener(this);
        logout.setOnClickListener(this);

        if (Prefs.getBoolean(StaticField.PrefActiveUser, false)) {
            showData();

            } else {
                notLogin.setVisibility(View.VISIBLE);
                user.setVisibility(View.GONE);
            }

            return view;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login:
                    new LoginDialog(getActivity(), this).show();
                    break;

                case R.id.sign_up:
                    new RegisterDialog(getActivity()).show();
                    break;

                case R.id.log_out:
                    notLogin.setVisibility(View.VISIBLE);
                    user.setVisibility(View.GONE);

                    Prefs.putString(StaticField.PrefToken, "");
                    Prefs.putInt(StaticField.PrefUserID, -1);
                    Prefs.putString(StaticField.PrefUserEmail, "Login");
                    Prefs.putBoolean(StaticField.PrefActiveUser, false);
                    Prefs.putString(StaticField.PrefCell, "");
                    Prefs.putString(StaticField.PrefLastName, "");

                    ((MainActivity)getActivity()).loadUser();
                    break;
            }
        }

        public void getUserData() {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(StaticField.DomainURL)
                    .build();

            GetUserApi getUserApi = restAdapter.create(GetUserApi.class);
            getUserApi.getUser(
                    Prefs.getString(StaticField.PrefAcceptLanguage, StaticField.AcceptLanguageDefault),
                    StaticField.Token + Prefs.getString(StaticField.PrefToken, StaticField.EmptyString),
                    new Callback<UserResponse>() {
                        @Override
                        public void success(UserResponse userResponse, Response response) {
                            Prefs.putString(StaticField.PrefLastName, userResponse.getLast_name());
                            Prefs.putString(StaticField.PrefCell, userResponse.getCell());
                            Prefs.putInt(StaticField.PrefUserID, userResponse.getId());
                            Prefs.putString(StaticField.PrefUserEmail, userResponse.getEmail());
                            showData();
                            ((MainActivity)getActivity()).loadUser();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            if (error.getKind() == RetrofitError.Kind.HTTP) {
                                if (error.getResponse().getStatus() == 403) {
                                    Prefs.putString(StaticField.PrefToken, "-");
                                    Prefs.putBoolean(StaticField.PrefActiveUser, false);
                                    Prefs.putString(StaticField.PrefCell, "-");
                                    Prefs.putInt(StaticField.PrefUserID, -1);
                                    Prefs.putString(StaticField.PrefUserEmail, "-");
                                    Prefs.putString(StaticField.PrefLastName, "-");
                                    Toast.makeText(getActivity(), "مشکل در شناسایی",
                                            Toast.LENGTH_LONG).show();
                                    ((MainActivity)getActivity()).loadUser();

                                } else {
                                    Toast.makeText(getActivity(), "خطا",
                                            Toast.LENGTH_LONG).show();
                                    showData();

                                }

                            } else {
                                Toast.makeText(getActivity(), getString(R.string.error_connection),
                                        Toast.LENGTH_LONG).show();
                                showData();

                            }

                        }
                    }
            );
        }

        public void showData() {
            notLogin.setVisibility(View.GONE);
            user.setVisibility(View.VISIBLE);
            email.setText(Prefs.getString(StaticField.PrefUserEmail, "-"));
            cell.setText(Prefs.getString(StaticField.PrefCell, "-"));
            name.setText(Prefs.getString(StaticField.PrefLastName, "-"));

            Picasso.with(getContext())
                    .load("noimage")
                    .placeholder(
                            new IconDrawable(
                                    getContext(),
                                    Iconify.IconValue.zmdi_account))
                    .into(avatar);
        }

        @Override
        public void onResume() {
            super.onResume();
            if (Prefs.getBoolean(StaticField.PrefActiveUser, false)) {
                showData();
            } else {
                notLogin.setVisibility(View.VISIBLE);
                user.setVisibility(View.GONE);
            }
            ((MainActivity)getActivity()).loadUser();
        }

}