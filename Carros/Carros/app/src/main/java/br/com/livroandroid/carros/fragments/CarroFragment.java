package br.com.livroandroid.carros.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.domain.Carro;

public class CarroFragment extends BaseFragment {
    private Carro carro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle b) {
        // Infla a view deste fragment.
        View view = inflater.inflate(R.layout.fragment_carro, container, false);
        // Lê o objeto carro dos parâmetros
        carro = getArguments().getParcelable("carro");
        // Atualiza a descrição do carro no TextView
        TextView tDesc = (TextView) view.findViewById(R.id.tDesc);
        tDesc.setText(carro.desc);
        return view;
    }
}
