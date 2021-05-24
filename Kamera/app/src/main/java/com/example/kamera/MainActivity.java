package com.example.kamera;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE=1;
    static Uri capturedImageUri=null;
    String currentImagePath= null;
    private static final int IMAGE_REQUEST = 1;
    private int flag = 0;


    Bitmap bm;
    Button button;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();


        button = (Button)findViewById(R.id.button);
        imageView=(ImageView)findViewById(R.id.imageView);

        if(!hasCamera()){
            button.setEnabled(false);
        }

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                flag=1;
            }
        });

        Button button2 =(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag!=1){
                    Toast.makeText(MainActivity.this,"Xin hãy chụp hình trước",Toast.LENGTH_SHORT).show();
                }
                else if(flag == 2 ){
                    Toast.makeText(MainActivity.this, "Hình đã được lưu @@", Toast.LENGTH_SHORT).show();
                }
                else {
                SaveImage(bm);
                Toast.makeText(MainActivity.this, "Đã lưu tại com.example.kamera/files/Pictures", Toast.LENGTH_LONG).show();
                flag=2;
                }
            }
        });

        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(v -> {


                Intent intent = new Intent(MainActivity.this,ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,0);

                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, 8);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),1000 * 60 * 60 * 24 * 7,pendingIntent);



//                long cur = System.currentTimeMillis();
//                long tensec = 1000*10;
//                alarmManager.set(AlarmManager.RTC_WAKEUP,cur+tensec,pendingIntent);

            Toast.makeText(this,"Đã cài Notification",Toast.LENGTH_SHORT).show();
        });
    }
    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            imageView.setImageBitmap(photo);
            bm=photo;
        }
    }

    private File getImageFile() {
        System.out.println("run?");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageName = "jpg_"+timeStamp+"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = null;
        try {

            imageFile = File.createTempFile(imageName,".jpg",storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentImagePath = imageFile.getAbsolutePath();

        return imageFile;
    }

    private void SaveImage(Bitmap finalBitmap) {
        File file = getImageFile();
        FileOutputStream out = null;
        try {
            file.createNewFile();
            out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "JapaneseAnimeViewer";
            String description = "Short for JAV";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel chanel = new NotificationChannel("notifi",name,importance);
            chanel.setDescription(description);

            NotificationManager notiManager = getSystemService(NotificationManager.class);
            notiManager.createNotificationChannel(chanel);
        }
    }
}