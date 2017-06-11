package br.com.livroandroid.carros.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.util.List;

import br.com.livroandroid.carros.CarrosApplication;
import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.activity.CarroActivity;
import br.com.livroandroid.carros.adapter.CarroAdapter;
import br.com.livroandroid.carros.domain.Carro;
import br.com.livroandroid.carros.domain.CarroService;


public class CarrosFragment extends BaseFragment {
    protected RecyclerView recyclerView;
    // Tipo do carro passado pelos argumentos
    private int tipo;
    // Lista de carros
    private List<Carro> carros;
    private ProgressDialog dialog;

    // Método para instanciar esse fragment pelo tipo
    public static CarrosFragment newInstance(int tipo) {
        Bundle args = new Bundle();
        args.putInt("tipo", tipo);
        CarrosFragment f = new CarrosFragment();
        f.setArguments(args);
        return f;
    }
    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        if (getArguments() != null) {
            // Lê o tipo dos argumentos
            this.tipo = getArguments().getInt("tipo");
        }		// Registra a classe para receber eventos
        CarrosApplication.getInstance().getBus().register(this);


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle b) {
        View view = inflater.inflate(R.layout.fragment_carros, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle b) {
        super.onActivityCreated(b);
        taskCarros();
    }

    private void taskCarros() {
        // Busca os carros: Dispara a Task
        new GetCarrosTask().execute();
    }
    // Task para buscar os carros
    private class GetCarrosTask extends AsyncTask<Void,Void,List<Carro>> {
        @Override
        protected List<Carro> doInBackground(Void... params) {
            try {
                // Busca os carros em background (Thread)
                return CarroService.getCarros(getContext(), tipo);
            } catch (IOException e) {
                alert("Erro: " + e.getMessage());
                return null;
            }
        }
        @Override
        protected void onPostExecute(List<Carro> carros) {
            // Atualiza a interface
            if(carros != null) {
                CarrosFragment.this.carros = carros;
                // Atualiza a view na UI Thread
                recyclerView.setAdapter(new CarroAdapter(getContext(), carros, onClickCarro()));
            }
        }
    }

    // Da mesma forma que tratamos o evento de clique em um botão (OnClickListener)
    // Vamos tratar o evento de clique na lista.
    // A diferença é que a interface CarroAdapter.CarroOnClickListener nós mesmo criamos.
    private CarroAdapter.CarroOnClickListener onClickCarro() {
        return new CarroAdapter.CarroOnClickListener() {
            @Override
            public void onClickCarro(View view, int idx) {
                // Abre a tela de detalhes com o carro selecionado.
                Carro c = carros.get(idx);
                Intent intent = new Intent(getContext(), CarroActivity.class);
                intent.putExtra("carro", c);
                startActivity(intent);

            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Cancela o recebimento de eventos
        CarrosApplication.getInstance().getBus().unregister(this);
    }
    @Subscribe
    public void onBusAtualizarListaCarros(String refresh) {
        // Recebeu o evento, atualiza a lista
        taskCarros();
    }

}

