package com.felipebc.bullyif.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.felipebc.bullyif.R;
import com.felipebc.bullyif.config.ConfiguracaoFirebase;
import com.felipebc.bullyif.model.Denuncia;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Analise extends AppCompatActivity {
    private TextView textoVitima2, textoAgressor2, textoLocalOcorrido2,
            textoTipoAgressao2, textoDataOcorrido2, textoHorarioOcorrido2, textoDescricao2;

    private FloatingActionButton floatingActionButton;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    Bundle bundle;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analise);


        //configurando toolbar na activity
        Toolbar toolbar = findViewById(R.id.toolbarAnalise);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        //exibir botão voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //configurações iniciais
        textoVitima2 = findViewById(R.id.textViewIdVitima2);
        textoAgressor2 = findViewById(R.id.textViewIdAgressor2);
        textoLocalOcorrido2 = findViewById(R.id.textViewLocalOcorrido2);
        textoTipoAgressao2 = findViewById(R.id.textViewTipoAgressao2);
        textoDataOcorrido2 = findViewById(R.id.textViewDataOcorrido2);
        textoHorarioOcorrido2 = findViewById(R.id.textViewHorarioOcorrido2);
        textoDescricao2 = findViewById(R.id.textViewDescricao2);
        floatingActionButton = findViewById(R.id.alterarStatus);


        radioGroup = findViewById(R.id.radiogroup);





        exibirDenuncia2();



    }

    //metodo permite recuperar  os dados da denuncia efetuada pelo do usuário

    public void exibirDenuncia2() {


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

                textoVitima2.setText(dadosDenuncia.getIdentificacaoVitima());
                textoAgressor2.setText(dadosDenuncia.getIdentificacaoAgressor());
                textoLocalOcorrido2.setText(dadosDenuncia.getLocalOcorrido());
                textoTipoAgressao2.setText(dadosDenuncia.getTipoAgressao());
                textoDataOcorrido2.setText(dadosDenuncia.getDataOcorrido());
                textoHorarioOcorrido2.setText(dadosDenuncia.getHorarioOcorrido());
                textoDescricao2.setText(dadosDenuncia.getDescricao());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    public void atualizarStatusDenuncia(View view){
         Denuncia atualizarDados = new Denuncia();


        bundle = getIntent().getExtras();
        String id = bundle.getString("IDenuncia");
        DatabaseReference denunciaRef;

        denunciaRef = ConfiguracaoFirebase.getFirebaseDatabase().child("denuncias");
        DatabaseReference denunciaPesquisa = denunciaRef.child(id);

        //recuperando as denuncias no banco de dados
        denunciaPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Denuncia dadosDenuncia2 = snapshot.getValue(Denuncia.class);

                atualizarDados.setIdentificacaoUsuario(dadosDenuncia2.getIdentificacaoUsuario());
                atualizarDados.setIdDenuncia(dadosDenuncia2.getIdDenuncia());
                atualizarDados.setIdentificacaoVitima(dadosDenuncia2.getIdentificacaoVitima());
                atualizarDados.setIdentificacaoAgressor(dadosDenuncia2.getIdentificacaoAgressor());
                atualizarDados.setLocalOcorrido(dadosDenuncia2.getLocalOcorrido());
                atualizarDados.setDataOcorrido(dadosDenuncia2.getDataOcorrido());
                atualizarDados.setHorarioOcorrido(dadosDenuncia2.getHorarioOcorrido());
                atualizarDados.setTipoAgressao(dadosDenuncia2.getTipoAgressao());
                atualizarDados.setDescricao(dadosDenuncia2.getDescricao());
                atualizarDados.setUsuarioExibicao(dadosDenuncia2.getUsuarioExibicao());

                //aplicando o feedback do assistente estudantil através dos radiobutton
                int radioid = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioid);
                atualizarDados.setStatusDenuncia(radioButton.getText().toString());
                denunciaPesquisa.setValue(atualizarDados);






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}