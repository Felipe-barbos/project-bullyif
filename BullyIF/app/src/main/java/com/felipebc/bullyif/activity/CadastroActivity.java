package com.felipebc.bullyif.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.felipebc.bullyif.R;
import com.felipebc.bullyif.config.ConfiguracaoFirebase;
import com.felipebc.bullyif.helper.Base64Custom;
import com.felipebc.bullyif.helper.Permissao;
import com.felipebc.bullyif.helper.UsuarioFirebase;
import com.felipebc.bullyif.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class CadastroActivity extends AppCompatActivity {




    private TextInputEditText campoNome, campoMatricula, campoEmail, campoSenha;
    private FirebaseAuth autenticacao;

    private CheckBox campoEstudante, campoProfessor, campoAssistente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //configurando text
        campoNome = findViewById(R.id.editNome);
        campoMatricula = findViewById(R.id.editMatricula);
        campoEmail = findViewById(R.id.editLoginEmail);
        campoSenha = findViewById(R.id.editLoginSenha);
        campoEstudante = findViewById(R.id.editEstudante);
        campoProfessor = findViewById(R.id.editProfessor);
        campoAssistente = findViewById(R.id.editAssistente);

    }



    public void cadastrarUsuario(Usuario usuario){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toast.makeText(CadastroActivity.this,
                            "Sucesso ao cadastrar o usuário!",
                            Toast.LENGTH_SHORT).show();
                UsuarioFirebase.atualizarDadosUsuario(usuario.getNome(), usuario.getMatricula(), usuario.getEmail());

                    finish();
                    try{
                        //usando email como id do usuario em base 64
                        String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                        usuario.setId(identificadorUsuario);
                        usuario.salvar();


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{

                    String excecao = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte!";
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        excecao = "Por favor, Digite um e-mail válido";
                    }catch (FirebaseAuthUserCollisionException e ){
                        excecao = "Está conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário"+ e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void validarCadastroUsuario(View view){

        //recuperando os textos dos campos
        String textoNome = campoNome.getText().toString();
        String textoMatricula = campoMatricula.getText().toString();
        String textoEmail = campoEmail.getText().toString();
        String textoSenha = campoSenha.getText().toString();
        String textoEstudante = "Estudante";
        String textoProfessor = "Professor";
        String textoAssistente = "Assistente Estudantil";
        if(!textoNome.isEmpty()){//verifica nome

            if(!textoMatricula.isEmpty()){//verifica Matricula

                if(!textoEmail.isEmpty()){//verifica Email

                    if(!textoSenha.isEmpty()){//verifica a senha

                        if(campoEstudante.isChecked()){
                            Usuario usuario = new Usuario();
                            usuario.setNome(textoNome);
                            usuario.setMatricula(textoMatricula);
                            usuario.setEmail(textoEmail);
                            usuario.setSenha(textoSenha);
                            usuario.setTipoUsuario(textoEstudante);

                            cadastrarUsuario(usuario);
                        }else if(campoProfessor.isChecked()){
                            Usuario usuario = new Usuario();
                            usuario.setNome(textoNome);
                            usuario.setMatricula(textoMatricula);
                            usuario.setEmail(textoEmail);
                            usuario.setSenha(textoSenha);
                            usuario.setTipoUsuario(textoProfessor);

                            cadastrarUsuario(usuario);
                        }else if(campoAssistente.isChecked()){
                            Usuario usuario = new Usuario();
                            usuario.setNome(textoNome);
                            usuario.setMatricula(textoMatricula);
                            usuario.setEmail(textoEmail);
                            usuario.setSenha(textoSenha);
                            usuario.setTipoUsuario(textoAssistente);

                            cadastrarUsuario(usuario);

                        }
                        else{
                            Toast.makeText(CadastroActivity.this,
                                    "Tipo de Usuário não selecionado!",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }else{
                        Toast.makeText(CadastroActivity.this,
                                "Preencha sua Senha!!!!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CadastroActivity.this,
                            "Preencha seu Email!!!",
                            Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(CadastroActivity.this,
                        "Preencha sua Matricula!!!",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(CadastroActivity.this,
                    "Preencha o nome!",
                    Toast.LENGTH_SHORT).show();
        }

    }


}