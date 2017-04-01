package com.example.android.muniappbeta.forms;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.muniappbeta.R;
import com.example.android.muniappbeta.model.Caja;
import com.example.android.muniappbeta.util.Comunes;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RcConsPagoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RcConsPagoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    Button btnConsulta;

    //Campos del formulario
    TextView tvCajeroRC;
    TextView tvFechaRC;
    TextView tvNomYApe;
    TextView tvTipoPartidaRc;
    TextView tvLibRc;
    TextView tvImporte;
    TextView tvFolioRC;
    TextView tvAnioRegistroRC;
    TextView tvCantidad;
    TextView tvTotalAPagar;
    EditText recibo;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public RcConsPagoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RcConsPagoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RcConsPagoFragment newInstance(String param1, String param2) {
        RcConsPagoFragment fragment = new RcConsPagoFragment();
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
      //  getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View view=inflater.inflate(R.layout.fragment_rc_cons_pago, container, false);
        btnConsulta=(Button)view.findViewById(R.id.btnConsultaRC);
        recibo=(EditText)view.findViewById(R.id.input_num_recibo);

        /**CAMPOS**/
        tvCajeroRC=(TextView)view.findViewById(R.id.tvCajeroRC);
        tvFechaRC=(TextView)view.findViewById(R.id.tvFechaRC);
        tvTipoPartidaRc=(TextView)view.findViewById(R.id.tvTipoPartidaRc);
        tvLibRc=(TextView)view.findViewById(R.id.tvLibRC);
        tvFolioRC=(TextView)view.findViewById(R.id.tvFolioRC);
        tvAnioRegistroRC=(TextView)view.findViewById(R.id.tvAnioRegistroRC);
        tvImporte=(TextView)view.findViewById(R.id.tvImporte);
        tvCantidad=(TextView)view.findViewById(R.id.tvCantidad);
        tvTotalAPagar=(TextView)view.findViewById(R.id.tvTotalAPagar);
        tvNomYApe=(TextView)view.findViewById(R.id.tvNomYApe);
        /**Fin de campos**/
        btnConsulta.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //Valida si es un numero
                if (Comunes.isNumeric(recibo.getText().toString())){
                    new SoapAction().execute(recibo.getText().toString());
                }else{
                    Toast.makeText(getActivity(), "Por favor Ingrese un numero valido", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }
        /**SOAP ACTION**/
    private class SoapAction extends AsyncTask<String,String,String> {
            //172.16.0.14
        static final String METHODNAME="ConsultarRegistroCivil";
        static final String URL= "http://apps2.munihuacho.gob.pe:9090/ConsultaDePagoWS/ConsultarPagos?WSDL";
        static final String NAMESPACE="http://consultar/";
        static final String SOAP_ACTION=NAMESPACE+METHODNAME;


        //*Datos*//
        Caja caja=new Caja();
        String nombreCompleto;

        @Override
        protected String doInBackground(String... params) {
            SoapObject request=new SoapObject(NAMESPACE,METHODNAME);
            request.addProperty("id",params[0]);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte=new HttpTransportSE(URL);
            try {

                transporte.debug=true;
                transporte.call(SOAP_ACTION,envelope);
                SoapObject response=(SoapObject)envelope.getResponse();


                //*Rescatar elementos del Output**/
                caja.setUsuario(response.getProperty("usuario").toString());
                caja.setLibro(response.getProperty("libro").toString());
                caja.setNombres(response.getProperty("nombres").toString());
                caja.setMaterno(response.getProperty("materno").toString());
                caja.setPaterno(response.getProperty("paterno").toString());
                caja.setFolio(response.getProperty("folio").toString());
                caja.setAñore(response.getProperty("añore").toString());
                caja.setImporte(response.getProperty("importe").toString());
                caja.setFecha(response.getProperty("fecha").toString());
                caja.setCantidad(response.getProperty("cantidad").toString());
                caja.setTotal(response.getProperty("total").toString());
                caja.setTipo(response.getProperty("tipo").toString());
                //Concat full name
                nombreCompleto=caja.getNombres() +" "+caja.getPaterno()+" "+caja.getMaterno();

            }catch (Exception ex){
                Log.d("Error",ex.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //LLenando campos del formulario con los datos
            tvCajeroRC.setText(caja.getUsuario());
            tvFechaRC.setText(caja.getFecha());
            tvNomYApe.setText(nombreCompleto);
            tvTipoPartidaRc.setText(caja.getTipo());
            tvLibRc.setText("Lib: "+caja.getLibro());
            tvFolioRC.setText("Folio: "+caja.getFolio());
            tvAnioRegistroRC.setText("Año de Registro: "+caja.getAñore());
            tvImporte.setText(caja.getImporte());
            tvCantidad.setText(caja.getCantidad());
            tvTotalAPagar.setText(caja.getTotal());
        }
    }

}
