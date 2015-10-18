package com.mahmoud.mahmoudapp;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.mahmoud.mahmoudapp.View.Fragment.AkhbarFragment;
import com.mahmoud.mahmoudapp.View.Fragment.AsarFragment;
import com.mahmoud.mahmoudapp.View.Fragment.BiographyFragment;
import com.mahmoud.mahmoudapp.View.Fragment.ErtebatBaMaFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open, R.string.close){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        Fragment fragment;

        fragment = new AsarFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, fragment)
                .commit();

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();

        Fragment fragment;

        switch (menuItem.getItemId()){

            case R.id.asar:
                fragment = new AsarFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, fragment)
                        .commit();
                return true;

            case R.id.bio:
                fragment = new BiographyFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, fragment)
                        .commit();
                return true;

            case R.id.ertebat:
                fragment = new ErtebatBaMaFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, fragment)
                        .commit();
                return true;


            case R.id.news:
                fragment = new AkhbarFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, fragment)
                        .commit();
                return true;

            default:
                return false;


        }
    }
}