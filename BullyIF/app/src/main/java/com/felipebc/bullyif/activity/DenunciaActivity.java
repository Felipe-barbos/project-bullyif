package com.felipebc.bullyif.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import com.felipebc.bullyif.R;
import com.felipebc.bullyif.config.ConfiguracaoFirebase;
import com.felipebc.bullyif.model.Denuncia;
import com.felipebc.bullyif.model.Usuario;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DenunciaActivity extends AppCompatActivity {

    private TextView textoVitima, textoAgressor, textoLocalOcorrido,
            textoTipoAgressao, textoDataOcorrido, textoHorarioOcorrido, textoDescricao;
    private ValueEventListener valueEventListenerDenuncias;
    Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);

        //configurando toolbar na activity
        Toolbar toolbar = findViewById(R.id.toolbarDenuncia);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        //exibir botão voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //configurações iniciais
        textoVitima = findViewById(R.id.textViewIdVitima);
        textoAgressor = findViewById(R.id.textViewIdAgressor);
        textoLocalOcorrido = findViewById(R.id.textViewLocalOcorrido);
        textoTipoAgressao = findViewById(R.id.textViewTipoAgressao);
        textoDataOcorrido = findViewById(R.id.textViewDataOcorrido);
        textoHorarioOcorrido = findViewById(R.id.textViewHorarioOcorrido);
        textoDescricao = findViewById(R.id.textViewDescricao);

        exibirDenuncia();


    }

    //metodo permite recuperar  os dados da denuncia efetuada pelo do usuário

    public void exibirDenuncia() {

        bundle = getIntent().getExtras();
        String id = bundle.getString("IDenuncia");
        DatabaseReference denunciaRef;

        denunciaRef = ConfiguracaoFirebase.getFirebaseDatabase().child("denuncias");
        DatabaseReference denunciaPesquisa = denunciaRef.child(id);

        //recuperando as denuncias no banco de dados

        denunciaPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Denuncia dadosDenuncia = snapshot.getValue(Denuncia.class);

                textoVitima.setText(dadosDenuncia.getIdentificacaoVitima());
                textoAgressor.setText(dadosDenuncia.getIdentificacaoAgressor());
                textoLocalOcorrido.setText(dadosDenuncia.getLocalOcorrido());
                textoTipoAgressao.setText(dadosDenuncia.getTipoAgressao());
                textoDataOcorrido.setText(dadosDenuncia.getDataOcorrido());
                textoHorarioOcorrido.setText(dadosDenuncia.getHorarioOcorrido());
                textoDescricao.setText(dadosDenuncia.getDescricao());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}