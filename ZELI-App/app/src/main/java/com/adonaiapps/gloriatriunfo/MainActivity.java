package com.adonaiapps.gloriatriunfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;



public class MainActivity extends ActionBarActivity {


    private Context context = this;
    private EditText met_buscar;
    private ListView lista;
    public static DrawerLayout drawerLayout;
    private ArrayList<itemsMenu> array;
    private FrameLayout contenedor_principal;
    private ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        configurarObjetos();

        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.mipmap.ic_more,
                R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu();
            }

        };

        drawerLayout.setDrawerListener(toggle);


    }

    public void configurarObjetos() {

        contenedor_principal = (FrameLayout) findViewById(R.id.contenedor_principal);
        lista = (ListView) findViewById(R.id.drawer);
        //et_buscar = (EditText) findViewById(R.id.et_buscar); //buscador en el listview

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //le ponemos el header
        View header = getLayoutInflater().inflate(R.layout.header_menu, null);
        lista.addHeaderView(header, null, false);

        //Lista de objetos
        array = new ArrayList<itemsMenu>();
        llenarListaMenu(array);

        //ADAPTER
        final MenuAdapter adaptador = new MenuAdapter(this, array);
        lista.setAdapter(adaptador);

        lista.setTextFilterEnabled(true);  //habilitador filtrado


        //PARA QUE SEA POR DEFECTO EL FRAGMENT1
        seleccionarItem(1);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                seleccionarItem(position);
            }

        });

    }

    //SELECCION DE FRAGMENT
    @SuppressLint("ResourceAsColor")
    public void seleccionarItem(int posicion) {
        Fragment fragment = null;
        boolean salida = false;
        switch (posicion) {
            // BOTON PLAY, PRIMERA POSICION = 0
            case 1:
                fragment = new himno0_fg();
                break;
            case 2:
                fragment = new himno1_fg();
                break;
            case 3:
                fragment = new himno2_fg();
                break;
            case 4:
                fragment = new himno3_fg();
                break;
            case 5:
                fragment = new himno4_fg();
                break;
            //case 6:
              //  fragment = new himno5_fg();
              //  break;
            case 6:
                salida = true;
                //MARCAMOS EL CLICKADO
                lista.setItemChecked(posicion, true);
                drawerLayout.closeDrawer(lista);
                finish();
        }
        //SI SE SALE DE LA APLICACION, AL HACER EL CAMBIO DE FRAGMENT DARIA NULLPOINTEREXCEPTION
        if (salida == false) {
            //HACEMOS USO DE LA LIBRERIA
            FragmentManager fragmentManager = getSupportFragmentManager();
            //REEMPLAZAMOS
            fragmentManager.beginTransaction().replace(R.id.contenedor_principal, fragment).commit();
            //MARCAMOS EL CLICKADO
            lista.setItemChecked(posicion, true);
            drawerLayout.closeDrawer(lista);
        }
    }

    //LLENAR LISTA DE OBJETOS DEL MENU
    public void llenarListaMenu(ArrayList<itemsMenu> arraydir) {
        itemsMenu item;
        // Introduzco los elementos
        item = new itemsMenu(1, "Start", "Inicio", getResources().getDrawable(R.mipmap.ic_lanzador));
        arraydir.add(item);
        item = new itemsMenu(2, "The Pets", "Los Animales", getResources().getDrawable(R.mipmap.ic_pata));
        arraydir.add(item);
        item = new itemsMenu(3, "The Colors", "Los Colores", getResources().getDrawable(R.mipmap.ic_color));
        arraydir.add(item);
        item = new itemsMenu(4, "The Numbers", "Los Numeros", getResources().getDrawable(R.mipmap.ic_numero));
        arraydir.add(item);
        item = new itemsMenu(5, "Things", "Cosas", getResources().getDrawable(R.mipmap.ic_cosas));
        arraydir.add(item);
        //item = new itemsMenu(6, "------------", "------------", getResources().getDrawable(R.mipmap.ic_launcher));
       // arraydir.add(item);
        item = new itemsMenu(6, "Close", "Cerrar", getResources().getDrawable(R.mipmap.ic_cerrar));
        arraydir.add(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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

        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Active the toggle with the icon
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }


    //-------------parte de sonidos----------------------------------------
    //---------------animales----------------------------------------------
    MediaPlayer mp;

    public void destruir() {
        if (mp != null)
            mp.release();
    }

    public void presionPerros(View v) {
        mp = MediaPlayer.create(this, R.raw.sonido_perro);
        mp.start();
    }

    public void animal_ballena(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.animal_ballena);
        mp.start();

    }

    public void animal_caballo(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.animal_caballo);
        mp.start();
    }

    public void animal_cerdo(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.animal_cerdo);
        mp.start();
    }

    public void animal_chango(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.animal_chango);
        mp.start();
    }

    public void animal_gallina(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.animal_gallina);
        mp.start();
    }

    public void animal_gallo(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.animal_gallo);
        mp.start();
    }

    public void animal_gato(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.animal_gato);
        mp.start();
    }

    public void animal_oveja(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.animal_oveja);
        mp.start();
    }

    public void animal_pajaro(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.animal_pajaro);
        mp.start();
    }

    public void animal_perro(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.animal_perro);
        mp.start();
    }

    public void animal_rana(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.animal_rana);
        mp.start();
    }

    public void animal_vaca(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.animal_vaca);
        mp.start();
    }

    //---------------colores----------------------------------------------
    public void color_amarillo(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.color_amarillo);
        mp.start();
    }

    public void color_azul(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.color_azul);
        mp.start();
    }

    public void color_blanco(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.color_blanco);
        mp.start();
    }

    public void color_cafe(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.color_cafe);
        mp.start();
    }

    public void color_negro(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.color_negro);
        mp.start();
    }

    public void color_rojo(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.color_rojo);
        mp.start();
    }

    public void color_rosa(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.color_rosa);
        mp.start();
    }

    public void color_verde(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.color_verde);
        mp.start();
    }

    //---------------numeros----------------------------------------------
    public void num_1(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_1);
        mp.start();

    }
    public void num_2(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_2);
        mp.start();

    }
    public void num_3(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_3);
        mp.start();

    }
    public void num_4(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_4);
        mp.start();

    }
    public void num_5(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_5);
        mp.start();

    }
    public void num_6(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_6);
        mp.start();

    }
    public void num_7(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_7);
        mp.start();

    }
    public void num_8(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_8);
        mp.start();

    }
    public void num_9(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_9);
        mp.start();

    }
    public void num_10(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_10);
        mp.start();

    }
    public void num_11(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_11);
        mp.start();

    }
    public void num_12(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_12);
        mp.start();

    }
    public void num_13(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_13);
        mp.start();

    }
    public void num_14(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_14);
        mp.start();

    }
    public void num_15(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_15);
        mp.start();

    }
    public void num_16(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_16);
        mp.start();

    }
    public void num_17(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_17);
        mp.start();

    }
    public void num_18(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_18);
        mp.start();

    }
    public void num_19(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_19);
        mp.start();

    }
    public void num_20(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.numero_20);
        mp.start();

    }
    //---------------cosas----------------------------------------------
    public void cosas_ambulancia(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.cosas_ambulancia);
        mp.start();

    }
    public void cosas_autobomba_firetruck(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.cosas_autobomba_firetruck);
        mp.start();

    }
    public void cosas_camiondebasura(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.cosas_camiondebasura);
        mp.start();

    }
    public void cosas_chocolate(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.cosas_chocolate);
        mp.start();

    }
    public void cosas_cochedepolicia(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.cosas_cochedepolicia);
        mp.start();

    }
    public void cosas_helado(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.cosas_helado);
        mp.start();

    }
    public void cosas_helicoptero(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.cosas_helicoptero);
        mp.start();

    }
    public void cosas_parquedejuegos(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.cosas_parquedejuegos);
        mp.start();

    }
    public void cosas_telefono(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.cosas_telefono);
        mp.start();

    }
    public void cosas_tortadecumpleanos(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.cosas_tortadecumpleanos);
        mp.start();

    }
    public void cosas_trampolin(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.cosas_trampolin);
        mp.start();

    }
    public void cosas_tren(View v) {
        destruir();
        mp = MediaPlayer.create(this, R.raw.cosas_tren);
        mp.start();

    }
}
