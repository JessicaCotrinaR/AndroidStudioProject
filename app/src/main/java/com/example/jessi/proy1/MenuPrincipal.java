package com.example.jessi.proy1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MenuPrincipal extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_principal, menu);
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

    public void MiRuta(View view){


        Intent x = new Intent(MenuPrincipal.this, MiZona.class);
        startActivity(x);

    }

    public void MiServicios(View view){


        Intent x = new Intent(MenuPrincipal.this, ServiciosCercanos.class);
        startActivity(x);

    }
    public void MantenimientoV(View view){


        Intent x = new Intent(MenuPrincipal.this,Mantenimiento.class);
        startActivity(x);

    }

    public void CalendarioPro(View view){

        Intent x = new Intent(MenuPrincipal.this,CalendarioPrograma.class);
        startActivity(x);

    }

    public void VehiculoMenu(View view){

        Intent x = new Intent(MenuPrincipal.this,MenuVehiculo.class);
        startActivity(x);

    }

}
