package pe.gob.munihuacho.municipalidadhuacho.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import pe.gob.munihuacho.municipalidadhuacho.MainActivity;
import pe.gob.munihuacho.municipalidadhuacho.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread hilo=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);

                }catch (InterruptedException ex){
                    Log.d("Error",ex.getMessage());
                }finally {
                    Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        };
        hilo.start();
    }
}
