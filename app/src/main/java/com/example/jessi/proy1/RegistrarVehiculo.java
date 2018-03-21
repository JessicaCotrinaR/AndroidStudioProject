package com.example.jessi.proy1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class RegistrarVehiculo extends ActionBarActivity {

    EditText txtMarca,txtModelo,txtTipo,txtAnio,txtColor,txtEstado,txtPlaca,txtIdMante;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_vehiculo);

        txtMarca=(EditText) findViewById(R.id.txtMarca);
        txtModelo=(EditText)findViewById(R.id.txtModelo);
        txtTipo=(EditText) findViewById(R.id.txtTipo);
        txtAnio=(EditText) findViewById(R.id.txtAnio);
        txtColor=(EditText) findViewById(R.id.txtColor);
        txtEstado=(EditText) findViewById(R.id.txtEstado);
        txtPlaca=(EditText) findViewById(R.id.txtPlaca);
        txtIdMante=(EditText) findViewById(R.id.txtIdMantenimiento);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registrar_vehiculo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void registrarVehiculoCel(View v){

        try{

            Thread nt = new Thread(){
                String res;
                @Override
                public void run(){
                    String NameSpace = "http://tempuri.org/";
                    String URL = "http://192.95.38.126/WSProy/WebSMantenimiento.asmx";
                    String MethodName = "RegistrarVehiculoV";
                    String SoapAction = "http://tempuri.org/RegistrarVehiculoV";

                    SoapObject request  = new SoapObject(NameSpace, MethodName);
                    request.addProperty("txtmarca", txtMarca.getText().toString());
                    request.addProperty("txtmodelo", txtModelo.getText().toString());
                    request.addProperty("txttipo", txtTipo.getText().toString());
                    request.addProperty("txtanio", txtAnio.getText().toString());
                    request.addProperty("txtcolor", txtColor.getText().toString());
                    request.addProperty("txtestado", txtEstado.getText().toString());
                    request.addProperty("txtplaca", txtPlaca.getText().toString());
                    request.addProperty("txtidMantenimiento",txtIdMante.getText().toString());


                    //request.addProperty("idUsuario", 1);

                    SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(
                            SoapEnvelope.VER11);
                    sobre.dotNet = true;
                    sobre.setOutputSoapObject(request);

                    HttpTransportSE transporte = new HttpTransportSE(URL);
                    try {
                        transporte.call(SoapAction, sobre);
                        SoapPrimitive resultado = (SoapPrimitive)sobre.getResponse();
                        res = resultado.toString();
                    } catch (HttpResponseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (XmlPullParserException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        public void run() {
                            if(Integer.parseInt(res)==1){

                                Toast.makeText(RegistrarVehiculo.this, "Se inserto correctamente",
                                        Toast.LENGTH_LONG).show();
                                Intent x = new Intent(RegistrarVehiculo.this, ListarVehiculo.class);
                                startActivity(x);
                            }
                            else if(Integer.parseInt(res)==-2){
                                Toast.makeText(RegistrarVehiculo.this, "Usuario ya existe",
                                        Toast.LENGTH_LONG).show();
                            }
						/*	else{
								Toast.makeText(LogueoActividad.this, "Usuario o password no valido",
												Toast.LENGTH_LONG).show();
							}*/
                        }
                    });
                }
            };
            nt.start();
        }catch(Exception e){
            Toast.makeText(this, e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}
