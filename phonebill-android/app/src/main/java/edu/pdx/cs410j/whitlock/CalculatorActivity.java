package edu.pdx.cs410j.whitlock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity {

    static final String EXTRA_SUM = "SUM";

    private int sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
    }

    public void computeSum(View view) {
        EditText leftOperand = findViewById(R.id.leftOperand);
        EditText rightOperand = findViewById(R.id.rightOperand);
        EditText sum = findViewById(R.id.sum);

        int left = Integer.parseInt(leftOperand.getText().toString());
        int right = Integer.parseInt(rightOperand.getText().toString());

        this.sum = left + right;
        sum.setText(String.valueOf(this.sum));
    }

    public void returnToMain(View view) {
        Intent data = new Intent();
        data.putExtra(EXTRA_SUM, this.sum);
        setResult(RESULT_OK, data);
        finish();
    }
}