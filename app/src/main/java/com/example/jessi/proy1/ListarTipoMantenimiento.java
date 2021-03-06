package com.example.jessi.proy1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class ListarTipoMantenimiento extends ActionBarActivity {
    private ListView lsvLista;
    private String[] lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_tipo_mantenimiento);
        ListadoTipoMantenimiento();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listar_tipo_mantenimiento, menu);
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

    private void ListadoTipoMantenimiento() {
        Thread th = new Thread(){
            @Override
            public void run(){
                String NameSpace = "http://tempuri.org/";
                String URL = "http://192.95.38.126/WSProy/WebSMantenimiento.asmx";
                String MethodName = "ListarTipoMantenimiento";
                String SoapAction = "http://tempuri.org/ListarTipoMantenimiento";

                SoapObject request = new SoapObject(NameSpace, MethodName);
                //request.addProperty("idusuario", 1);


                SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                sobre.dotNet=true;
                sobre.setOutputSoapObject(request);
                HttpTransportSE transporte = new HttpTransportSE(URL);
                try{
                    transporte.call(SoapAction, sobre);
                    SoapObject list = (SoapObject)sobre.getResponse();
                    lista = new String[list.getPropertyCount()];
                    for(int i=0; i<list.getPropertyCount();i++){
                        SoapObject result = (SoapObject)list.getProperty(i);
                        lista[i] = result.getPropertyAsString(1).toString();
                        lista[i] = result.getPropertyAsString(2).toString();
                    }
                }catch(Exception e){
                }

                runOnUiThread(new Runnable(){
                    public void run(){
                        lsvLista = (ListView)findViewById(R.id.lsTipoMantenimiento);
                        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                                ListarTipoMantenimiento.this,

                                android.R.layout.simple_list_item_1, lista);
                        lsvLista.setAdapter(adaptador);
                    }
                });
            }
        };
        th.start();
    }
}
