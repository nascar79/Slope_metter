package qwerty.slopemetter;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class slope_metter extends Activity implements SensorListener {

     private int zeroSlopeY=0;
    private int zeroSlopeZ=0;
  //  private int slopeX=0;
    private int slopeY=0;
    private int slopeZ=0;



    final String tag = "IBMEyes";
    SensorManager sm = null;

    TextView xViewA = null;
    TextView yViewA = null;
    TextView zViewA = null;
    TextView xViewO = null;
    TextView yViewO = null;
    TextView zViewO = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        setContentView(R.layout.activity_slope_metter);
        xViewA = (TextView) findViewById(R.id.xbox);
        yViewA = (TextView) findViewById(R.id.ybox);
        zViewA = (TextView) findViewById(R.id.zbox);
        xViewO = (TextView) findViewById(R.id.xboxo);
        yViewO = (TextView) findViewById(R.id.yboxo);
        zViewO = (TextView) findViewById(R.id.zboxo);

    }
    public void onSensorChanged(int sensor, float[] values) {
        synchronized (this) {

            Log.d(tag, "onSensorChanged: " + sensor + ", x: " + values[0] + ", y: " + values[1] + ", z: " + values[2]);
            if (sensor == SensorManager.SENSOR_ORIENTATION) {
                xViewO.setText(" :  " + values[0]);
                if ((slopeY + zeroSlopeY) > 0) {
                    yViewO.setTextColor(Color.RED);
                } else yViewO.setTextColor(Color.GREEN);
                slopeY = (int) values[1];
                yViewO.setText(" " + Math.abs(slopeY + zeroSlopeY) + "'");
                if ((slopeZ + zeroSlopeZ) > 0) {
                    zViewO.setTextColor(Color.RED);
                } else zViewO.setTextColor(Color.GREEN);
                slopeZ = (int) values[2];
                zViewO.setText(" " + Math.abs(slopeZ + zeroSlopeZ) + "'");
            }

            if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
                xViewA.setText(" :  " + values[0]);
                yViewA.setText(" :  " + values[1]);
                zViewA.setText(" :  " + values[2]);
            }

        }
    }

    public void onAccuracyChanged(int sensor, int accuracy) {
        Log.d(tag,"onAccuracyChanged: " + sensor + ", accuracy: " + accuracy);
    }


    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this,
                SensorManager.SENSOR_ORIENTATION |
                        SensorManager.SENSOR_ACCELEROMETER,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        sm.unregisterListener(this);
        super.onStop();
    }

   /* public void resetX(View view) {
    }*/
    public void resetY(View view) {
        zeroSlopeY = slopeY*-1;
    }
    public void resetZ(View view) {
        zeroSlopeZ = slopeZ*-1;
    }

    public void onReset(View view) {
        zeroSlopeY=0;
        zeroSlopeZ=0;
    }

    }
