package br.com.livroandroid.hellofragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Adiciona o fragment dinamicamente pela API
        if (savedInstanceState == null) {
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            HelloFragment frag1 = new HelloFragment();
            ft.add(R.id.layoutFrag, frag1, "HelloFragment");
            ft.commit();
        }
    }

    public void onClickHello(View view) {
        // Busca o fragment pela tag e chama o método hello()
        FragmentManager fm = getSupportFragmentManager();
        HelloFragment frag1 = (HelloFragment) fm.findFragmentByTag("HelloFragment");
        frag1.hello();
    }
}

