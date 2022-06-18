package com.felipebc.bullyif.helper;

import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;

import com.felipebc.bullyif.config.ConfiguracaoFirebase;
import com.felipebc.bullyif.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsuarioFirebase {





    public static String getIdentificadorUsuario(){
        FirebaseAuth usuario = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String email = usuario.getCurrentUser().getEmail();
        String identificadorUsuario = Base64Custom.codificarBase64(email);





        return identificadorUsuario;
    }

    public static FirebaseUser getUsuarioAtual() {
        FirebaseAuth usuario = ConfiguracaoFirebase.getFirebaseAutenticacao();
        return usuario.getCurrentUser();
    }

//atualizar os dados do perfil como foto, nome e matricula com email
    public static boolean atualizarDadosUsuario(String nome, String matricula, String email){

        try{
            FirebaseUser user = getUsuarioAtual();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(nome+"\n"+matricula+"\n"+email)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!task.isSuccessful()){
                        Log.d("Perfil","Erro ao atualizar dados do perfil!");
                    }
                }
            });

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }





    public static boolean atualizarFotoUsuario(Uri url){

        try{
            FirebaseUser user = getUsuarioAtual();
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(url)
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(!task.isSuccessful()){
                        Log.d("Perfil","Erro ao atualizar foto do perfil!");
                    }
                }

            });



            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }
    public static Usuario getDadosUsuarioLogado(){

        //metodo permite recuperar  os dados do usuário
        FirebaseUser firebaseUser = getUsuarioAtual();

        DatabaseReference usuariosRef;
        usuariosRef = ConfiguracaoFirebase.getFirebaseDatabase().child("usuarios");
        String idUsuario = getIdentificadorUsuario();

        DatabaseReference usuarioPesquisa = usuariosRef.child(idUsuario);

        Usuario usuario = new Usuario();

        usuarioPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario dadosUsuario = snapshot.getValue(Usuario.class);

                usuario.setNome(dadosUsuario.getNome());
                usuario.setMatricula(dadosUsuario.getMatricula());
                usuario.setTipoUsuario(dadosUsuario.getTipoUsuario());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        usuario.getNome();
        usuario.getEmail();
        usuario.getTipoUsuario();

        if(firebaseUser.getPhotoUrl() == null){
            usuario.setFoto("");
        }else{
            usuario.setFoto(firebaseUser.getPhotoUrl().toString());
        }
        return usuario;
    }



}
