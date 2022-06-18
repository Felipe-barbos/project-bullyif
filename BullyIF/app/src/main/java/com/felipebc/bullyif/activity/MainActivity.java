package com.felipebc.bullyif.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.felipebc.bullyif.Grafico3Fragment;
import com.felipebc.bullyif.R;
import com.felipebc.bullyif.config.ConfiguracaoFirebase;
import com.felipebc.bullyif.fragment.ContatosFragment;
import com.felipebc.bullyif.fragment.ConversasFragment;
import com.felipebc.bullyif.fragment.Grafico1Fragment;
import com.felipebc.bullyif.fragment.Grafico2Fragment;
import com.felipebc.bullyif.helper.Permissao;
import com.felipebc.bullyif.helper.UsuarioFirebase;
import com.felipebc.bullyif.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private  String[] permissõesNecessarias = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private FirebaseAuth autenticacao;
    private StorageReference storageReference;
    private ImageButton imageButtonCamera, imageButtonGaleria;
    private CircleImageView circleImageViewPerfil;
    private String identificadorUsuario;
    private TextView editPerfilNome;
    private Usuario usuarioLogado;



    private static final int SELECAO_CAMERA = 100;
    private  static final int SELECAO_GALERIA = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //configurar identificador usuário recuperando
        identificadorUsuario = UsuarioFirebase.getIdentificadorUsuario();

        //validar permissão para acessar o storage do bancod e dados
        storageReference = ConfiguracaoFirebase.getFirebaseStorage();

        //validar permissões
        Permissao.validarPermissoes(permissõesNecessarias, this,1);

        //configurando imagebutton
        imageButtonCamera = findViewById(R.id.imageButtonCamera);
        imageButtonGaleria = findViewById(R.id.imageButtonGaleria);

        //configurando imagem circular do perfil
        circleImageViewPerfil = findViewById(R.id.circleImageFotoPerfil);

        //configurando editText
        editPerfilNome = findViewById(R.id.nomedoPerfil);

        //instanciando o objeto autenticacao
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //instaciando a toolbar na activity
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //recuperar dados do usuário
        FirebaseUser usuario = UsuarioFirebase.getUsuarioAtual();
        Uri url = usuario.getPhotoUrl();

        //carregando imagem de perfil na activity
        if(url != null){
            Glide.with(MainActivity.this)
                    .load(url)
                    .into(circleImageViewPerfil);
        }else{
            circleImageViewPerfil.setImageResource(R.drawable.padrao);
        }


        //recuperar dados do usuário para ser exibido no perfil
        editPerfilNome.setText(usuario.getDisplayName());

        //atualizar os dados do usuário
        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();
        usuarioLogado.atualizar();


        //abrir camera
        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(i.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(i, SELECAO_CAMERA);
                }

            }
        });

        //abrir galeria

        imageButtonGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if(i.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(i, SELECAO_GALERIA);
                }
            }
        });

        //configurando abbas
        //view pager do gráfico

        FragmentPagerItemAdapter adapter =  new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("", Grafico1Fragment.class)
                        .add("", Grafico2Fragment.class)
                        .create()

        );
        ViewPager viewPager = findViewById(R.id.viewPagerGrafico);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = findViewById(R.id.viewPagerTabGrafico);
        viewPagerTab.setViewPager(viewPager);





    }


    //criando menu na activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSair:
                deslogarUsuario();
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void deslogarUsuario(){
        try{

            autenticacao.signOut();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //REcuperando a imagem no circle view do perfil
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK){
            Bitmap imagem = null;

            try{
                switch (requestCode){
                    case SELECAO_CAMERA:
                        imagem = (Bitmap) data.getExtras().get("data");
                        break;
                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        imagem= MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);
                        break;
                }
                if(imagem != null){

                    circleImageViewPerfil.setImageBitmap(imagem);

                    //recuperar imagem para o firebase
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    //salvar imagem no firebase
                    final StorageReference imagemRef = storageReference
                            .child("imagens")
                            .child("perfil")
                           // .child(identificadorUsuario)
                            .child(identificadorUsuario+".png");

                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this,
                                    "Erro ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(MainActivity.this,
                                    "Sucesso ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();

                            //recuperar url da imagem de perfil
                            imagemRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Uri url = task.getResult();
                                    atualizaFotoUsuario(url);

                                }
                            });


                        }
                    });

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void atualizaFotoUsuario(Uri url){
        boolean retorno = UsuarioFirebase.atualizarFotoUsuario(url);
        if(retorno){
            usuarioLogado.setFoto(url.toString());
            usuarioLogado.atualizar();

            Toast.makeText(MainActivity.this,
                    "Sua foto foi alterada!",
                    Toast.LENGTH_SHORT).show();
        }



    }


    public void abrirTelaChat(View view){
        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
        startActivity(intent);
    }

    public void abrirTelaDenuncia(View view){

        //configurações iniciais para acessar o banco de dados
        DatabaseReference usuariosRef;
        usuariosRef = ConfiguracaoFirebase.getFirebaseDatabase().child("usuarios");
        String idUsuario = UsuarioFirebase.getIdentificadorUsuario();

        //objeto que irá pegar as informações do usuário atual.
        Usuario usuario = new Usuario();
        DatabaseReference usuarioPesquisa = usuariosRef.child(idUsuario);
        usuarioPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario dadosUsuario = snapshot.getValue(Usuario.class);

                String TipoUsuario = dadosUsuario.getTipoUsuario();

                if(TipoUsuario.contains("Estudante") || TipoUsuario.contains("Professor")){
                    Intent intent = new Intent(MainActivity.this, ListaDenuncia.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(MainActivity.this, ListaDenuncia2.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }

        );





    }



    //metodos que verifica se o usuário concedeu as permissões necessárias para o funcionamento do app

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int permissaoResultado: grantResults){
            if(permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }



}