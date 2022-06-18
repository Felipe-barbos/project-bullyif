package com.felipebc.bullyif.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.felipebc.bullyif.R;
import com.felipebc.bullyif.config.ConfiguracaoFirebase;
import com.felipebc.bullyif.helper.UsuarioFirebase;
import com.felipebc.bullyif.model.Denuncia;
import com.felipebc.bullyif.model.Usuario;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;

import java.util.Random;

public class CampoDenunciaActivity extends AppCompatActivity {
    private EditText idVitima, idAgressor, localOcorrido, horarioOcorrido,
            dataOcorrido, tipoAgressao, descricaoOcorrido;
    private DatabaseReference database;
    private DatabaseReference mensagensRef;
    private String usuarioAtual;
    private String idUsuarioRemetente;
    private Integer IdDenuncia;
    private TextView textViewNome;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campo_denuncia);

        //configurando toolbar na activity
        Toolbar toolbar = findViewById(R.id.toolbarCampoDenuncia);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        //exibir botão voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //configurações iniciais
         idVitima = findViewById(R.id.editTextIdVitima);
         idAgressor = findViewById(R.id.editTextIdAgressor);
         localOcorrido = findViewById(R.id.editTextLocalOcorrido);
         horarioOcorrido = findViewById(R.id.editTextHorarioOcorrido);
         dataOcorrido = findViewById(R.id.editTextDataOcorrido);
         tipoAgressao = findViewById(R.id.editTextTipoAgressao);
         descricaoOcorrido = findViewById(R.id.editTextDescricao);

        //recuperando dados do usuario
        usuario = UsuarioFirebase.getDadosUsuarioLogado();


        //configurando mensagens com id usuário destinatario e remetente
        database = ConfiguracaoFirebase.getFirebaseDatabase();
        mensagensRef = database.child("denuncias");


    }

    public void enviarDenuncia(View view){
        String statusPredefinido = "Não Resolvido";
        IdDenuncia = new Random().nextInt(99999999);
        Denuncia denuncia = new Denuncia();
        denuncia.setIdDenuncia(IdDenuncia.toString());
        denuncia.setIdentificacaoUsuario(usuarioAtual);
        denuncia.setIdentificacaoVitima(idVitima.getText().toString());
        denuncia.setIdentificacaoAgressor(idAgressor.getText().toString());
        denuncia.setLocalOcorrido(localOcorrido.getText().toString());
        denuncia.setDataOcorrido(dataOcorrido.getText().toString());
        denuncia.setHorarioOcorrido(horarioOcorrido.getText().toString());
        denuncia.setStatusDenuncia(statusPredefinido);
        denuncia.setTipoAgressao(tipoAgressao.getText().toString());
        denuncia.setDescricao(descricaoOcorrido.getText().toString());
        denuncia.setUsuarioExibicao(usuario);
        denuncia.Salvar();

        Toast.makeText(CampoDenunciaActivity.this,
                "Sucesso ao enviar denúncia!",
                Toast.LENGTH_SHORT).show();

        finish();


    }
}