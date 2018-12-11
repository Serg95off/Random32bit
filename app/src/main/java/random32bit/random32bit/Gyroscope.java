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

public class Gyroscope extends Service implements SensorEventListener {

    private SensorManager senSensorManager;
    private Sensor senGyroscope;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;

    public static byte[] END = new byte[32];

    public Gyroscope() {
        senSensorManager = (SensorManager) MainActivity.getContext().getSystemService(this.SENSOR_SERVICE);
        senGyroscope = senSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        senSensorManager.registerListener(this, senGyroscope , SensorManager.SENSOR_DELAY_NORMAL);
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

        if (mySensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x = MainActivity.decimal_format(sensorEvent.values[0]);  long bitX = Float.floatToIntBits(x);
            float y = MainActivity.decimal_format(sensorEvent.values[1]); long bitY = Float.floatToIntBits(y);
            float z = MainActivity.decimal_format(sensorEvent.values[2]); long bitZ = Float.floatToIntBits(z);
            String strX=""; String strY=""; String strZ="";

            if(Long.toBinaryString(bitX).length()<32){ strX= Strings.padStart(Long.toBinaryString(bitX), 32, '0');}else {strX=deleteCharacters(Long.toBinaryString(bitX), 0, 32);}
            if(Long.toBinaryString(bitY).length()<32){ strY=Strings.padStart(Long.toBinaryString(bitY), 32, '0');}else {strY=deleteCharacters(Long.toBinaryString(bitY), 0, 32);}
            if(Long.toBinaryString(bitZ).length()<32){ strZ=Strings.padStart(Long.toBinaryString(bitZ), 32, '0');}else {strZ=deleteCharacters(Long.toBinaryString(bitZ), 0, 32);}

           /* System.out.println(strX);
            System.out.println(strY);
            System.out.println(strZ);*/
            Log.i("onSensorChanged ", x+"  "+ y+"  "+z);
            char[] chArrayX = strX.toCharArray();
            byte[] array_X = new byte[32];
            for(int i = 0; i<strX.length(); i++) {
                array_X[i] = Byte.valueOf(String.valueOf(chArrayX[i]));
            }
            char[] chArrayY = strY.toCharArray();
            byte[] array_Y = new byte[32];
            for(int i = 0; i<strY.length(); i++) {
                array_Y[i] = Byte.valueOf(String.valueOf(chArrayY[i]));
            }
            char[] chArrayZ = strZ.toCharArray();
            byte[] array_Z = new byte[32];
            for(int i = 0; i<strZ.length(); i++) {
                array_Z[i] = Byte.valueOf(String.valueOf(chArrayZ[i]));
            }
            // XOR
            byte[] array_XOR = new byte[32];
            byte[] END = new byte[32];
            int iss = 0;
            for (byte xor1 : array_X)
                array_XOR[iss] = (byte) (xor1 ^ array_Y[iss++]);
            iss = 0;
            for (byte xor2 : array_XOR)
                END[iss] = (byte) (xor2 ^ array_Z[iss++]);


            //     System.out.println("выводим"+ Arrays.toString(END));
            MainActivity.gyroscope.setText(bytesToString(END));


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
