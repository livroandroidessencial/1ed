package br.com.livroandroid.helloservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by rlech on 06-Dec-15.
 */
public class HelloService extends IntentService {
    private static final int MAX = 10;
    private static final String TAG = "livro";
    protected int count;

    private boolean running;

    public HelloService() {
        super("LivroAndroid");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, ">> HelloService.onHandleIntent()");
        running = true;
        while (running && count < MAX) {
            // Simula algum processamento
            try {
                Thread.sleep(1000);
                Log.d(TAG, "HelloService executando... " + count);
                count++;
            } catch (InterruptedException e) {
            }
        }

        Log.d(TAG, "<< HelloService.onHandleIntent()");
        Intent mainIntent = new Intent(this, MainActivity.class);
        NotificationUtil.create(this, 1, mainIntent, "Livro Android", "Fim do serviço.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "HelloService.onDestroy()");
        running = false;
    }
}
