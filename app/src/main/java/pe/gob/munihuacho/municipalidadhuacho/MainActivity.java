package pe.gob.munihuacho.municipalidadhuacho;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pe.gob.munihuacho.municipalidadhuacho.forms.ConsultaPapeletasFragment;
import pe.gob.munihuacho.municipalidadhuacho.forms.OperacionVehiculoFragment;
import pe.gob.munihuacho.municipalidadhuacho.forms.RegistroEstadoCivilFragment;
import pe.gob.munihuacho.municipalidadhuacho.fragments.ConsultarPagoFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onStart() {
        //ESTO HACE QUE EL TECLADO NO DISTORSIONE CUANDO HAGA POP UP
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            }else{
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.back_button){
            if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            }else{
                super.onBackPressed();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment newFragment=null;
        if (id == R.id.nav_consultarpago) {
            newFragment= ConsultarPagoFragment.newInstance(String.valueOf(id),item.getTitle().toString());
            setTitle(item.getTitle());
        } else if (id == R.id.nav_operacionvehiculo) {
            newFragment= OperacionVehiculoFragment.newInstance(String.valueOf(id),item.getTitle().toString());
            setTitle(item.getTitle());

        } else if (id == R.id.nav_regyestadocivil) {
            newFragment= RegistroEstadoCivilFragment.newInstance(String.valueOf(id),item.getTitle().toString());
            setTitle(item.getTitle());

        } else if (id == R.id.nav_papeletas) {
            newFragment= ConsultaPapeletasFragment.newInstance(String.valueOf(id),item.getTitle().toString());
            setTitle(item.getTitle());
        }
        if(newFragment!=null){

            openFragment(newFragment);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**INICIO DE METODOS PARA CAMBIAR DE FRAGMENT **/
    private void openFragment(Fragment newFragment){
        Fragment containerFragment = getSupportFragmentManager().findFragmentById(R.id.content_main);
        if (containerFragment == null){
            addFragment(newFragment);
        } else{
            if (!containerFragment.getClass().getName().equalsIgnoreCase(newFragment.getClass().getName())) {
                replaceFragment(newFragment);
            }
        }

    }
    private void replaceFragment(Fragment newFragment){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.content_main,newFragment);
        ft.addToBackStack(newFragment.getClass().getName());
        ft.commit();
    }
    private void addFragment(Fragment newFragment) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.content_main, newFragment);
        ft.commit();
    }
    /**FINAL DE METODOS PARA CAMBIAR DE FRAGMENT**/
}
