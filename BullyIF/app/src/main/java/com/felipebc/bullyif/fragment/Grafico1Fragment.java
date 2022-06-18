package com.felipebc.bullyif.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.felipebc.bullyif.R;
import com.felipebc.bullyif.config.ConfiguracaoFirebase;
import com.felipebc.bullyif.model.Denuncia;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Grafico1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Grafico1Fragment extends Fragment {

    private ProgressBar progressNucleo, progressRefeitorio, progressAuditorio, progressEnsinoMedio;
    private ArrayList<Denuncia> listaDenuncias2 = new ArrayList<>();
    private DatabaseReference denunciasRef;
    private int contator = 0;
    private int contator2 = 0;
    private int contator3 = 0;
    private int contator4 = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Grafico1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment grafico1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Grafico1Fragment newInstance(String param1, String param2) {
        Grafico1Fragment fragment = new Grafico1Fragment();
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


        View view =  inflater.inflate(R.layout.fragment_grafico1, container, false);

        progressNucleo = view.findViewById(R.id.progressNucleo);
        progressAuditorio = view.findViewById(R.id.progressAuditorio);
        progressRefeitorio = view.findViewById(R.id.progressRefeitorio);
        progressEnsinoMedio = view.findViewById(R.id.progressEnsinoMedio);


        denunciasRef = ConfiguracaoFirebase.getFirebaseDatabase().child("denuncias");
                denunciasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dados: snapshot.getChildren()){
                    Denuncia denuncia2 = dados.getValue(Denuncia.class);

                    if(denuncia2.getLocalOcorrido().contains("Núcleo de Informática")){
                         contator = contator + 10;
                         progressNucleo.setProgress(contator);
                         progressNucleo.setMax(100);
                    }
                     else if (denuncia2.getLocalOcorrido().contains("Refeitório")){
                        contator2 = contator2 + 30;
                        progressRefeitorio.setProgress(contator2);
                        progressRefeitorio.setMax(100);
                    }
                    else if (denuncia2.getLocalOcorrido().contains("Auditório")){
                        contator3 = contator3 + 40;
                        progressAuditorio.setProgress(contator3);
                        progressAuditorio.setMax(100);
                    }
                     else if(denuncia2.getLocalOcorrido().contains("Ensino Médio")){
                        contator4 = contator4 + 70;
                        progressEnsinoMedio.setProgress(contator4);
                        progressEnsinoMedio.setMax(100);
                    }
                     else if(denuncia2.getLocalOcorrido().contains("")){

                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }


}