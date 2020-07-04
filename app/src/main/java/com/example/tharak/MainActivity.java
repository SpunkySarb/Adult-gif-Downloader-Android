package com.example.tharak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public static ListView gifList;
    EditText gif;
    ArrayList<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ActivityCompat
                .requestPermissions(
                        MainActivity.this,
                        new String[] { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE },
                        101);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(this).asGif().load(R.drawable.tharak).into(imageView);
        gifList = (ListView) findViewById(R.id.listView);
        ///Query

        gif = (EditText) findViewById(R.id.query);
        gifList = (ListView) findViewById(R.id.listView);
        ImageButton ibtn = (ImageButton) findViewById(R.id.imageButton2);
        ibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.clear();
                String s = gif.getText().toString();
                API obj = new API(s);
                Thread t1 = new Thread(obj);
if(t1.isAlive()){
    t1.interrupt();
}
                t1.start();
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ChatAdapter adapter = null;

                 data = obj.links();



                adapter = new ChatAdapter(MainActivity.this, data );


                gifList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        gifList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
              Save object = new Save(data.get(pos).replace("\"", ""));
                Thread t1 = new Thread(object);
                if(t1.isAlive()){
                    t1.interrupt();
                }
                t1.start();

                Toast toast=Toast.makeText(getApplicationContext(),"GIF SAVED USTAAD JI",Toast.LENGTH_SHORT);
                Log.v("long clicked","pos: " + pos);
toast.show();
                return true;
            }
        });

    }

    public class ChatAdapter extends ArrayAdapter<String> {
        public String x;
        ArrayList<String> listt = new ArrayList<>();


        public ChatAdapter(Context ctx, ArrayList<String> query) {


            super(ctx, 0);
            listt = query;
        }

        public int getCount() {
            return listt.size();
        }

        public String getItem(int position) {
            return listt.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {





            if(convertView == null){
                LayoutInflater lInflater = (LayoutInflater)getContext().getSystemService(
                        Activity.LAYOUT_INFLATER_SERVICE);

                convertView = lInflater.inflate(R.layout.gif_screen, null);
            }


                ImageView imageView2 = (ImageView) convertView.findViewById(R.id.imageView2);

                Glide.with(getContext()).asGif().load(listt.get(position).replace("\"", "")).into(imageView2);




            return convertView;

        }




    }
}
