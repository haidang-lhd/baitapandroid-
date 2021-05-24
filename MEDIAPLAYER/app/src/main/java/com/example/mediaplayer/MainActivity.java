package com.example.mediaplayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView baner;
    ListView listView;
    ArrayList<String> paths;
    String dir;
    public static final String  NAME = "NAME" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        baner.setImageResource(R.drawable.banner);
        checkAndRequestPermissions();

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File directoryPath = new File(path);
        String contents[] = directoryPath.list();
        listView.setAdapter(new Adapter_listmain(this,R.layout.adapter_listmain,contents));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),Play_Song_Activity.class);
                intent.putExtra(NAME, contents[position]);
                dir = contents[position];
                if(initList()>0)
                    startActivity(intent);
                else Toast.makeText(getApplicationContext(),"Dữ liệu rỗng.",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void anhxa(){
        baner = findViewById(R.id.banner);
        listView = findViewById(R.id.list_item);
    }

    private void checkAndRequestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat

                    .checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }
    private int initList() {
        paths = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+dir;
        File file = new File(path);
        File files[] = file.listFiles();
        int lenght = files.length;
        if(lenght>0) {
            for (int i = 0; i < lenght; i++) {
                String s = files[i].getName();
                if (s.endsWith(".mp3")) {
                    paths.add(files[i].getAbsolutePath());
                }
            }
        }
        return paths.size();
    }

}