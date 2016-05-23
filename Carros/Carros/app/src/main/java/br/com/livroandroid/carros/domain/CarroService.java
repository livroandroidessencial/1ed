package br.com.livroandroid.carros.domain;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.livroandroid.carros.R;
import livroandroid.lib.utils.HttpHelper;

public class CarroService {

    private static final String URL = "http://livrowebservices.com.br/rest/carros";
    private static final String TAG = CarroService.class.getSimpleName();

    public static List<Carro> getCarros(Context context, int tipo) throws IOException {
        List<Carro> carros = null;
        if(tipo == R.string.favoritos) {
            // Consulta no banco de dados
            CarroDB db = new CarroDB(context);
            carros = db.findAll();
        } else {
            // Consulta no web service.
            String tipoString = getTipo(tipo);
            String url = URL + "/tipo/" + tipoString;
            // Faz a requisição HTTP no servidor e retorna a string com o conteúdo.
            HttpHelper http = new HttpHelper();
            http.LOG_ON = true;
            // GET
            String json = http.doGet(url);
            // Parser JSON
            carros = parserJSON(context, json);
        }
        return carros;
    }

    private static List<Carro> parserJSON(Context context, String json) throws IOException {
        Type listType = new TypeToken<ArrayList<Carro>>() {}.getType();
        List<Carro> carros = new Gson().fromJson(json, listType);
        return carros;
    }

    // Converte a constante para string, para criar a URL do web service.
    private static String getTipo(int tipo) {
        if (tipo == R.string.classicos) {
            return "classicos";
        } else if (tipo == R.string.esportivos) {
            return "esportivos";
        }
        return "luxo";
    }
}