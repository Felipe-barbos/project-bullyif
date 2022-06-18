package com.felipebc.bullyif.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.felipebc.bullyif.R;
import com.felipebc.bullyif.activity.CadastroActivity;
import com.felipebc.bullyif.activity.DenunciaActivity;
import com.felipebc.bullyif.activity.ListaDenuncia;
import com.felipebc.bullyif.activity.ListaDenuncia2;
import com.felipebc.bullyif.activity.MainActivity;
import com.felipebc.bullyif.adapter.Denuncias2Adapter;
import com.felipebc.bullyif.adapter.DenunciasAdapter;
import com.felipebc.bullyif.config.ConfiguracaoFirebase;
import com.felipebc.bullyif.helper.RecyclerItemClickListener;
import com.felipebc.bullyif.helper.UsuarioFirebase;
import com.felipebc.bullyif.model.Denuncia;
import com.felipebc.bullyif.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DenunciasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DenunciasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView recyclerViewListaDenuncias;
    private DenunciasAdapter adapter;
    private ArrayList<Denuncia> listaDenuncias = new ArrayList<>();
    private DatabaseReference denunciasRef;
    private ValueEventListener valueEventListenerDenuncias;
    private ValueEventListener valueEventListenerContatos;
    public DenunciasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DenunciasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DenunciasFragment newInstance(String param1, String param2) {
        DenunciasFragment fragment = new DenunciasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_denuncias, container, false);

        //configurações iniciais
        recyclerViewListaDenuncias = view.findViewById(R.id.recyclerViewListaDenuncias);
        denunciasRef = ConfiguracaoFirebase.getFirebaseDatabase().child("denuncias");

        //configuração do adapter
        adapter =  new DenunciasAdapter(listaDenuncias, getActivity() );

        //configurar recyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity());
        recyclerViewListaDenuncias.setLayoutManager(layoutManager);
        recyclerViewListaDenuncias.setHasFixedSize( true );
        recyclerViewListaDenuncias.setAdapter(adapter);


        //evento de clique denuncia

        recyclerViewListaDenuncias.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getActivity(),
                        recyclerViewListaDenuncias,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                               Denuncia denunciaSelecionada = listaDenuncias.get(position);
                               Intent i = new Intent(getActivity(), DenunciaActivity.class);
                               i.putExtra("IDenuncia",denunciaSelecionada.getIdDenuncia());
                               startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );



        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarDenuncias();
    }

    @Override
    public void onStop() {
        super.onStop();
        denunciasRef.removeEventListener(valueEventListenerDenuncias);
    }

    public void recuperarDenuncias(){

       valueEventListenerDenuncias =  denunciasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dados: snapshot.getChildren()){
                    Denuncia denuncia = dados.getValue(Denuncia.class);
                    listaDenuncias.add(denuncia);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}