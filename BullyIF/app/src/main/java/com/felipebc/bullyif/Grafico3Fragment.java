package com.felipebc.bullyif;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.felipebc.bullyif.config.ConfiguracaoFirebase;
import com.felipebc.bullyif.model.Denuncia;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Grafico3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Grafico3Fragment extends Fragment {


    private DatabaseReference denunciasRef;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Grafico3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Grafico3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Grafico3Fragment newInstance(String param1, String param2) {
        Grafico3Fragment fragment = new Grafico3Fragment();
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
        View view2 =  inflater.inflate(R.layout.fragment_grafico3, container, false);
        /*denunciasRef = ConfiguracaoFirebase.getFirebaseDatabase().child("denuncias");
        denunciasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dados: snapshot.getChildren()){
                    Denuncia denuncia2 = dados.getValue(Denuncia.class);

                    if(denuncia2.getStatusDenuncia().contains("Resolvido")){
                        contator1 = contator1 + 1;

                    }
                    else if (denuncia2.getStatusDenuncia().contains("Em Andamento")){
                        contator2 = contator2 + 1;

                    }
                    else if (denuncia2.getStatusDenuncia().contains("Não Resolvido")){
                        contator3 = contator3 + 1;

                    }

                    PieChart pieChart = view.findViewById(R.id.pieChart);

                    ArrayList<PieEntry> qtFeedback = new ArrayList<>();
                    qtFeedback.add(new PieEntry(contator1, "Resolvido"));
                    qtFeedback.add(new PieEntry(contator2,"Em Andamento"));
                    qtFeedback.add(new PieEntry(contator3,"Não Resolvido"));

                    PieDataSet pieDataSet = new PieDataSet(qtFeedback,"Feedback da Assistência");
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    pieDataSet.setValueTextColor(Color.WHITE);
                    pieDataSet.setValueTextSize(16f);


                    PieData pieData = new PieData(pieDataSet);
                    pieChart.setData(pieData);
                    pieChart.setCenterText("Feedback");
                    pieChart.animate();




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/
        return view2;
    }
}