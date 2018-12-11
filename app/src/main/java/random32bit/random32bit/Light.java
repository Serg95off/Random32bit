package random32bit.random32bit;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

import com.google.common.base.Strings;

public class Light extends Service implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senLight;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;
    public static byte[] END = new byte[32];

    public Light() {
        senSensorManager = (SensorManager) MainActivity.getContext().getSystemService(this.SENSOR_SERVICE);
        senLight = senSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        senSensorManager.registerListener(this, senLight , SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    String deleteCharacters(String str, int from, int to) {
        return str.substring(0,from)+str.substring(to);
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_LIGHT) {
            float x = sensorEvent.values[0];  long bitX = Float.floatToIntBits(x);
            MainActivity.lumen.setText(x+" лк.");
            System.out.println("Датчик света "+x);
            String strX="";

            if(Long.toBinaryString(bitX).length()<32){ strX= Strings.padStart(Long.toBinaryString(bitX), 32, '0');}else {strX=deleteCharacters(Long.toBinaryString(bitX), 0, 32);}

           /* System.out.println(strX);
            System.out.println(strY);
            System.out.println(strZ);*/
          //  Log.i("onSensorChanged ", String.valueOf(x));

            char[] chArrayX = strX.toCharArray();

            for(int i = 0; i<strX.length(); i++) {
                END[i] = Byte.valueOf(String.valueOf(chArrayX[i]));
            }

            //     System.out.println("выводим"+ Arrays.toString(END));
            MainActivity.light.setText(bytesToString(END));


//try {
            //  int i = Integer.parseInt(bytesToString(END), 2);
            // MainActivity.textAcceleromentint.setText(String.valueOf(i));
            //  MainActivity.textAcceleromentint.setText(bytesToString(END));
            //  System.out.println("выводим "+ i);
//}catch (Exception e){}


        }
    }
    public static String bytesToString(byte[] bytes) {
        String cb="";
        for(int i = 0; i<bytes.length; i++) {
            cb = cb+bytes[i];
        }
        return cb;
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    public void onDestroy() {
        super.onDestroy();
        senSensorManager.unregisterListener(this);
        System.out.println("выходим ");
        // senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);

    }
}
