package com.example.project2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText LiczbaKostek;
    private GridLayout Kostki;
    private TextView Wyniki;

    private final ArrayList<Integer> wyniki = new ArrayList<>();
    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LiczbaKostek = findViewById(R.id.LiczbaKostek);
        Kostki = findViewById(R.id.Kostki);
        Wyniki = findViewById(R.id.Wyniki);
        Button buttonLosuj = findViewById(R.id.buttonLosuj);
        Button buttonRosnaco = findViewById(R.id.buttonRosnaco);
        Button buttonMalejaco = findViewById(R.id.buttonMalejaco);

        Kostki.setColumnCount(5);

        buttonLosuj.setOnClickListener(v -> losuj());

        buttonRosnaco.setOnClickListener(v -> {
            if (wyniki.isEmpty()) {
                Toast.makeText(this, "najpierw wylosuj", Toast.LENGTH_SHORT).show();
                return;
            }
            sortujRos();
            odswiez();
        });

        buttonMalejaco.setOnClickListener(v -> {
            if (wyniki.isEmpty()) {
                Toast.makeText(this, "najpierw wylosuj", Toast.LENGTH_SHORT).show();
                return;
            }
            sortujMal();
            odswiez();
        });
    }

    private void losuj() {
        int liczba;
        try {
            liczba = Integer.parseInt(LiczbaKostek.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Wpisz poprawną liczbę", Toast.LENGTH_SHORT).show();
            return;
        }

        if (liczba < 1 || liczba > 100) {
            Toast.makeText(this, "liczba musi byc 1< lub >100", Toast.LENGTH_SHORT).show();
            return;
        }

        wyniki.clear();
        for (int i = 0; i < liczba; i++) {
            wyniki.add(random.nextInt(6) + 1);
        }
        odswiez();
    }
    private void sortujRos() {
        int n = wyniki.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (wyniki.get(j) > wyniki.get(j + 1)) {
                    int temp = wyniki.get(j);
                    wyniki.set(j, wyniki.get(j + 1));
                    wyniki.set(j + 1, temp);
                }
            }
        }
    }
    private void sortujMal() {
        int n = wyniki.size();
        for (int i = 0; i < n - 1; i++) {
            int max = i;
            for (int j = i + 1; j < n; j++) {
                if (wyniki.get(j) > wyniki.get(max)) {
                    max = j;
                }
            }
            int temp = wyniki.get(i);
            wyniki.set(i, wyniki.get(max));
            wyniki.set(max, temp);
        }
    }
    private void odswiez() {
        Kostki.removeAllViews();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < wyniki.size(); i++) {
            TextView kostka = new TextView(this);
            kostka.setText(String.valueOf(wyniki.get(i)));
            Kostki.addView(kostka);

            if (i > 0) sb.append(", ");
            sb.append(wyniki.get(i));
        }
        Wyniki.setText(sb.toString());
    }
}