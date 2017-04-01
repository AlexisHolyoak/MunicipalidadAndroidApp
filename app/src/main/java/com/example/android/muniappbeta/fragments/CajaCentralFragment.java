package com.example.android.muniappbeta.fragments;


import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.muniappbeta.R;
import com.example.android.muniappbeta.forms.CcCtaAhorroFragment;
import com.example.android.muniappbeta.forms.CcCtaCteFragment;
import com.example.android.muniappbeta.model.CajaCentral;
import com.example.android.muniappbeta.model.CajaCentralCc;
import com.example.android.muniappbeta.util.Caracteres;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CajaCentralFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CajaCentralFragment extends Fragment {
    Button btnBuscar;
    EditText input_fecha;
    EditText numMovimiento;
    EditText numCajero;
    TextView tvCajero;
    private  int dia,mes,ano;
    static final int DIALOG_ID=0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public CajaCentralFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CajaCentralFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CajaCentralFragment newInstance(String param1, String param2) {
        CajaCentralFragment fragment = new CajaCentralFragment();
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
        //ESTO HACE QUE EL TECLADO NO DISTORSIONE CUANDO HAGA POP UP
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_caja_central, container, false);


        input_fecha=(EditText) view.findViewById(R.id.input_fecha);
        //Handle Cta Cte Button Actions
        btnBuscar=(Button)view.findViewById(R.id.btnBuscarCC);
        numCajero=(EditText) view.findViewById(R.id.tvCajeroCC);
        numMovimiento=(EditText)view.findViewById(R.id.numMovimiento);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        input_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Caracteres c=new Caracteres();
                if(!c.movimientoSet(numMovimiento.getText().toString())){
                    Toast.makeText(getActivity(), "Movimiento Necesita 7 Caracteres", Toast.LENGTH_SHORT).show();

                }else{

                    new SoapAction().execute(input_fecha.getText().toString().trim(),
                            numMovimiento.getText().toString().trim(),
                            numCajero.getText().toString().trim()
                    );
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    /**DATE PICKER ACTIONS**/
    private void showDatePicker(){
        DatePickerFragment date=new DatePickerFragment();
        /*
        * Set up Current Date in Dialog
        */
        Calendar calendar=Calendar.getInstance();
        Bundle args=new Bundle();
        args.putInt("year",calendar.get(Calendar.YEAR));
        args.putInt("month",calendar.get(Calendar.MONTH));
        args.putInt("day",calendar.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /*
        * Ser CallBack to capture selected Data
        */
        date.setCallBack(ondate);
        date.show(getFragmentManager(),"Date Picker");
    }
    DatePickerDialog.OnDateSetListener ondate=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Caracteres caracteres=new Caracteres();

            input_fecha.setText(String.valueOf(year)+"-"+caracteres.fechaSet(String.valueOf(month+1))+"-"+caracteres.fechaSet(String.valueOf(dayOfMonth)));
        }
    };
    /**SOAP ACTION METHOD**/
    private class SoapAction extends AsyncTask<String,String,String> {

        static final String URL= "http://apps2.munihuacho.gob.pe:9090/ConsultaDePagoWS/ConsultarPagos?WSDL";
        static final String NAMESPACE="http://consultar/";
        String METHODNAME="";
        CajaCentralCc cajaCentralCc=new CajaCentralCc();
        CajaCentral cajaCentral=new CajaCentral();
        String mensaje;
        ArrayList<String> listaImporte=new ArrayList<String>();
        ArrayList<String> listaConcepto=new ArrayList<String>();
        String output="";
        String nombre="";
        // static final String SOAP_ACTION=NAMESPACE+METHODNAME;
        @Override
        protected String doInBackground(String... params) {
            try {
                METHODNAME="ConsultarCajaCentralCc";
                String SOAP_ACTION=NAMESPACE+METHODNAME;
                SoapObject request=new SoapObject(NAMESPACE,METHODNAME);
                request.addProperty("fecha",params[0]);
                request.addProperty("movimiento",params[1]);
                request.addProperty("caja",params[2]);
                SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=false;
                envelope.setOutputSoapObject(request);
                HttpTransportSE transporte = new HttpTransportSE(URL);
                transporte.call(SOAP_ACTION, envelope);
                SoapObject response=(SoapObject)envelope.getResponse();
                if(response.getPropertyCount()>0){
                    //ASINGAR PROPIEDADES
                    cajaCentralCc.setTributo(response.getProperty("tributo").toString());
                    cajaCentralCc.setContribuyente(response.getProperty("contribuyente").toString());
                    cajaCentralCc.setMovimiento(response.getProperty("movimiento").toString());
                    cajaCentralCc.setNombre(response.getProperty("nombre").toString());
                    cajaCentralCc.setDeuda(response.getProperty("deuda").toString());
                    cajaCentralCc.setEmision(response.getProperty("emision").toString());
                    cajaCentralCc.setMorein(response.getProperty("morein").toString());
                    cajaCentralCc.setPeriodo(response.getProperty("periodo").toString());
                    cajaCentralCc.setTotal(response.getProperty("total").toString());
                    cajaCentralCc.setHora(response.getProperty("hora").toString());
                    cajaCentralCc.setUsuario(response.getProperty("usuario").toString());
                    cajaCentralCc.setFecha(response.getProperty("fecha").toString());
                    cajaCentralCc.setCaja(response.getProperty("caja").toString());

                }
            }catch (Exception ex){
                //this returns a list
                try {
                    METHODNAME="ConsultarCajaCentral";
                    String SOAP_ACTION=NAMESPACE+METHODNAME;
                    SoapObject request=new SoapObject(NAMESPACE,METHODNAME);
                    //WebParams
                    request.addProperty("fecha",params[0]);
                    request.addProperty("movimiento",params[1]);
                    request.addProperty("caja",params[2]);
                    SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet=false;
                    envelope.setOutputSoapObject(request);
                    HttpTransportSE transporte = new HttpTransportSE(URL);
                    transporte.call(SOAP_ACTION, envelope);
                    //
                    SoapObject obj1=(SoapObject)envelope.bodyIn;
                    for (int i=0;i<obj1.getPropertyCount();i++){
                        SoapObject obj2=(SoapObject)obj1.getProperty(i);
                        cajaCentral.setNombre(obj2.getProperty("nombre").toString());
                        cajaCentral.setMovimiento(obj2.getProperty("movimiento").toString());
                        cajaCentral.setLiq(obj2.getProperty("liq").toString());
                        cajaCentral.setCaja(obj2.getProperty("caja").toString());
                        cajaCentral.setUsuario(obj2.getProperty("usuario").toString());
                        cajaCentral.setHora(obj2.getProperty("hora").toString());
                        cajaCentral.setFecha(obj2.getProperty("fecha").toString());
                        cajaCentral.setTotal(obj2.getProperty("total").toString());
                        listaConcepto.add(obj2.getProperty("concepto").toString());
                        listaImporte.add(obj2.getProperty("importe").toString());
                    }

                }catch (Exception er){
                    mensaje="No se encontraron resultados, por favor verifique los datos."+er.getMessage();
                    er.printStackTrace();
                }

            }
            return null;
        }



        @Override
        protected void onPostExecute(String s) {
            if(mensaje!=null){
                Toast.makeText(getActivity(), mensaje, Toast.LENGTH_SHORT).show();
            }else{
                //Cuenta Ahorro
                if(METHODNAME.equals("ConsultarCajaCentralCc")){
                    Fragment newFragment;
                    newFragment= CcCtaCteFragment.newInstance(cajaCentralCc.getTributo(),
                            cajaCentralCc.getContribuyente(),
                            cajaCentralCc.getMovimiento(),
                            cajaCentralCc.getCaja(),
                            cajaCentralCc.getNombre(),
                            cajaCentralCc.getPeriodo(),
                            cajaCentralCc.getDeuda(),
                            cajaCentralCc.getEmision(),
                            cajaCentralCc.getMorein(),
                            cajaCentralCc.getFecha(),
                            cajaCentralCc.getHora(),
                            cajaCentralCc.getTotal(),
                            cajaCentralCc.getUsuario()
                    );
                    openFragment(newFragment);
                }
                if(METHODNAME.equals("ConsultarCajaCentral")){
                    Fragment newFragment= CcCtaAhorroFragment.newInstance(cajaCentral.getMovimiento(),
                            cajaCentral.getCaja(),
                            cajaCentral.getLiq(),
                            cajaCentral.getNombre(),
                            cajaCentral.getUsuario(),
                            cajaCentral.getHora(),
                            cajaCentral.getFecha(),
                            cajaCentral.getTotal(),
                            listaConcepto,
                            listaImporte
                    );
                    openFragment(newFragment);
                }
            }
            super.onPostExecute(s);
        }
    }
    /**FRAGMENTS ACTION**/
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
