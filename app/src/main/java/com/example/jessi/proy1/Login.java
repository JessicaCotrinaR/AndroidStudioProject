package com.example.jessi.proy1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class Login extends ActionBarActivity {


   EditText textUsuario, txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textUsuario= (EditText) findViewById(R.id.txtUsuario);
        txtPassword=(EditText) findViewById(R.id.txtPassword);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void login(View v){

        try{
            if(textUsuario.getText().toString().equals("")){
                Toast.makeText(this, "Debe ingresar Usuario",
                        Toast.LENGTH_LONG).show();
                return;
            }
            if(txtPassword.getText().toString().equals("")){
                Toast.makeText(this, "Debe ingresar su Password",
                        Toast.LENGTH_LONG).show();
                return;
            }

            Thread nt = new Thread(){
                int res;
                @Override
                public void run(){
                    String NameSpace = "http://tempuri.org/";
                    String URL = "http://192.95.38.126/WSProy/WebSMantenimiento.asmx";
                    String MethodName = "Login";
                    String SoapAction = "http://tempuri.org/Login";

                    SoapObject request  = new SoapObject(NameSpace, MethodName);
                    request.addProperty("Usuario",textUsuario.getText().toString());
                    request.addProperty("Password", txtPassword.getText().toString());


                    SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(
                            SoapEnvelope.VER11);
                    sobre.dotNet = true;
                    sobre.setOutputSoapObject(request);

                    HttpTransportSE transporte = new HttpTransportSE(URL);
                    try {
                        transporte.call(SoapAction, sobre);
                        SoapObject resultado = (SoapObject)sobre.getResponse();

                        if(resultado!=null){
                            res = resultado.getPropertyCount();
                        }
                        else{
                            res=0;
                        }
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

                            if(res>0){
                                Intent x = new Intent(Login.this, MenuPrincipal.class);
                                startActivity(x);
                            }
                            else{

                                Toast.makeText(Login.this, "Usuario o password incorrectos",
                                       Toast.LENGTH_LONG).show();
                               // Intent x = new Intent(Login.this, MenuPrincipal.class);
                               // startActivity(x);

                            }
                        }
                    });
                }
            };
            nt.start();
        }catch(Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }
}
