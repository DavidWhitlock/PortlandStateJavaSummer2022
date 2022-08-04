package edu.pdx.cs410j.whitlock;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int GET_SUM_FROM_CALCULATOR = 42;
    private ArrayAdapter<Integer> sums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.sums);

        sums = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(sums);

        listView.setOnItemClickListener((adapterView, view, index, l) -> {
            Integer sum = (Integer) adapterView.getItemAtPosition(index);
            Toast.makeText(MainActivity.this, "Clicked on " + sum, Toast.LENGTH_LONG).show();
        });
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
        }
    }
}