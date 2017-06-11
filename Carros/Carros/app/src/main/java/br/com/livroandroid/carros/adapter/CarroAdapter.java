package br.com.livroandroid.carros.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.livroandroid.carros.R;
import br.com.livroandroid.carros.domain.Carro;

/**
 * Created by rlech on 10-Dec-15.
 */
public class CarroAdapter extends RecyclerView.Adapter<CarroAdapter.CarrosViewHolder> {
    protected static final String TAG = "livroandroid";
    private final Context context;
    // Lista de carros
    private final List<Carro> carros;
    // Interface de callback para expor os eventos de toque na lista
    private CarroOnClickListener carroOnClickListener;

    // Construtor, geralmente recebe o contexto, a lista e a implementação da interface de callback.
    public CarroAdapter(Context context, List<Carro> carros, CarroOnClickListener
            carroOnClickListener) {
        this.context = context;
        this.carros = carros;
        this.carroOnClickListener = carroOnClickListener;
    }

    @Override
    public int getItemCount() {
        // Retorna a quantidade de linhas da lista (geralmente é a quantidade de elementos do array)
        // Uma view será criada para cada linha.
        return this.carros != null ? this.carros.size() : 0;
    }

    @Override
    public CarrosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Este método deve inflar a view e criar um ViewHolder.
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_carro, viewGroup, false);
        // Depois de inflar a view (R.layout.adapter_carro), cria o ViewHolder.
        // O ViewHolder contém a referência para todas as views do layout.
        CarrosViewHolder holder = new CarrosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final CarrosViewHolder holder, final int position) {
        // Recupera o carro da linha x
        Carro c = carros.get(position);
        // Faz a ligação (bind) das informações do carro, com as views do layout.
        holder.tNome.setText(c.nome);
        holder.progress.setVisibility(View.VISIBLE);
        // Faz o download da foto e mostra o ProgressBar
        Picasso.with(context).load(c.urlFoto).fit().into(holder.img,
                new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        // Download Ok, então esconde o progress.
                        holder.progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.progress.setVisibility(View.GONE);
                    }
                });
        // Click
        if (carroOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ao clicar na view da lista, chama a interface de callback (listener)
                    // Obs: A variável position é final
                    carroOnClickListener.onClickCarro(holder.itemView, position);
                }
            });
        }
    }

    // Interface de callback para expor os eventos da lista.
    public interface CarroOnClickListener {
        public void onClickCarro(View view, int idx);
    }

    // O ViewHolder possui a referência para as views do layout
    public static class CarrosViewHolder extends RecyclerView.ViewHolder {
        TextView tNome;
        ImageView img;
        ProgressBar progress;
        CardView cardView;

        private CarrosViewHolder(View view) {
            super(view);
            // Faz o findViewById(id) para armazenar as views
            // O Android vai reutilizar este ViewHolder durante a rolagem
            tNome = (TextView) view.findViewById(R.id.text);
            img = (ImageView) view.findViewById(R.id.img);
            progress = (ProgressBar) view.findViewById(R.id.progressImg);
            cardView = (CardView) view.findViewById(R.id.card_view);
        }
    }
}
