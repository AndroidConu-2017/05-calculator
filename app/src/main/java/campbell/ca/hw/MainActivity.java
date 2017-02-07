package campbell.ca.hw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Simple calculator, two entry fields on the ui
 * A button for each of add, subtract, multiply and divide
 * which generate a result
 *
 * Minor data validation (not empty, no divide by zero
 *
 * This version maintains state for the result
 *
 * @author PMCampbell
 * @version 2
 *
 **/
public class MainActivity extends AppCompatActivity {
    String TAG = "CALC";  // tag for Logging
    EditText etNum1, etNum2;
    TextView result = null;
    double num1, num2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get a handle to text fields
        etNum1 = (EditText) findViewById(R.id.num1);
        etNum2 = (EditText) findViewById(R.id.num2);
        result = (TextView) findViewById(R.id.result);
    }

    public void addNums(View v) {
        num1 = Double.parseDouble(etNum1.getText().toString());
        num2 = Double.parseDouble(etNum2.getText().toString());
        result.setText(Double.toString(num1+num2));
    }
    public void subtrNums(View v) {
        if (!readNums())
            return;
        result.setText(Double.toString(num1-num2));
    }
    public void divNums(View v) {
        if (!readNums())
            return;
        if (num2 ==0 )
            result.setText("Cannot divide by 0");
        else
            result.setText(Double.toString(num1/num2));
    }
    public void multNums(View v) {
        if (!readNums())
            return;
        result.setText(Double.toString(num1*num2));
    }
    public boolean readNums()  {
      if (etNum1.getText().toString().isEmpty() ||  etNum2.getText().toString().isEmpty() ) {
          result.setText("Number(s) input invalid");
          return false;
      }
        // TODO should be checking this ...
        num1 = Double.parseDouble(etNum1.getText().toString());
        num2 = Double.parseDouble(etNum2.getText().toString());
       return true;
    }

    /**
     *  State method onSaveInstanceState
     *  we don't need to keep the state of EditText etc if we use them,
     *  all Views with an id are saved by the superclass in
     *  the instance bundle automatically by Android
     *  if onSaveInstanceState() is called it will be run before onStop()
     *
     *  For this app the only thing we need to save ourselves is the result
     *  which is in a TextView the EditText and Buttons are saved by Android
     *  when we call the super.
     *
     *  @param outState
     */

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String strResult = "not set";
        if (result != null)  {
            strResult = result.getText().toString();
        }

        outState.putString("result", strResult);
        Log.d(TAG, "onSaveInstanceState() result:"+strResult);

    }

    /**
     *  State method onSaveInstanceState
     *  restore savedInstanceState here or in onCreate(Bundle)
     *
     *  For this app the only thing we need to restore ourselves is the result
     *  which is in a TextView the EditText and Buttons are restored by Android
     *  when we call the super.
     *
     *
     * @param inState   state bundle
     */
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
        // restore savedInstanceState here or in onCreate(Bundle)
        String strResult = inState.getString("result");
        if (result != null)
            result.setText(strResult);
        Log.d(TAG, "onRestoreInstanceState() result:"+strResult);

    }
}
