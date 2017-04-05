package pe.gob.munihuacho.municipalidadhuacho.forms;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import pe.gob.munihuacho.municipalidadhuacho.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CcCtaAhorroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CcCtaAhorroFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";
    private static final String ARG_PARAM8 = "param8";
    private static final String ARG_PARAM9 = "param9";
    private static final String ARG_PARAM10 = "param10";

    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;
    private String mParam7;
    private String mParam8;
    private ArrayList<String> mParam9;
    private ArrayList<String> mParam10;
    /**Campos**/
    TextView tvUsuarioCnCte;
    TextView tvNombreCtaNcte;
    TextView tvLiqCnCte;
    TextView tvMovCnCte;
    TextView tvCajeroCnCte;
    TextView tvFechaCnCte;
    TextView tvHoraCnCte;
    TextView tvTotalCnCte;
    /**Estructura*/
    TableLayout tl;
    TableRow filas;
    TextView tvImporte;
    TextView tvConcepto;

    public CcCtaAhorroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CcCtaAhorroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CcCtaAhorroFragment newInstance(String param1,
                                                  String param2,
                                                  String param3,
                                                  String param4,
                                                  String param5,
                                                  String param6,
                                                  String param7,
                                                  String param8,
                                                  ArrayList param9,
                                                  ArrayList param10) {
        CcCtaAhorroFragment fragment = new CcCtaAhorroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        args.putString(ARG_PARAM7, param7);
        args.putString(ARG_PARAM8, param8);
        args.putStringArrayList(ARG_PARAM9, param9);
        args.putStringArrayList(ARG_PARAM10, param10);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
            mParam5 = getArguments().getString(ARG_PARAM5);
            mParam6 = getArguments().getString(ARG_PARAM6);
            mParam7 = getArguments().getString(ARG_PARAM7);
            mParam8 = getArguments().getString(ARG_PARAM8);
            mParam9 = getArguments().getStringArrayList(ARG_PARAM9);
            mParam10 = getArguments().getStringArrayList(ARG_PARAM10);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cc_cta_ahorro, container, false);
        tvUsuarioCnCte=(TextView)view.findViewById(R.id.tvUsuarioCnCte);
        tvNombreCtaNcte=(TextView)view.findViewById(R.id.tvNombreCtaNcte);
        tvFechaCnCte=(TextView)view.findViewById(R.id.tvFechaCnCte);
        tvMovCnCte=(TextView)view.findViewById(R.id.tvMovCnCte);
        tvLiqCnCte=(TextView)view.findViewById(R.id.tvLiqCnCte);
        tvHoraCnCte=(TextView)view.findViewById(R.id.tvHoraCnCte);
        tvCajeroCnCte=(TextView)view.findViewById(R.id.tvCajeroCnCte);
        tvTotalCnCte=(TextView)view.findViewById(R.id.tvTotalCnCte);
        tl=(TableLayout)view.findViewById(R.id.tabla);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvMovCnCte.setText(mParam1);
        tvCajeroCnCte.setText(mParam2);
        tvLiqCnCte.setText(mParam3);
        tvNombreCtaNcte.setText(mParam4);
        tvUsuarioCnCte.setText(mParam5);
        tvHoraCnCte.setText(mParam6);
        tvFechaCnCte.setText(mParam7);
        tvTotalCnCte.setText(mParam8);

        for (int i=0;i<mParam9.size();i++){
            filas=new TableRow(getContext());
            tvImporte=new TextView(getContext());
            tvConcepto=new TextView(getContext());
            filas.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.
                    MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT));

            /*Concepto*/
            tvConcepto.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT,1f));
            tvConcepto.setGravity(Gravity.LEFT);
            tvConcepto.setText(mParam9.get(i));



            /*Importe*/
            tvImporte.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT,1f));
            tvImporte.setGravity(Gravity.RIGHT);
            tvImporte.setText(mParam10.get(i));


            filas.addView(tvConcepto);
            filas.addView(tvImporte);
            tl.addView(filas,new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }

    }
}
