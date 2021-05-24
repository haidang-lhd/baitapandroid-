package com.example.androidstudioproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    ArrayList<String> regcode;
    ArrayList<Double> value;
    String option1,option2;
    Spinner spinner1;
    Spinner spinner2;
    ArrayList<String> list;
    ArrayAdapter adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        super.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        setContentView(R.layout.activity_main);

        spinner1=findViewById(R.id.option1);
        spinner2=findViewById(R.id.option2);
        Button btn=findViewById(R.id.convert);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.currency));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        list = new ArrayList<>();

        adapter2= new ArrayAdapter<>(this,R.layout.history_item, list);
        ListView listView = (ListView) findViewById(R.id.lsv);
        listView.setAdapter(adapter2);

        regcode=new ArrayList<>();
        value=new ArrayList<>();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv=findViewById(R.id.money);
                if (tv.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Bạn cần phải nhập số!",Toast.LENGTH_SHORT).show();
                } else {
                    option1= spinner1.getSelectedItem().toString().substring(0,3).toLowerCase();
                    option2= spinner2.getSelectedItem().toString().substring(0,3);
                    regcode.clear();
                    value.clear();
                    new ReadRSS().execute("https://"+option1+".fxexchangerate.com/rss.xml");
                }

            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private class ReadRSS extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content=new StringBuilder();
            try {
                URL url=new URL(strings[0]);
                InputStreamReader inputStreamReader=new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            XMLDOMParser parser=new XMLDOMParser();
            Document document=parser.getDocument(s);
            NodeList nodeList=document.getElementsByTagName("item");
            String  title="";

            for(int i=0;i<nodeList.getLength();i++){
                Element element= (Element) nodeList.item(i);
                title=parser.getValue(element,"title");
                title=title.substring(title.length()-4, title.length()-1);
                regcode.add(title);
                String str=parser.getValue(element,"description");
                String[] output = str.split(" ");
                for(int j=0;j<output.length;j++){
                    if (output[j].equalsIgnoreCase("="))
                        value.add(Double.parseDouble(output[j+1]));
                }
            }
            for(int i=0;i<regcode.size();i++){
                if(option2.equalsIgnoreCase(regcode.get(i))){
                    EditText money=findViewById(R.id.money);
                    TextView rs=findViewById(R.id.result);
                    double kq=value.get(i)*Double.parseDouble(money.getText().toString());
                    double f = Math.round((kq) *100.0)/100.0;
                    MoneyChanger mc=new MoneyChanger(option1.toUpperCase(),option2.toUpperCase(),Double.parseDouble(money.getText().toString()),f);
                    rs.setText(""+f+" "+option2.toUpperCase());

                    list.add(0,mc.toString());
                    adapter2.notifyDataSetChanged();
                    break;
                }
            }
        }

    }

    private boolean checkInternetConnection() {
        ConnectivityManager connManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            Toast.makeText(this, "Chưa có kết nối mạng!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!networkInfo.isConnected()) {
            Toast.makeText(this, "Chưa có kết nối mạng!", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!networkInfo.isAvailable()) {
            Toast.makeText(this, "Chưa có kết nối mạng!", Toast.LENGTH_LONG).show();
            return false;
        }
        Toast.makeText(this, "Kết nối mạng thành công!", Toast.LENGTH_LONG).show();
        return true;
    }

//    public void getHistory(){
//        List l=CSVFile.read();
//        for (Object s: l){
//
//        }
//    }
}