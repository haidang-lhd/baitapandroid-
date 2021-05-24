package com.example.temperaturechange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText txtDoC = (EditText)findViewById(R.id.txtDoC);
        EditText txtDoF = (EditText)findViewById(R.id.txtDoF);
        Convert c = new Convert();
        Button btnClean = (Button) findViewById(R.id.btnClear);

        Button btnCtoF = (Button)findViewById(R.id.btnCtoF);
        btnCtoF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNumber(txtDoC.getText().toString())){
                    double doC = Double.parseDouble(txtDoC.getText().toString());
                    c.setDoC(doC);
                    c.convertCtoF();

                    double doF = c.getDoF();
                    txtDoF.setText(String.valueOf(doF));
                }else {
                    Toast.makeText(getApplicationContext(),"Vui Lòng Nhập Nhiệt Độ",Toast.LENGTH_LONG).show();
                }

            }
        });
        Button btnFtoC = (Button)findViewById(R.id.btnFtoC);
        btnFtoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNumber(txtDoF.getText().toString())){
                    double doF = Double.parseDouble(txtDoF.getText().toString());

                    c.setDoF(doF);
                    c.convertFtoC();

                    double doC = c.getDoC();
                    txtDoC.setText(String.valueOf(doC));
                }else {
                    Toast.makeText(getApplicationContext(),"Vui Lòng Nhập Nhiệt Độ",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDoC.setText("");
                txtDoF.setText("");
            }
        });
    }


    public static boolean checkNumber(String str){
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}