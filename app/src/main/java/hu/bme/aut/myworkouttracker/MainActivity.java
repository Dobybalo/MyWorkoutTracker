package hu.bme.aut.myworkouttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import model.DataManager;
import model.Workout;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String selectedWorkoutShortName;
   // private long id;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    private boolean firstTimeStarting = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // SugarContext.init(this);

        //betöltjük a workout adatokat
        DataManager.setupWorkouts();

        //Spinner beállítása
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // listener!
        spinner.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
        R.array.workoutProgramsArray, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        //startButton
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //beállítjuk az aktív programot
                DataManager.setActiveWorkout(selectedWorkoutShortName);

            //    Log.i("activeworkout", "setActiveWorkout lefutott, returns: ");

                Workout activeWorkout = DataManager.getActiveWorkout();
                // int days = activeWorkout.getRequiredDaysPerWeek();


                if (activeWorkout.hasStarted() == false) {
                    Intent intent = new Intent(MainActivity.this, SelectWorkoutDaysActivity.class);
                   // intent.putExtra(SelectWorkoutDaysActivity.KEY_HOW_MANY, days);
                    startActivity(intent);
                } else {

                    //ki kell olvasnunk az adatbázisból a dátumokat, napok "állapotát"
                    activeWorkout.readScheduleFromDB();

                    // TODO - egyébként a naptárhoz megyünk
                    Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                    startActivity(intent);

                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String[] workoutShortNames = getResources().getStringArray(R.array.workoutShortNames);
        selectedWorkoutShortName = workoutShortNames[pos];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
