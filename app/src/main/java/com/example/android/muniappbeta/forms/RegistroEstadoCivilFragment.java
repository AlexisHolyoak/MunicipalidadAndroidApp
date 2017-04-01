package com.example.android.muniappbeta.forms;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.muniappbeta.R;
import com.example.android.muniappbeta.adapters.TablaAdapter;
import com.example.android.muniappbeta.model.Defuncion;
import com.example.android.muniappbeta.model.Matrimonio;
import com.example.android.muniappbeta.model.Nacimiento;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistroEstadoCivilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroEstadoCivilFragment extends Fragment {

    /**WEB SERVICE STATIC STRINGS**/
    static final String URL="http://apps2.munihuacho.gob.pe:9090/ConsultaDeArchivosWS/ConsultarArchivos?WSDL";
    static final String NAMESPACE="http://Consultar/";
    /**Spinner**/
    private Spinner spinnerOpciones;
    /**Objetos**/
    EditText input_paternoReC;
    EditText input_maternoReC;
    EditText input_nombreReC;
    Button btnConsultarReC;
    /**TABLA DE DATOS DINAMICOS**/
    TableLayout tablaReg;
    TableRow filas;

    //Text Views Para Todos
    TextView tvApellidoPaterno;
    TextView tvApellidoMaterno;
    TextView tvNombres;
    TextView tvSexo;
    TextView tvFechaDeInscripcion;
    //Text Views para Nacimiento
    TextView tvFechaDeNacimiento;
    //Text Views Para Matrimonio
    TextView tvApellidoPaternoDon;
    TextView tvApellidoMaternoDon;
    TextView tvNombresDon;
    TextView tvFechaMatrimonio;

    //Text Views Para Defuncion
    TextView tvFechaDefuncion;

//

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public RegistroEstadoCivilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroEstadoCivilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroEstadoCivilFragment newInstance(String param1, String param2) {
        RegistroEstadoCivilFragment fragment = new RegistroEstadoCivilFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

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
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_registro_estado_civil, container, false);
        final TablaAdapter tablaAdapter=new TablaAdapter(getActivity(),(TableLayout)view.findViewById(R.id.tablaHeaderReC));
        addItemsOnSpinner(view);
        btnConsultarReC=(Button)view.findViewById(R.id.btnConsultarReC);
        input_paternoReC=(EditText) view.findViewById(R.id.input_paternoReC);
        input_maternoReC=(EditText)view.findViewById(R.id.input_maternoReC);
        input_nombreReC=(EditText)view.findViewById(R.id.input_nombreReC);
        tablaReg=(TableLayout)view.findViewById(R.id.tablaContentReC);
        btnConsultarReC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int res=0;
                //TODO: Restringir que por lo menos uno de los edittext no tenga menos de 3 caracteres
                //TODO: Validar que existe acceso a internet
                //En cada busqueda limpia los datos de la tabla de contenidos
                tablaReg.removeAllViews();
                if(spinnerOpciones.getSelectedItem().toString().equals("Nacimiento")){
                    Toast.makeText(getActivity(), "Partidas de nacimiento hasta 26/01/2012.", Toast.LENGTH_SHORT).show();
                    res=R.array.cabecera_nacimiento;
                   new SoapNacimiento().execute(input_paternoReC.getText().toString().trim(),
                           input_maternoReC.getText().toString().trim(),
                           input_nombreReC.getText().toString().trim());
                }
                if(spinnerOpciones.getSelectedItem().toString().equals("Matrimonio")){
                    Toast.makeText(getActivity(), "Partidas de matrimonio hasta 30/12/2010.", Toast.LENGTH_SHORT).show();
                    new SoapMatrimonio().execute(input_paternoReC.getText().toString().trim(),
                            input_maternoReC.getText().toString().trim(),
                            input_nombreReC.getText().toString().trim());
                    res=R.array.cabecera_matrimonio;
                }
                if(spinnerOpciones.getSelectedItem().toString().equals("Defuncion")){
                    Toast.makeText(getActivity(), "Partidas de defuncion hasta 31/12/2010.", Toast.LENGTH_SHORT).show();
                    new SoapDefuncion().execute(input_paternoReC.getText().toString().trim(),
                            input_maternoReC.getText().toString().trim(),
                            input_nombreReC.getText().toString().trim());
                    res=R.array.cabecera_defuncion;
                }
                if(res!=0){
                    if(tablaAdapter.getFilas()>0){
                       tablaAdapter.eliminarFila(0);
                    }

                    tablaAdapter.agregarCabecera(res);
                }

            }
        });
        return view;
    }

    /**LISTA DE ITEMS EN EL SPINNER**/
    public void addItemsOnSpinner(View view){
        spinnerOpciones=(Spinner)view.findViewById(R.id.spOpcionesReC);
        List<String> list=new ArrayList<String>();
        list.add("Nacimiento");
        list.add("Matrimonio");
        list.add("Defuncion");
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOpciones.setAdapter(dataAdapter);
    }

    //  ASYNCTASK METHODS
    private class SoapNacimiento extends AsyncTask<String,String,String>{
        static final String METHODNAME="consutarNacimiento";
        static final String SOAP_ACTION=NAMESPACE+METHODNAME;
        Nacimiento nacimiento;
        String problema=null;
        ArrayList<Nacimiento> listaNacidos=new ArrayList<Nacimiento>();
        @Override
        protected String doInBackground(String... params) {
            SoapObject request=new SoapObject(NAMESPACE,METHODNAME);
            request.addProperty("paterno",params[0]);
            request.addProperty("materno",params[1]);
            request.addProperty("nombres",params[2]);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte=new HttpTransportSE(URL);
           try {
               transporte.debug=true;
               transporte.call(SOAP_ACTION,envelope);
               SoapObject obj1=(SoapObject)envelope.bodyIn;
               for(int i=0;i<obj1.getPropertyCount();i++){
                   SoapObject obj2=(SoapObject)obj1.getProperty(i);
                   // TODO: Set the variables with propertys
                   nacimiento=new Nacimiento();
                   nacimiento.setPaterno(obj2.getProperty("paterno").toString());
                   nacimiento.setMaterno(obj2.getProperty("materno").toString());
                   nacimiento.setNombres(obj2.getProperty("nombres").toString());
                   nacimiento.setSexo(obj2.getProperty("sexo").toString());
                   nacimiento.setFechanac(obj2.getProperty("fechanac").toString());
                   //Fecha Inscripcion
                   nacimiento.setDiainscrip(obj2.getProperty("diainscrip").toString());
                   nacimiento.setMesinscrip(obj2.getProperty("mesinscrip").toString());
                   nacimiento.setAñoinscrip(obj2.getProperty("añoinscrip").toString());
                   //Concatenacion de fecha de inscripcion
                   nacimiento.setFecha_inscripcion(nacimiento.getDiainscrip()+"/"+
                           nacimiento.getMesinscrip()+"/"+nacimiento.getAñoinscrip());
                   //End de concatenacion
                    listaNacidos.add(nacimiento);
               }

           }catch (Exception ex){
               Toast.makeText(getActivity(), "Problemas con la conexion", Toast.LENGTH_SHORT).show();
               problema="No records found";
           }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (!listaNacidos.isEmpty()){
                addDataRows(listaNacidos);
            }else{
            //HANDLE NOT FOUND DATA

            }
            super.onPostExecute(s);

        }
    }
    private class SoapMatrimonio extends AsyncTask<String,String,String>{
        static final String METHODNAME="consutarMatrimonio";
        static final String SOAP_ACTION=NAMESPACE+METHODNAME;
        Matrimonio matrimonio;
        String problema=null;
        ArrayList<Matrimonio> listaCasados=new ArrayList<Matrimonio>();
        @Override
        protected String doInBackground(String... params) {
            SoapObject request=new SoapObject(NAMESPACE,METHODNAME);
            request.addProperty("paterno",params[0]);
            request.addProperty("materno",params[1]);
            request.addProperty("nombres",params[2]);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte=new HttpTransportSE(URL);
            try {
                transporte.debug=true;
                transporte.call(SOAP_ACTION,envelope);
                SoapObject obj1=(SoapObject)envelope.bodyIn;
                for(int i=0;i<obj1.getPropertyCount();i++){
                    SoapObject obj2=(SoapObject)obj1.getProperty(i);
                    matrimonio=new Matrimonio();
                    //Hombre
                    matrimonio.setPaterno(obj2.getProperty("paterno").toString());
                    matrimonio.setMaterno(obj2.getProperty("materno").toString());
                    matrimonio.setNombres(obj2.getProperty("nombres").toString());
                    //Mujer
                    matrimonio.setPaternodon(obj2.getProperty("paternodon").toString());
                    matrimonio.setMaternodon(obj2.getProperty("maternodon").toString());
                    matrimonio.setNombresdon(obj2.getProperty("nombresdon").toString());
                    //fecha
                    matrimonio.setFechamat(obj2.getProperty("fechamat").toString());
                    matrimonio.setDiainscrip(obj2.getProperty("diainscrip").toString());
                    matrimonio.setMesinscrip(obj2.getProperty("mesinscrip").toString());
                    matrimonio.setAñoinscrip(obj2.getProperty("añoinscrip").toString());
                    //concatenando fecha de inscripcion
                    matrimonio.setFecha(matrimonio.getDiainscrip()+"/"+
                            matrimonio.getMesinscrip()+"/"+
                            matrimonio.getAñoinscrip());
                    listaCasados.add(matrimonio);
                }

            }catch (Exception ex){
                // TODO: handle connection timeout exceptions
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(!listaCasados.isEmpty()){
                addDataRows(listaCasados);
            }else{

            }
            super.onPostExecute(s);
        }
    }
    private class SoapDefuncion extends  AsyncTask<String,String,String>{
        static final String METHODNAME="consutarDefuncion";
        static final String SOAP_ACTION=NAMESPACE+METHODNAME;
        Defuncion defuncion;
        String problema=null;
        ArrayList<Defuncion> listaMuertos=new ArrayList<Defuncion>();
        @Override
        protected String doInBackground(String... params) {
            SoapObject request=new SoapObject(NAMESPACE,METHODNAME);
            request.addProperty("paterno",params[0]);
            request.addProperty("materno",params[1]);
            request.addProperty("nombres",params[2]);
            SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet=false;
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte=new HttpTransportSE(URL);
            try {
                transporte.debug=true;
                transporte.call(SOAP_ACTION,envelope);
                SoapObject obj1=(SoapObject)envelope.bodyIn;
                for(int i=0;i<obj1.getPropertyCount();i++){
                    SoapObject obj2=(SoapObject)obj1.getProperty(i);
                    defuncion=new Defuncion();
                    defuncion.setPaterno(obj2.getProperty("paterno").toString());
                    defuncion.setMaterno(obj2.getProperty("materno").toString());
                    defuncion.setNombres(obj2.getProperty("nombres").toString());
                    defuncion.setSexo(obj2.getProperty("sexo").toString());
                    defuncion.setFechadef(obj2.getProperty("fechadef").toString());
                    defuncion.setDiainscrip(obj2.getProperty("diainscrip").toString());
                    defuncion.setMesinscrip(obj2.getProperty("mesinscrip").toString());
                    defuncion.setAñoinscrip(obj2.getProperty("añoinscrip").toString());
                    defuncion.setFecha(defuncion.getDiainscrip()+
                            "/"+defuncion.getMesinscrip()+
                            "/"+defuncion.getAñoinscrip());
                    listaMuertos.add(defuncion);
                }

            }catch (Exception ex){
                // TODO: handle connection timeout exceptions
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(!listaMuertos.isEmpty()){
                addDataRows(listaMuertos);
            }
            else{
                //handle not found data
            }
            super.onPostExecute(s);
        }
    }

    public void addDataRows(ArrayList lista) {
        /**NACIMIENTO ROWS PLUS CONFIGS**/
        if (spinnerOpciones.getSelectedItem().toString().equals("Nacimiento")) {
            for (Iterator iterator = lista.iterator(); iterator.hasNext(); ) {
                Object next = iterator.next();
                Nacimiento nac = (Nacimiento) next;
                filas = new TableRow(getContext());
                tvApellidoPaterno = new TextView(getContext());
                tvApellidoMaterno = new TextView(getContext());
                tvNombres = new TextView(getContext());
                tvSexo = new TextView(getContext());
                tvFechaDeNacimiento = new TextView(getContext());
                tvFechaDeInscripcion = new TextView(getContext());
                filas.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.
                       MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

                TextView[] textos={tvApellidoPaterno,
                        tvApellidoMaterno,
                        tvNombres,
                        tvSexo,
                        tvFechaDeNacimiento,
                        tvFechaDeInscripcion};

                configurarTextView(textos);
                tvApellidoPaterno.setText(nac.getPaterno());
                tvApellidoMaterno.setText(nac.getMaterno());
                tvNombres.setText(nac.getNombres());
                tvSexo.setText(nac.getSexo());
                tvFechaDeNacimiento.setText(nac.getFechanac());
                tvFechaDeInscripcion.setText(nac.getFecha_inscripcion());
                /**Definiendo los campos de la fila**/
                addTextViewsToRow(textos,filas);
                tablaReg.addView(filas, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT));
            }

        }
        /**MATRIMONIO ROWS PLUS CONFIGS**/
        if(spinnerOpciones.getSelectedItem().toString().equals("Matrimonio")){
            for (Iterator iterator = lista.iterator(); iterator.hasNext(); ) {
                Object next=iterator.next();
                Matrimonio mat=(Matrimonio)next ;
                filas = new TableRow(getContext());
                tvApellidoPaterno=new TextView(getContext());
                tvApellidoMaterno=new TextView(getContext());
                tvNombres=new TextView(getContext());
                tvApellidoPaternoDon=new TextView(getContext());
                tvApellidoMaternoDon=new TextView(getContext());
                tvNombresDon=new TextView(getContext());
                tvFechaMatrimonio=new TextView(getContext());
                tvFechaDeInscripcion=new TextView(getContext());

                TextView[] textos={tvApellidoPaterno,tvApellidoMaterno,
                        tvNombres,tvApellidoPaternoDon,
                        tvApellidoMaternoDon,tvNombresDon,
                        tvFechaMatrimonio,tvFechaDeInscripcion};
                configurarTextView(textos);
                tvApellidoPaterno.setText(mat.getPaterno());
                tvApellidoMaterno.setText(mat.getMaterno());
                tvNombres.setText(mat.getNombres());
                tvApellidoPaternoDon.setText(mat.getPaternodon());
                tvApellidoMaternoDon.setText(mat.getMaternodon());
                tvNombresDon.setText(mat.getNombresdon());
                tvFechaMatrimonio.setText(mat.getFechamat());
                tvFechaDeInscripcion.setText(mat.getFecha());
                addTextViewsToRow(textos,filas);
                tablaReg.addView(filas, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }
        /**DEFUNCION ROWS PLUS CONFIG**/
        if(spinnerOpciones.getSelectedItem().toString().equals("Defuncion")){
            for (Iterator iterator=lista.iterator();iterator.hasNext();){
                Object next=iterator.next();
                Defuncion def=(Defuncion) next ;
                filas = new TableRow(getContext());
                tvApellidoPaterno=new TextView(getContext());
                tvApellidoMaterno=new TextView(getContext());
                tvNombres=new TextView(getContext());
                tvSexo=new TextView(getContext());
                tvFechaDefuncion=new TextView(getContext());
                tvFechaDeInscripcion=new TextView(getContext());

                TextView[] textos={tvApellidoPaterno,
                        tvApellidoMaterno,
                        tvNombres,
                        tvSexo,
                        tvFechaDefuncion,
                        tvFechaDeInscripcion};
                configurarTextView(textos);
                tvApellidoPaterno.setText(def.getPaterno());
                tvApellidoMaterno.setText(def.getMaterno());
                tvNombres.setText(def.getNombres());
                tvSexo.setText(def.getSexo());
                tvFechaDefuncion.setText(def.getFechadef());
                tvFechaDeInscripcion.setText(def.getFecha());

                addTextViewsToRow(textos,filas);
                tablaReg.addView(filas, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }
    }
    /**CONFIGURACIONES DE LOS TEXT VIEWS PARA TODOS LOS CAMPOS DE LA TABLA DE DATOS**/
    public void configurarTextView(TextView[] texto){
        for (int i=0;i<texto.length;i++){
          texto[i].setWidth(200);
          //  texto[i].setHeight(80);
            texto[i].setPadding(20, 0, 0, 0);
            texto[i].setGravity(Gravity.CENTER_VERTICAL);
            texto[i].setBackgroundResource(R.drawable.table_rows);
            texto[i].setAllCaps(true);
        }
    }
    /**AGREGA LOS CAMPOS DE TEXTO CON SUS DATOS CORRESPONDIENTES A LA FILA**/
    public void addTextViewsToRow(TextView[] texto,TableRow row){
        for (int i=0;i<texto.length;i++){
            row.addView(texto[i]);
        }
    }

    /**METODO PARA REVISAR LA CONEXION A INTERNET**/
    public boolean isNetDisponible(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo!=null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
