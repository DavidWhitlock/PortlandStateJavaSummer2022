package edu.pdx.cs410j.whitlock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    private static final int GET_SUM_FROM_CALCULATOR = 42;
    private ArrayAdapter<Integer> sums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.sums);

        sums = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        try {
            sums.addAll(readSumsFromFile());

        } catch (IOException e) {
            toastExeption(e);
        }
        listView.setAdapter(sums);

        listView.setOnItemClickListener((adapterView, view, index, l) -> {
            Integer sum = (Integer) adapterView.getItemAtPosition(index);
            Toast.makeText(MainActivity.this, "Clicked on " + sum, Toast.LENGTH_LONG).show();
        });
    }

    private Collection<Integer> readSumsFromFile() throws IOException {
        File sumsFile = getSumsFile();
        Stream<String> lines = Files.lines(sumsFile.toPath());
        return lines.map(Integer::parseInt).collect(Collectors.toList());
    }

    public void launchCalculator(View view) {
        Intent intent = new Intent(this, CalculatorActivity.class);
        startActivityForResult(intent, GET_SUM_FROM_CALCULATOR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_SUM_FROM_CALCULATOR && resultCode == RESULT_OK && data != null) {
            int sum = data.getIntExtra(CalculatorActivity.EXTRA_SUM, 0);
            this.sums.add(sum);
            try {
                writeSumsToFile();

            } catch (IOException e) {
                toastExeption(e);
            }
        }
    }

    private void toastExeption(IOException e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void writeSumsToFile() throws IOException {
        File sumsFile = getSumsFile();
        try (PrintWriter pw = new PrintWriter(new FileWriter(sumsFile))) {
            for(int i = 0; i < this.sums.getCount(); i++) {
                Integer sum = this.sums.getItem(i);
                pw.println(sum);
            }
        }
    }

    @NonNull
    private File getSumsFile() {
        return new File(this.getDataDir(), "sums.txt");
    }
}