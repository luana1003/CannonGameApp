package com.example.cannongameapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {
    TextView tvPoints;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);

        // Verifica se há extras antes de tentar obter "points"
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int points = extras.getInt("points", 0); // O segundo parâmetro é o valor padrão se "points" não estiver presente
            tvPoints = findViewById(R.id.tvPoints);
            tvPoints.setText(String.valueOf(points));
        }
    }


    public void restart(View v){
        Intent intent = new Intent(GameOver.this, StartUp.class);
        startActivity(intent);
        finish();
    }

    public void exit (View v){
        finish();
    }
}
