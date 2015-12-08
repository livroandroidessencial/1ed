package br.com.livroandroid.helloreceiver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickCriarNotificacao(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        NotificationUtil.notify(this, 1, intent, "Livro Android", "Hello Notification");
    }
}
