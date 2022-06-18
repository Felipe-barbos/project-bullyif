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

public class DenunciasAdapter  extends RecyclerView.Adapter<DenunciasAdapter.MyViewHolder>{

private List<Denuncia> denuncias;
private List<Usuario> contatos;
private Context context;
private DatabaseReference usuariosRef;
private StorageReference storageReference;
private EventListener childEventListenerContatos;


    public DenunciasAdapter(List<Denuncia> listaDenuncias, Context c) {
        this.denuncias = listaDenuncias;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(   parent.getContext() ).inflate(R.layout.adapter_denuncias, parent, false);
        usuariosRef = ConfiguracaoFirebase.getFirebaseDatabase().child("usuarios");

        return new MyViewHolder(itemLista);
    }

    //recuperar os dados do objet denuncia e retornando o adapter
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Denuncia denuncia = denuncias.get(position);
        Usuario usuario = denuncia.getUsuarioExibicao();
        holder.tipoagresao.setText(denuncia.getTipoAgressao());
        holder.idDenuncia.setText(denuncia.getIdDenuncia());
        holder.dataOcorrido.setText(denuncia.getDataOcorrido());
        holder.horarioOcorrido.setText(denuncia.getHorarioOcorrido());
        holder.status.setText(denuncia.getStatusDenuncia());
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
        TextView tipoagresao, idDenuncia, descricao,status, dataOcorrido, horarioOcorrido;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            foto = itemView.findViewById(R.id.imageViewFotoDenunciante);
            tipoagresao = itemView.findViewById(R.id.textTipoAgressao);
            idDenuncia = itemView.findViewById(R.id.textIdDenunciaLista2);
            dataOcorrido = itemView.findViewById(R.id.textDataOcorrido);
            horarioOcorrido = itemView.findViewById(R.id.textHorarioOcorrido);
            descricao = itemView.findViewById(R.id.textDescricao);
            status = itemView.findViewById(R.id.textStatusDenuncia);

        }
    }


}

