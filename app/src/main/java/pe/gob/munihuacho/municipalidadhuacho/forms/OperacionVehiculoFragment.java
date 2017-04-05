package pe.gob.munihuacho.municipalidadhuacho.forms;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import pe.gob.munihuacho.municipalidadhuacho.R;
import pe.gob.munihuacho.municipalidadhuacho.adapters.AsynctaskWithDelayedIndeterminateProgress;
import pe.gob.munihuacho.municipalidadhuacho.model.PermisoOperacion;
import pe.gob.munihuacho.municipalidadhuacho.util.CheckNetwork;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OperacionVehiculoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OperacionVehiculoFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    //TEXTVIEWS
    TextView tvPlacaOV,
            tvPadronOV,
            tvCertiOperOV,
            tvDniOV,
            tvApellidosOV,
            tvNombresOV,
            tvEmpresaOV,
            tvServicioOV,
            tvClaseOV,
            tvMotorOV,
            tvSerieOV,
            tvColorOV,
            tvFechaEmisionOV,
            tvFechaCaducidadOV;

    //BUTTONS
    Button btnBuscarOV;

    //EDITTEXTS
    EditText inputPlacaPadronOV;

    //WEBSERVICE
    static final String URL="http://apps2.munihuacho.gob.pe:9090/ConsultaDeOperacionDeVehiculoWS/ConsultarOperacionDeVehiculo?WSDL";
    static final String NAMESPACE="http://Consultar/";
    static final String METHODNAME="consultar";

    public OperacionVehiculoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OperacionVehiculoFragment.
     */
    public static OperacionVehiculoFragment newInstance(String param1, String param2) {
        OperacionVehiculoFragment fragment = new OperacionVehiculoFragment();
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
        View view=inflater.inflate(R.layout.fragment_operacion_vehiculo, container, false);
        tvPlacaOV=(TextView)view.findViewById(R.id.tvPlacaOV);
        tvPadronOV=(TextView)view.findViewById(R.id.tvPadronOV);
        tvCertiOperOV=(TextView)view.findViewById(R.id.tvCertiOperOV);
        tvDniOV=(TextView)view.findViewById(R.id.tvDniOV);
        tvApellidosOV=(TextView)view.findViewById(R.id.tvApellidosOV);
        tvNombresOV=(TextView)view.findViewById(R.id.tvNombresOV);
        tvEmpresaOV=(TextView)view.findViewById(R.id.tvEmpresaOV);
        tvServicioOV=(TextView)view.findViewById(R.id.tvServicioOV);
        tvClaseOV=(TextView)view.findViewById(R.id.tvClaseOV);
        tvMotorOV=(TextView)view.findViewById(R.id.tvMotorOV);
        tvSerieOV=(TextView)view.findViewById(R.id.tvSerieOV);
        tvColorOV=(TextView)view.findViewById(R.id.tvColorOV);
        tvFechaEmisionOV=(TextView)view.findViewById(R.id.tvFechaEmisionOV);
        tvFechaCaducidadOV=(TextView)view.findViewById(R.id.tvFechaCaducidadOV);
        btnBuscarOV=(Button)view.findViewById(R.id.btnBuscarOV);
        inputPlacaPadronOV=(EditText)view.findViewById(R.id.inputPlacaPadronOV);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnBuscarOV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SoapAction(getActivity()).execute(inputPlacaPadronOV.getText().toString().trim());
            }
        });
    }
    /**SOAP METHOD**/
    private class SoapAction extends AsynctaskWithDelayedIndeterminateProgress<String,Integer,String>{
        static final String SOAP_ACTION=NAMESPACE+METHODNAME;
        PermisoOperacion permisoOperacion=new PermisoOperacion();
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
                HttpTransportSE transportSE=new HttpTransportSE(URL);
                try {
                    transportSE.debug=true;
                    transportSE.call(SOAP_ACTION,envelope);
                    SoapObject response=(SoapObject)envelope.getResponse();
                    if(response.getPropertyCount()>0){
                        permisoOperacion.setNumplaca(response.getProperty("numplaca").toString());
                        permisoOperacion.setNumpadron(response.getProperty("numpadron").toString());
                        permisoOperacion.setNumtarjeta(Integer.parseInt(response.getProperty("numtarjeta").toString()));
                        permisoOperacion.setPer_dni(response.getProperty("per_dni").toString());
                        permisoOperacion.setPer_apellidos(response.getProperty("per_apellidos").toString());
                        permisoOperacion.setPer_nombres(response.getProperty("per_nombres").toString());
                        permisoOperacion.setRazonsocial(response.getProperty("razonsocial").toString());
                        permisoOperacion.setTiposervicioempresa(response.getProperty("tiposervicioempresa").toString());
                        permisoOperacion.setClasevh(response.getProperty("clasevh").toString());
                        permisoOperacion.setNummotor(response.getProperty("nummotor").toString());
                        permisoOperacion.setNumserie(response.getProperty("numserie").toString());
                        permisoOperacion.setColor(response.getProperty("color").toString());
                        permisoOperacion.setFechaEmision(response.getProperty("fechaEmision").toString());
                        permisoOperacion.setFechaCaducidad(response.getProperty("fechaCaducidad").toString());
                    }else{
                        notFoundMessage="No se encontro el registro";
                    }
                }catch (Exception ex){
                    notFoundMessage="No se encontro el registro";
                }
            }else{
                notEthernetConnection="No existe conexion a internet";
            }
            return null;
        }

        @Override
        protected void onPostExecute(String children) {
            if (notEthernetConnection!=""){
                super.onPostExecute(children);
                Toast.makeText(getActivity(), notEthernetConnection, Toast.LENGTH_SHORT).show();
            }
            if (notFoundMessage.equals("")){
                super.onPostExecute(children);
                tvPlacaOV.setText(permisoOperacion.getNumplaca());
                tvPadronOV.setText(permisoOperacion.getNumpadron());
                tvCertiOperOV.setText(String.valueOf(permisoOperacion.getNumtarjeta()).trim());
                tvDniOV.setText(permisoOperacion.getPer_dni());
                tvApellidosOV.setText(permisoOperacion.getPer_apellidos());
                tvNombresOV.setText(permisoOperacion.getPer_nombres().trim());
                tvEmpresaOV.setText(permisoOperacion.getRazonsocial().trim());
                tvServicioOV.setText(permisoOperacion.getTiposervicioempresa().trim());
                tvClaseOV.setText(permisoOperacion.getClasevh().trim());
                tvMotorOV.setText(permisoOperacion.getNummotor().trim());
                tvSerieOV.setText(permisoOperacion.getNumserie().trim());
                tvColorOV.setText(permisoOperacion.getColor().trim());
                tvFechaEmisionOV.setText(permisoOperacion.getFechaEmision().trim());
                tvFechaCaducidadOV.setText(permisoOperacion.getFechaCaducidad().trim());
            }
            else{
                super.onPostExecute(children);
                Toast.makeText(getActivity(), notFoundMessage, Toast.LENGTH_SHORT).show();

            }
        }
    }
}
