package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.instagram.fragments.FavouriteFragment;
import com.example.instagram.fragments.HomeFragment;
import com.example.instagram.fragments.ProfileFragment;
import com.example.instagram.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mBottomNavigationView= findViewById(R.id.bottom_nav_bar);
        mBottomNavigationView.setOnNavigationItemSelectedListener(navListner);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragment,new HomeFragment())
                .commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListner=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment=null;

                    switch ((menuItem.getItemId())){
                        case R.id.action_home:
                            selectedFragment= new HomeFragment();
                            break;
                        case  R.id.action_post:
                            startActivity(new Intent(HomeActivity.this,PostActivity.class));
                            break;
                        case R.id.action_favourite:
                            selectedFragment= new FavouriteFragment();
                            break;
                        case R.id.action_profile:
                            selectedFragment= new ProfileFragment();
                            break;
                        case R.id.action_search:
                            selectedFragment= new SearchFragment();
                            break;
                    }
                    if(selectedFragment!=null){
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container_fragment,selectedFragment)
                                .addToBackStack(null)
                                .commit();

                    }
                    return false;
                }

            };

}
