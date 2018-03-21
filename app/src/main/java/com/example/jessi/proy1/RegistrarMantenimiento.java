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


public class RegistrarMantenimiento extends ActionBarActivity {

    EditText txtfecharealizada,txtfechaemision,txtestado,txttipomatenimiento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mantenimiento);

        txtfecharealizada=(EditText) findViewById(R.id.txtfecharealizada);
        txtfechaemision=(EditText) findViewById(R.id.txtfechaemision);
        txtestado=(EditText) findViewById(R.id.txtestadomantenimiento);
        txttipomatenimiento=(EditText) findViewById(R.id.txtidtipomante);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registrar_mantenimiento, menu);
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


    public void registrarMantenimientoCel(View v){

        try{

            Thread nt = new Thread(){
                String res;
                @Override
                public void run(){
                    String NameSpace = "http://tempuri.org/";
                    String URL = "http://192.95.38.126/WSProy/WebSMantenimiento.asmx";
                    String MethodName = "RegistrarMantenimiento";
                    String SoapAction = "http://tempuri.org/RegistrarMantenimiento";

                    SoapObject request  = new SoapObject(NameSpace, MethodName);
                    request.addProperty("txtfechaRealizada", txtfecharealizada.getText().toString());
                    request.addProperty("txtfechaEmision", txtfechaemision.getText().toString());
                    request.addProperty("txtestado", txtestado.getText().toString());
                    request.addProperty("txtIdTipoMantenimiento", txttipomatenimiento.getText().toString());



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

                                Toast.makeText(RegistrarMantenimiento.this, "Se inserto correctamente",
                                        Toast.LENGTH_LONG).show();
                                Intent x = new Intent(RegistrarMantenimiento.this, ListarMantenimiento.class);
                                startActivity(x);
                            }
                            else if(Integer.parseInt(res)==-2){
                                Toast.makeText(RegistrarMantenimiento.this, "Usuario ya existe",
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
