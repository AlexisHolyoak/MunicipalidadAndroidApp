package pe.gob.munihuacho.municipalidadhuacho.forms;


import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Iterator;

import pe.gob.munihuacho.municipalidadhuacho.R;
import pe.gob.munihuacho.municipalidadhuacho.adapters.AsynctaskWithDelayedIndeterminateProgress;
import pe.gob.munihuacho.municipalidadhuacho.adapters.TablaAdapter;
import pe.gob.munihuacho.municipalidadhuacho.model.Papeleta;
import pe.gob.munihuacho.municipalidadhuacho.util.CheckNetwork;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsultaPapeletasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultaPapeletasFragment extends Fragment {
    EditText inputDni_Papeleta;
    Button btnBuscarCP;
    TablaAdapter tablaAdapter;

    /**WEB SERVICE*/
    static final String URL="http://apps2.munihuacho.gob.pe:9090/ConsultaDePapeletasWS/ConsultarPapeletas?WSDL";
    static final String NAMESPACE="http://Consultar/";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    TableLayout tablaReg;
    TableRow filas;
    /**ROW ELEMENTS**/
    TextView dni,fechaInfraccion,numPapeleta,tipoInfraccion,placa,monto,estado;


    public ConsultaPapeletasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultaPapeletasFragment.
     */

    public static ConsultaPapeletasFragment newInstance(String param1, String param2) {
        ConsultaPapeletasFragment fragment = new ConsultaPapeletasFragment();
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
        View view=inflater.inflate(R.layout.fragment_consulta_papeletas, container, false);
        inputDni_Papeleta=(EditText)view.findViewById(R.id.inputDni_Papeleta);
        btnBuscarCP=(Button)view.findViewById(R.id.btnBuscarCP);
        tablaAdapter=new TablaAdapter(getActivity(),(TableLayout)view.findViewById(R.id.tablaHeaderCP));
        tablaReg=(TableLayout)view.findViewById(R.id.tablaContentCP);
        btnBuscarCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputDni_Papeleta.getText().toString().trim().equals("")){
                    Toast.makeText(getActivity(), "Por favor ingrese un ", Toast.LENGTH_SHORT).show();


                }else{
                    int res=0;
                    tablaReg.removeAllViews();
                    new SoapAction(getActivity()).execute(inputDni_Papeleta.getText().toString().trim());
                    res=R.array.cabecera_papeletas;
                    if (res!=0){
                        if(tablaAdapter.getFilas()>0){
                            tablaAdapter.eliminarFila(0);
                        }
                        tablaAdapter.agregarCabeceraCP(res);
                    }

                }
            }
        });
        return view;
    }
    /**SOAP METHOD FOR CONSULTA PAPELETAS**/
    private class SoapAction extends AsynctaskWithDelayedIndeterminateProgress<String,Integer,String>
    {
        static final String METHODNAME="consultaDePapeleta";
        static final String SOAP_ACTION=NAMESPACE+METHODNAME;
        Papeleta papeleta;
        ArrayList<Papeleta> listPapeleta=new ArrayList<Papeleta>();
        String notFoundMessage="";
        String notEthernetConnection="";
        protected SoapAction(Activity activity) {
            super(activity);
        }

        @Override
        protected String doInBackground(String... params) {
            if (new CheckNetwork(getActivity()).isNetworkAvailable()){
                SoapObject request=new SoapObject(NAMESPACE,METHODNAME);
                request.addProperty("busqueda",params[0]);
                SoapSerializationEnvelope envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=false;
                envelope.setOutputSoapObject(request);
                HttpTransportSE transporte=new HttpTransportSE(URL);
                try {
                    transporte.debug=true;
                    transporte.call(SOAP_ACTION,envelope);
                    SoapObject obj1=(SoapObject)envelope.bodyIn;
                    if (obj1.getPropertyCount()>0){
                        for (int i=0;i<obj1.getPropertyCount();i++){
                            SoapObject obj2=(SoapObject)obj1.getProperty(i);
                            papeleta=new Papeleta();
                            papeleta.setDni(obj2.getProperty("dni").toString());
                            papeleta.setFechaInfraccion(obj2.getProperty("fechaInfraccion").toString());
                            papeleta.setNume_pape(obj2.getProperty("nume_pape").toString());
                            papeleta.setCodi_inte(obj2.getProperty("codi_inte").toString());
                            papeleta.setPlac_dttr(obj2.getProperty("plac_dttr").toString());
                            papeleta.setTota_pago(Double.parseDouble(obj2.getProperty("tota_pago").toString()));
                            papeleta.setDesc_esta(obj2.getProperty("desc_esta").toString());
                            listPapeleta.add(papeleta);}
                    }else {
                        //TODO: HANDLE NOT FOUND STRING
                        notFoundMessage="No se encontro ningun registro";
                    }
                }catch (Exception ex){
                    // TODO: HANDLE TIMEOUT EXCEPTION
                    notFoundMessage="No se encontro ningun registro";
                    Log.d("error", ex.getMessage());
                }
            }else{
                notEthernetConnection="No hay conexion a internet";
            }
            return null;
        }

        @Override
        protected void onPostExecute(String children) {
            if(notEthernetConnection!=""){
                super.onPostExecute(children);
                Toast.makeText(getActivity(), notEthernetConnection, Toast.LENGTH_SHORT).show();
            }
            if (!listPapeleta.isEmpty()){
                super.onPostExecute(children);
                addDataRow(listPapeleta);
            }
            if (notFoundMessage!=""){
                Toast.makeText(getActivity(), notFoundMessage, Toast.LENGTH_SHORT).show();
                super.onPostExecute(children);
            }

        }
    }
    /**AGREGAR DATOS A LA FILA**/
    public void addDataRow(ArrayList list){
        for (Iterator iterator = list.iterator(); iterator.hasNext();){
            Object next=iterator.next();
            Papeleta pap=(Papeleta)next;
            filas=new TableRow(getContext());
            dni= new TextView(getContext());
            fechaInfraccion= new TextView(getContext());
            numPapeleta=new TextView(getContext());
            tipoInfraccion= new TextView(getContext());
            placa = new TextView(getContext());
            monto= new TextView(getContext());
            estado=new TextView(getContext());

            filas.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.
                    MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

            TextView[] data={dni,
                    fechaInfraccion,
                    numPapeleta,
                    tipoInfraccion,
                    placa,
                    monto,
                    estado};

            configurarTextViews(data);
            dni.setText(pap.getDni());
            fechaInfraccion.setText(pap.getFechaInfraccion());
            numPapeleta.setText(pap.getNume_pape());
            tipoInfraccion.setText(pap.getCodi_inte());
            placa.setText(pap.getPlac_dttr());
            monto.setText(String.valueOf(pap.getTota_pago()));
            estado.setText(pap.getDesc_esta());
            estado.setTextColor(getResources().getColor(R.color.azul));
            addTextViewsToRow(data,filas);
            tablaReg.addView(filas, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
    /**CONFIGURACIONES PARA LOS ELEMENTOS DE LA FILA**/
    public  void configurarTextViews(TextView[] textViews){

        for (int i=0;i<textViews.length;i++){
            textViews[i].setWidth(100);
            textViews[i].setPadding(20, 0, 0, 0);
            textViews[i].setGravity(Gravity.CENTER_VERTICAL);
            textViews[i].setBackgroundResource(R.drawable.table_rows);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                textViews[i].setAllCaps(true);
            }
        }
    }
    public void addTextViewsToRow(TextView[] textViews,TableRow row){
        for (int i=0;i<textViews.length;i++){
            row.addView(textViews[i]);
        }
    }

}
