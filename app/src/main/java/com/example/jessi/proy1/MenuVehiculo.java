package com.example.jessi.proy1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MenuVehiculo extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_vehiculo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_vehiculo, menu);
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


    public void RegistrarMantenimiento(View view){

        Intent x = new Intent(MenuVehiculo.this,RegistrarMantenimiento.class);
        startActivity(x);

    }
    public void ListarMantenimiento(View view){

        Intent x = new Intent(MenuVehiculo.this,ListarMantenimiento.class);
        startActivity(x);

    }
    public void ListarTipoMantenimiento(View view){

        Intent x = new Intent(MenuVehiculo.this,ListarTipoMantenimiento.class);
        startActivity(x);

    }
    public void ListarVoluntario(View view){

        Intent x = new Intent(MenuVehiculo.this,ListarVoluntario.class);
        startActivity(x);

    }
    public void ListarVehiculo(View view){

        Intent x = new Intent(MenuVehiculo.this,ListarVehiculo.class);
        startActivity(x);

    }
    public void RegistrarVehiculo(View view){

        Intent x = new Intent(MenuVehiculo.this,RegistrarVehiculo.class);
        startActivity(x);

    }



}
