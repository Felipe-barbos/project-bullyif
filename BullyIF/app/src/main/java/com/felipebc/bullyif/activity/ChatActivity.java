package com.felipebc.bullyif.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.felipebc.bullyif.R;
import com.felipebc.bullyif.fragment.ContatosFragment;
import com.felipebc.bullyif.fragment.ConversasFragment;
import com.felipebc.bullyif.fragment.DenunciasFragment;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.Locale;

public class ChatActivity extends AppCompatActivity {

    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        //configurando toolbar na activity
        Toolbar toolbar = findViewById(R.id.toolbarChat);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        //exibir botão voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //configurando abbas
        FragmentPagerItemAdapter adapter =  new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                .add("Conversas", ConversasFragment.class)
                .add("Contatos", ContatosFragment.class)
                .create()

        );
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = findViewById(R.id.viewPagerTab);
        viewPagerTab.setViewPager(viewPager);

        //configuações inicial do search view
        searchView = findViewById(R.id.materialSearchPrincipal);

        //listener para o search view
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                ConversasFragment fragment = (ConversasFragment) adapter.getPage(0);
                fragment.recarregarConversa();
            }
        });

        //listener para caixa de texto
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ConversasFragment fragment = (ConversasFragment) adapter.getPage(0);
                if(newText !=null && !newText.isEmpty()){
                    fragment.pesquisarConversas(newText.toLowerCase());
                }
                return true;
            }
        });
    }

    //criar os menus na toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_secundario, menu);

        //configrar botao de pesquisa
        MenuItem item = menu.findItem(R.id.menuPesquisaChat);
        searchView.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);

    }
}