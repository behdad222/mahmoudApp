package com.mahmoud.mahmoudapp.View.Activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.mahmoud.mahmoudapp.Adapter.DrawerAdapter;
import com.mahmoud.mahmoudapp.R;
import com.mahmoud.mahmoudapp.View.Fragment.AboutFragment;
import com.mahmoud.mahmoudapp.View.Fragment.AsarFragment;
import com.mahmoud.mahmoudapp.View.Fragment.BiographyFragment;
import com.mahmoud.mahmoudapp.View.Fragment.ErtebatBaMaFragment;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final int WORKS = 0;
    public static final int BIOGRAPHY = 1;
    public static final int CONTACT_US = 2;
    public static final int ABOUT = 3;
    public static final int EXIT = 4;
    public int menuPosition = 0;

    private DrawerLayout drawerLayout;
    private View appMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale locale = new Locale("fa");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView navigation = (RecyclerView) findViewById(R.id.navigation_view);
        navigation.setHasFixedSize(true);
        DrawerAdapter adapter = new DrawerAdapter(this);
        navigation.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        navigation.setLayoutManager(layoutManager);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        appMainView = findViewById(R.id.app_main_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar,R.string.open, R.string.close){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                appMainView.setTranslationX(slideOffset * drawerView.getWidth() * -1);
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, new AsarFragment())
                .commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Checking for the "menu" key
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers();
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void selectItem(int position) {
        switch (position){

            case WORKS:
                if (menuPosition != WORKS) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, new AsarFragment())
                        .commit();
                    menuPosition = WORKS;
                }

                break;

            case BIOGRAPHY:
                if (menuPosition != BIOGRAPHY) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame, new BiographyFragment())
                            .commit();
                    menuPosition = BIOGRAPHY;
                }

                break;

            case CONTACT_US:
                if (menuPosition != CONTACT_US) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame, new ErtebatBaMaFragment())
                            .commit();
                    menuPosition = CONTACT_US;
                }

                break;

            case ABOUT:
                if (menuPosition != ABOUT) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame, new AboutFragment())
                            .commit();
                    menuPosition = ABOUT;
                }

                break;

            case EXIT:
                finish();
                break;

            default:
                break;
        }

        drawerLayout.closeDrawers();
    }
}