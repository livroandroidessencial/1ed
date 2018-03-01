package br.com.livroandroid.hellovideoview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Precisa da permissão INTERNET no AndroidManifest.xml
         */
        VideoView videoView = findViewById(R.id.videoView);
        videoView.setMediaController(new MediaController(this));
        String url = "http://www.livroandroid.com.br/livro/carros/esportivos/ferrari_ff.mp4";
        videoView.setVideoURI(Uri.parse(url));
        videoView.start();

        // Intent para tocar o vídeo no player nativo
        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(url), "video*//*");
        startActivity(intent);*/

    }
}
