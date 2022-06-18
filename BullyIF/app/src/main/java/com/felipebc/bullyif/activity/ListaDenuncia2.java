package com.felipebc.bullyif.activity;

import android.content.Intent;
import android.os.Bundle;

import com.felipebc.bullyif.R;
import com.felipebc.bullyif.activity.CampoDenunciaActivity;
import com.felipebc.bullyif.activity.ListaDenuncia;
import com.felipebc.bullyif.fragment.DenunciasFragment;
import com.felipebc.bullyif.fragment.DenunciasFragment2;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.felipebc.bullyif.databinding.ActivityListaDenuncia2Binding;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class ListaDenuncia2 extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityListaDenuncia2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListaDenuncia2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //instaciando a toolbar na activity
        Toolbar toolbar = findViewById(R.id.toolbarDenuncia);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //configurando abbas
        FragmentPagerItemAdapter adapter =  new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("", DenunciasFragment2.class)
                        .create()

        );
        ViewPager viewPager = findViewById(R.id.viewPagerDenuncia2);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = findViewById(R.id.viewPagerTabDenuncia2);
        viewPagerTab.setViewPager(viewPager);

    }

    public void atualizarLista(View view){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }




}