package random32bit.random32bit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
static Context context;
    static TextView textAcceleroment,gyroscope,light, lumen,xor;
    static int format=0;
    String[] data = {"Не устанавливать точность", "1", "2", "3", "4", "5"};
int a=0,g=0,l=0;
    byte[] XOR = new byte[32];
    Button btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textAcceleroment = (TextView) findViewById(R.id.textAcceleroment);
        gyroscope = (TextView) findViewById(R.id.gyroscope);
        light = (TextView) findViewById(R.id.light);
        lumen = (TextView) findViewById(R.id.lumen);
        xor = (TextView) findViewById(R.id.xor);
        btnStart = (Button) findViewById(R.id.btnStart);
        context=this;
        run();
// адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Точность показаний");
        // выделяем элемент
        spinner.setSelection(0);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
             // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                format=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void onClickStart(View v) {
        startService(new Intent(this, Accelerometer.class));
        a=1;
        btnStart.setBackgroundColor(Color.parseColor("#1cd000"));
    }

    public void onClickStop(View v) {
        stopService(new Intent(this, Accelerometer.class));
        a=0;
        findViewById(R.id.btnStart).setBackgroundColor(Color.parseColor("#F5F6FA"));
    }

    public void onClickStartg(View v) {
        startService(new Intent(this, Gyroscope.class));
        g=1;
        findViewById(R.id.btnStartg).setBackgroundColor(Color.parseColor("#1cd000"));
    }
    public void onClickStopg(View v) {
        stopService(new Intent(this, Gyroscope.class));
        g=0;
        findViewById(R.id.btnStartg).setBackgroundColor(Color.parseColor("#F5F6FA"));
    }

    public void onClickStartl(View v) {
        startService(new Intent(this, Light.class));
        l=1;
        findViewById(R.id.button).setBackgroundColor(Color.parseColor("#1cd000"));
    }
    public void onClickStopl(View v) {
        stopService(new Intent(this, Light.class));
        l=0;
        findViewById(R.id.button).setBackgroundColor(Color.parseColor("#F5F6FA"));
    }

    public static float decimal_format(float x){
        DecimalFormat df = null;

           if(format==1){df = new DecimalFormat("#");}
               if(format==2){df = new DecimalFormat("#.#");}
                   if(format==3){df = new DecimalFormat("#.##");}
                       if(format==4){df = new DecimalFormat("#.###");}
                           if(format==5){df = new DecimalFormat("#.####");}
                              if(format==6){ df = new DecimalFormat("#.#####");}

        if(format==0){ df = new DecimalFormat("#.#############");}
        System.out.println("формат вывод "+df.format(x));
        System.out.println("формат вывод float "+Float.valueOf(df.format(x).replace(',', '.')));

        return Float.valueOf(df.format(x).replace(',', '.'));
    }

    public static Context getContext() {
        return context;
    }

public void run(){
    Timer timer = new Timer();
    timer.schedule(new TimerTask()
    {
            @Override
            public void run()
            {
                try {
                    if(a==1 && g==1 && l==0){ag();}
                    if(a==1 && g==0 && l==1){al();}
                    if(a==0 && g==1 && l==1){gl();}
                    if(a==1 && g==1 && l==1){agl();}
                }catch (Exception c){}

            }

    }, 0, 500);
}
  public void ag(){
      runOnUiThread(new Runnable() {
          @Override
          public void run() {
              int iss = 0;
              for (byte xor1 : Accelerometer.END)
                  XOR[iss] = (byte) (xor1 ^ Gyroscope.END[iss++]);
              xor.setText(bytesToString(XOR));
          }
      });

  }
    public void al(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
        int iss = 0;
        for (byte xor1 : Accelerometer.END)
            XOR[iss] = (byte) (xor1 ^ Light.END[iss++]);
        xor.setText(bytesToString(XOR));
            }
        });
    }
    public void gl(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
        int iss = 0;
        for (byte xor1 : Gyroscope.END)
            XOR[iss] = (byte) (xor1 ^ Light.END[iss++]);
        xor.setText(bytesToString(XOR));
            }
        });
    }
    public void agl(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
        int i = 0;
        byte[] XOR1 = new byte[32];
        for (byte xor1 : Accelerometer.END)
            XOR1[i] = (byte) (xor1 ^ Gyroscope.END[i++]);
        int is = 0;
        for (byte xor1 : XOR1)
            XOR[is] = (byte) (xor1 ^ Light.END[is++]);
        xor.setText(bytesToString(XOR));
            }
        });
    }
    public static String bytesToString(byte[] bytes) {
        String cb="";
        for(int i = 0; i<bytes.length; i++) {
            cb = cb+bytes[i];
        }
        return cb;
    }
}
