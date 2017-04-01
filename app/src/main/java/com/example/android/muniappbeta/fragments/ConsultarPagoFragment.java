package com.example.android.muniappbeta.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.example.android.muniappbeta.R;
import com.example.android.muniappbeta.forms.RcConsPagoFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsultarPagoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultarPagoFragment extends Fragment {
    Button btnCajaCentral;
    Button btnRegistroCivil;
    Fragment newFragment=null;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ConsultarPagoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultarPagoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultarPagoFragment newInstance(String param1, String param2) {
        ConsultarPagoFragment fragment = new ConsultarPagoFragment();
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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view=inflater.inflate(R.layout.fragment_consultar_pago, container, false);

        btnCajaCentral=(Button)view.findViewById(R.id.btnCajaCentral);
        btnCajaCentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFragment= CajaCentralFragment.newInstance("Caja Central","Mensaje");
                if(newFragment!=null){
                    openFragment(newFragment);
                }

            }

        });
        btnRegistroCivil=(Button)view.findViewById(R.id.btnRegistroCivil);
        btnRegistroCivil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFragment= RcConsPagoFragment.newInstance("Registro Civil","Mensaje");
                if(newFragment!=null){
                    openFragment(newFragment);
                }
            }
        });
        return view;
    }
    private void openFragment(Fragment newFragment){
        Fragment containerFragment = getFragmentManager().findFragmentById(R.id.content_main);
        if (containerFragment == null){
            addFragment(newFragment);
        } else{
            if (!containerFragment.getClass().getName().equalsIgnoreCase(newFragment.getClass().getName())) {
                replaceFragment(newFragment);
            }
        }

    }
    private void replaceFragment(Fragment newFragment){
        FragmentManager fm=getFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.content_main,newFragment);
        ft.addToBackStack(newFragment.getClass().getName());
        ft.commit();
    }
    private void addFragment(Fragment newFragment) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.content_main, newFragment);
        ft.commit();
    }

}
