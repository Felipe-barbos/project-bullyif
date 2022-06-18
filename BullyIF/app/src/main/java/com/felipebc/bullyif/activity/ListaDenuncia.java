package com.felipebc.bullyif.activity;

import android.content.Intent;
import android.os.Bundle;

import com.felipebc.bullyif.R;
import com.felipebc.bullyif.fragment.DenunciasFragment;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import com.felipebc.bullyif.databinding.ActivityListaDenunciaBinding;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class ListaDenuncia extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityListaDenunciaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListaDenunciaBinding.inflate(getLayoutInflater());
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
                        .add("", DenunciasFragment.class)
                        .create()

        );
        ViewPager viewPager = findViewById(R.id.viewPagerDenuncia);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = findViewById(R.id.viewPagerTabDenuncia);
        viewPagerTab.setViewPager(viewPager);
    }


  public void abrirTelaCampoDenuncia(View view){
        Intent intent = new Intent(ListaDenuncia.this, CampoDenunciaActivity.class);
        startActivity(intent);
  }
}
