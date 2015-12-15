package br.com.livroandroid.carros.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.domain.Carro;
import br.com.livroandroid.carros.fragments.CarroFragment;

public class CarroActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_carro);
        // Configura a Toolbar como a action bar
        setUpToolbar();
        // Título da Toolbar e botão up navigation
        Carro c = (Carro) getIntent().getParcelableExtra("carro");
        getSupportActionBar().setTitle(c.nome);
        // Liga o botão up navigation para voltar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Imagem de cabeçalho na AppBar
        ImageView appBarImg = (ImageView) findViewById(R.id.appBarImg);
        Picasso.with(getContext()).load(c.urlFoto).into(appBarImg);
        if (b == null) {
            // Cria o fragment com o mesmo Bundle (args) da intent
            CarroFragment frag = new CarroFragment();
            frag.setArguments(getIntent().getExtras());
            // Adiciona o fragment no layout
            getSupportFragmentManager().beginTransaction().add(R.id.CarroFragment, frag).commit();
        }
    }
}
