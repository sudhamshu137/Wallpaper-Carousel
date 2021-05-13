package com.example.wallpapercarousel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public ListView list;
    public ArrayList<String> i1;
    public ArrayList<String> i2;

    SharedPreferences prf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(!(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)){
            if(permission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        }

        Toast.makeText(MainActivity.this, "Loading maybe slow! please wait", Toast.LENGTH_SHORT).show();


        reduceChoreographerSkippedFramesWarningThreshold();

        prf = getSharedPreferences("aspect", Context.MODE_PRIVATE);
        String ar = prf.getString("ratio", "9:16");

        list = findViewById(R.id.listview);
        i1 = new ArrayList<>();
        i2 = new ArrayList<>();

        String xx;
        switch (ar) {
            case "1:1": xx = "/3000/3000";
                break;
            case "1:2": xx = "/1000/2000";
                break;
            case "2:1": xx = "/2000/1000";
                break;
            case "3:2": xx = "/3000/2000";
                break;
            case "2:3": xx = "/2000/3000";
                break;
            case "3:4": xx = "/3000/4000";
                break;
            case "4:3": xx = "/4000/3000";
                break;
            case "16:9": xx = "/3200/1800";
                break;
            default: xx = "/1800/3200";
                break;
        }

        for(int i=0; i<20; i++){

            Random r = new Random();
            int x = r.nextInt(999);
            i1.add("https://picsum.photos/id/" + x + xx);
            x = r.nextInt(999);
            i2.add("https://picsum.photos/id/" + x + xx);

            myAdapter adapter = new myAdapter();
            list.setAdapter(adapter);

        }


    }


    public class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return i1.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            view = getLayoutInflater().inflate(R.layout.card, viewGroup, false);
            final ImageView iv1 = view.findViewById(R.id.iv1);
            final ImageView iv2 = view.findViewById(R.id.iv2);
            final ImageButton save1 = view.findViewById(R.id.save1);
            final ImageButton save2 = view.findViewById(R.id.save2);

            // Do the random number generator here only and not there

//            Random r = new Random();
//            int x = r.nextInt(999);
//
//            Picasso.get().load("https://picsum.photos/id/" + x + "/3000/2000").networkPolicy(NetworkPolicy.NO_CACHE)
//                    .memoryPolicy(MemoryPolicy.NO_CACHE).into(iv1);
//
//            x = r.nextInt(999);
//
//            Picasso.get().load("https://picsum.photos/id/" + x + "/3000/2000").networkPolicy(NetworkPolicy.NO_CACHE)
//                    .memoryPolicy(MemoryPolicy.NO_CACHE).into(iv2);



//            Glide.with(MainActivity.this)
//                    .load("https://picsum.photos/id/" + x + "/3000/2000")
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
//                    .fitCenter()
//                    .into(iv1);

//            Glide.with(MainActivity.this)
//                    .load("https://picsum.photos/id/" + x + "/3000/2000")
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
//                    .fitCenter()
//                    .into(iv2);

//              .networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE)

            Picasso.get().load(i1.get(i)).into(iv1);
            Picasso.get().load(i2.get(i)).into(iv2);


            save1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                        save(iv1);
                        save1.setImageResource(R.drawable.savedbtn);
                    }
                    else{
                        if(permission != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                        }
                        else{
                            save(iv1);
                            save1.setImageResource(R.drawable.savedbtn);
                        }
                    }
                }
            });

            save2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int permission = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                        save(iv2);
                        save2.setImageResource(R.drawable.savedbtn);
                    }
                    else{
                        if(permission != PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                        }
                        else{
                            save(iv2);
                            save2.setImageResource(R.drawable.savedbtn);
                        }
                    }
                }
            });

            return view;
        }
    }



    public void save(ImageView iv){

        Bitmap bitmap = iv.getDrawingCache();

        BitmapDrawable draw = (BitmapDrawable) iv.getDrawable();
        bitmap = draw.getBitmap();

        File storageLoc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //context.getExternalFilesDir(null);

        File file = new File(storageLoc, System.currentTimeMillis() + ".jpg");

        try{
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            scanFile(this, Uri.fromFile(file));
            Toast.makeText(MainActivity.this, "Image saved to gallery", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void scanFile(Context context, Uri imageUri){
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        context.sendBroadcast(scanIntent);
    }

    public void goset(View view){
        Intent i = new Intent(MainActivity.this, settings.class);
        startActivity(i);
    }

    public void refresh(View view){

        prf = getSharedPreferences("aspect", Context.MODE_PRIVATE);
        String ar = prf.getString("ratio", "9:16");

        String xx;
        switch (ar) {
            case "1:1": xx = "/3000/3000";
                break;
            case "1:2": xx = "/1000/2000";
                break;
            case "2:1": xx = "/2000/1000";
                break;
            case "3:2": xx = "/3000/2000";
                break;
            case "2:3": xx = "/2000/3000";
                break;
            case "3:4": xx = "/3000/4000";
                break;
            case "4:3": xx = "/4000/3000";
                break;
            case "16:9": xx = "/3200/1800";
                break;
            default: xx = "/1800/3200";
                break;
        }

        list.setAdapter(null);
        i1.clear();
        i2.clear();
        for(int i=0; i<20; i++){

            Random r = new Random();
            int x = r.nextInt(999);
            i1.add("https://picsum.photos/id/" + x + xx);
            x = r.nextInt(999);
            i2.add("https://picsum.photos/id/" + x + xx);

            myAdapter adapter = new myAdapter();
            list.setAdapter(adapter);

        }

    }



    private void reduceChoreographerSkippedFramesWarningThreshold() {
        if (BuildConfig.DEBUG) {
            Field field = null;
            try {
                field = Choreographer.class.getDeclaredField("SKIPPED_FRAME_WARNING_LIMIT");
                field.setAccessible(true);
                field.setInt(field, field.getModifiers() & ~Modifier.FINAL);
                field.set(null, 5);
            } catch (Throwable e) {
                Log.e("TAG", "failed to change choreographer's skipped frames threshold");
            }
        }
    }


}


