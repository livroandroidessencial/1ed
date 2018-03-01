package br.com.livroandroid.helloreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Ricardo Lecheta on 08/03/2015.
 */
public class HelloReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context c, Intent it) {
        Log.d("livroandroid", "Hello Receiver !!!");

        // Cria uma notificação.
        Intent intent = new Intent(c, MainActivity.class);
        NotificationUtil.create(c, 1, intent, "Livro Android", "Hello Receiver");
    }
}
