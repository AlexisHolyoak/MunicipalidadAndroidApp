package com.example.android.muniappbeta.forms;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.muniappbeta.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CcCtaCteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CcCtaCteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3="param3";
    private static final String ARG_PARAM4="param4";
    private static final String ARG_PARAM5="param5";
    private static final String ARG_PARAM6="param6";
    private static final String ARG_PARAM7="param7";
    private static final String ARG_PARAM8="param8";
    private static final String ARG_PARAM9="param9";
    private static final String ARG_PARAM10="param10";
    private static final String ARG_PARAM11="param11";
    private static final String ARG_PARAM12="param12";
    private static final String ARG_PARAM13="param13";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;
    private String mParam7;
    private String mParam8;
    private String mParam9;
    private String mParam10;
    private String mParam11;
    private String mParam12;
    private String mParam13;

    /**Form elements**/
    TextView tvTributoCcte;
    TextView tvContribuyenteCcte;
    TextView tvMovCcte;
    TextView tvCajeroCcte;
    TextView tvIntervaloFechaCcte;
    TextView tvTolDeudaCcte;
    TextView tvTolEmisionCcte;
    TextView tvTolMeReInCcte;
    TextView tvFechaCcte;
    TextView tvHoraCcte;
    TextView tvUsuarioCcte;
    TextView tvNombreCcte;
    TextView tvTotalCcte;

    public CcCtaCteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CcCtaCteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CcCtaCteFragment newInstance(String param1,
                                               String param2,
                                               String param3,
                                               String param4,
                                               String param5,
                                               String param6,
                                               String param7,
                                               String param8,
                                               String param9,
                                               String param10,
                                               String param11,
                                               String param12,
                                               String param13) {
        CcCtaCteFragment fragment = new CcCtaCteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4,param4);
        args.putString(ARG_PARAM5,param5);
        args.putString(ARG_PARAM6,param6);
        args.putString(ARG_PARAM7,param7);
        args.putString(ARG_PARAM8,param8);
        args.putString(ARG_PARAM9,param9);
        args.putString(ARG_PARAM10,param10);
        args.putString(ARG_PARAM11,param11);
        args.putString(ARG_PARAM12,param12);
        args.putString(ARG_PARAM13,param13);
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
            mParam9 = getArguments().getString(ARG_PARAM9);
            mParam10 = getArguments().getString(ARG_PARAM10);
            mParam11 = getArguments().getString(ARG_PARAM11);
            mParam12 = getArguments().getString(ARG_PARAM12);
            mParam13 = getArguments().getString(ARG_PARAM13);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_cc_cta_cte, container, false);
        tvTributoCcte=(TextView)view.findViewById(R.id.tvTributoCcte);
        tvContribuyenteCcte=(TextView)view.findViewById(R.id.tvContribuyenteCcte);
        tvMovCcte=(TextView)view.findViewById(R.id.tvMovCcte);
        tvCajeroCcte=(TextView)view.findViewById(R.id.tvCajeroCcte);
        tvIntervaloFechaCcte=(TextView)view.findViewById(R.id.tvIntervaloFechaCcte);
        tvTolDeudaCcte=(TextView)view.findViewById(R.id.tvTolDeudaCcte);
        tvTolEmisionCcte=(TextView)view.findViewById(R.id.tvTolEmisionCcte);
        tvTolMeReInCcte=(TextView)view.findViewById(R.id.tvTolMeReInCcte);
        tvFechaCcte=(TextView)view.findViewById(R.id.tvFechaCcte);
        tvHoraCcte=(TextView)view.findViewById(R.id.tvHoraCcte);
        tvUsuarioCcte=(TextView)view.findViewById(R.id.tvUsuarioCcte);
        tvNombreCcte=(TextView)view.findViewById(R.id.tvNombreCcte);
        tvTotalCcte=(TextView)view.findViewById(R.id.tvTotalCcte);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tvTributoCcte.setText(mParam1);
        tvContribuyenteCcte.setText(mParam2);
        tvMovCcte.setText(mParam3);
        tvCajeroCcte.setText(mParam4);
        tvNombreCcte.setText(mParam5);
        tvIntervaloFechaCcte.setText(mParam6);
        tvTolDeudaCcte.setText(mParam7);
        tvTolEmisionCcte.setText(mParam8);
        tvTolMeReInCcte.setText(mParam9);
        tvFechaCcte.setText(mParam10);
        tvHoraCcte.setText(mParam11);
        tvTotalCcte.setText(mParam12);
        tvUsuarioCcte.setText(mParam13);
        super.onViewCreated(view, savedInstanceState);
    }

}
