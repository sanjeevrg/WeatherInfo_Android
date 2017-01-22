package com.sanjeevg.weatherinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.TextView;
import java.lang.Class;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main, new WeatherDisplay())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
		/*int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);*/
        if(item.getItemId() == R.id.change_city){
            showChangeLocationDialog();
        }
        return false;

    }


    private void showChangeLocationDialog(){

        LayoutInflater factory = LayoutInflater.from(this);
        final View selectLocationView = factory.inflate(R.layout.select_location, null);
        final EditText selectCountry = (EditText) selectLocationView.findViewById(R.id.select_country);
        final EditText selectCity = (EditText) selectLocationView.findViewById(R.id.select_city);
        selectCountry.setText("Country", TextView.BufferType.EDITABLE);
        selectCity.setText("City", TextView.BufferType.EDITABLE);


        AlertDialog.Builder changePreference = new AlertDialog.Builder(this);
        changePreference.setTitle("Change Preferred Location");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
//        changePreference.setView(input);
        changePreference.setView(selectLocationView);
        changePreference.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichText) {
                if(checkCountryCity(selectCountry.getText().toString(),selectCity.getText().toString())){
                    changeCity(selectCountry.getText().toString(),selectCity.getText().toString());
                }else{
                    dialog.cancel();
                    displayInvalidLocation();
                }

            }
        });
        changePreference.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        changePreference.show();
    }

    public void displayInvalidLocation(){
        new AlertDialog.Builder(this)
                .setTitle("Invalid entry")
                .setMessage("Entered Location is Invalid")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public boolean checkCountryCity(String country,String city){
        return (new UserLocation(this).checkValidLocation(country,city));

    }

    public void changeCity(String country,String city){

        WeatherDisplay wf = (WeatherDisplay)getSupportFragmentManager()
                .findFragmentById(R.id.activity_main);
        wf.changeCity(city);
        new UserLocation(this).setLocation(country,city);

    }
}
