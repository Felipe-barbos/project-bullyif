package com.felipebc.bullyif.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.felipebc.bullyif.R;
import com.felipebc.bullyif.config.ConfiguracaoFirebase;
import com.felipebc.bullyif.model.Denuncia;
import com.felipebc.bullyif.model.Usuario;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.EventListener;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Denuncias2Adapter  extends RecyclerView.Adapter<Denuncias2Adapter.MyViewHolder>{

    private List<Denuncia> denuncias;
    private List<Usuario> contatos;
    private Context context;
    private DatabaseReference usuariosRef;
    private StorageReference storageReference;
    private EventListener childEventListenerContatos;


    public Denuncias2Adapter(List<Denuncia> listaDenuncias, Context c) {
        this.denuncias = listaDenuncias;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(   parent.getContext() ).inflate(R.layout.adapter_denuncias2, parent, false);
        usuariosRef = ConfiguracaoFirebase.getFirebaseDatabase().child("usuarios");

        return new MyViewHolder(itemLista);
    }

    //recuperar os dados do objet denuncia e retornando o adapter
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Denuncia denuncia = denuncias.get(position);
        Usuario usuario = denuncia.getUsuarioExibicao();
        holder.nomeUsuario.setText(denuncia.getUsuarioExibicao().getNome());
        holder.tipoAgressao.setText(denuncia.getTipoAgressao());
        holder.idDenuncia.setText(denuncia.getIdDenuncia());
        holder.dataOcorrido.setText(denuncia.getDataOcorrido());
        holder.matricula.setText(denuncia.getUsuarioExibicao().getMatricula());
        holder.descricao.setText(denuncia.getDescricao());

        if(usuario.getFoto() != null){
            Uri uri = Uri.parse(usuario.getFoto());
            Glide.with(context).load(uri).into(holder.foto);
        }else{
            holder.foto.setImageResource(R.drawable.padrao);
        }

    }

    @Override
    public int getItemCount() {
        return denuncias.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        CircleImageView foto;
        TextView nomeUsuario, matricula, tipoAgressao, idDenuncia, dataOcorrido, descricao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foto = itemView.findViewById(R.id.imageViewFotoDenunciante);
            nomeUsuario = itemView.findViewById(R.id.textNomeUsuario);
            idDenuncia = itemView.findViewById(R.id.textIdDenunciaLista2);
            matricula = itemView.findViewById(R.id.textMatricula);
            tipoAgressao = itemView.findViewById(R.id.textTipoAgressao2);
            dataOcorrido = itemView.findViewById(R.id.textDataOcorrido2);
            descricao = itemView.findViewById(R.id.textDescricao2);


        }
    }


}