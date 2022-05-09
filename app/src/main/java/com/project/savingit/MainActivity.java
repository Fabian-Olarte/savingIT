package com.project.savingit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.security.Principal;

public class MainActivity extends AppCompatActivity {

    PrincipalFragment principalFragment = new PrincipalFragment();
    ComprasFragment comprasFragment = new ComprasFragment();
    ConsejosFragment consejosFragment = new ConsejosFragment();
    PerfilFragment perfilFragment = new PerfilFragment();

    BottomNavigationView navigationItemView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(principalFragment);
        navigationItemView = findViewById(R.id.navigationBar);

        navigationItemView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.principal:
                        loadFragment(principalFragment);
                        return true;
                    case R.id.compras:
                        loadFragment(comprasFragment);
                        return true;
                    case R.id.consejos:
                        loadFragment(consejosFragment);
                        return true;
                    case R.id.perfil:
                        loadFragment(perfilFragment);
                        return true;
                }
                return false;
            }
        });

    }

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
    }
}