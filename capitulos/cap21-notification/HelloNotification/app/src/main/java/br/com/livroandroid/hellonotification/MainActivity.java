package br.com.livroandroid.hellonotification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cria o canal (channel) para a notificação
        NotificationUtil.createChannel(this);
    }

    // Mostra a notificação
    public void onClickCriarNotificacao(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        NotificationUtil.create(this, 1, intent, "Livro Android", "Hello Notification");
    }
}
