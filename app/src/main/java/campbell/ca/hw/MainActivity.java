package campbell.ca.hw;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Simple calculator, two entry fields on the ui
 * A button for each of add, subtract, multiply and divide
 * which generate a result.
 * The view for the result has it's state maintaned
 *
 * Minor data validation (not empty, no divide by zero
 *
 * This version saves the input & result for whatever the las
 * calculation was,
 * This version hides the keyboard when the add button is hit.
 *
 * @author PMCampbell
 * @version 3
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
        checkForAndRestorePrefs();
    }
    /**
     * check shared preferences, restore the values
     * probably should check if there are or not
     * but zero is ok by me on startup
     */
    public void checkForAndRestorePrefs() {
        String strResult, strNum1, strNum2;

        // get the data from shared preferences
        // you can save other datatypes (float, int, boolean etc)
        // you cannot directly save a double
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        strNum1 = prefs.getString("num1", "");
        strNum2 = prefs.getString("num2","");
        strResult = prefs.getString("result", "");

        // restore the data on the UI
        etNum1.setText(strNum1);
        etNum2.setText(strNum2);
        result.setText(strResult);
    }

    public void addNums(View v) {
        num1 = Double.parseDouble(etNum1.getText().toString());
        num2 = Double.parseDouble(etNum2.getText().toString());
        result.setText(Double.toString(num1+num2));
        hideKeyboard();
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
     * method to close the keyboard, by default it stays open if the cursor
     * is on an input field.
     *
     */
    public void hideKeyboard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    /**
     * we save the data in the finish()
     * activity lifecycle method
     * it is the one invoked when the app is being closed
     * onStop() may be invoked if we go into the stopped state,E
     * but we are not necessarily finished.
     *
     * so if we want to save data only on finish, we save it here
     *
     * don't forget to call the super @ end
     */
    @Override
    public void finish() {
        String strResult, strNum1, strNum2;

        // get  the data from the UI
        strNum1 = etNum1.getText().toString();
        strNum2 = etNum2.getText().toString();
        strResult = result.getText().toString();
        // MODE_PRIVATE: file only accessible by calling app (same UID)
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // save the data as key/value pairs
        // you can use other data types see SharedPreferences class
        editor.putString("num1", strNum1);
        editor.putString("num2", strNum2);
        editor.putString("result", strResult);

        // don't forget to commit the changes
        editor.commit();
        Log.d(TAG, "finish()");
        super.finish();

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
