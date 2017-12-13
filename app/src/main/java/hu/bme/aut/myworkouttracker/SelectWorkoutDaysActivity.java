package hu.bme.aut.myworkouttracker;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.joda.time.LocalDate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import model.DataManager;
import model.Workout;

public class SelectWorkoutDaysActivity extends AppCompatActivity {

    public static final String KEY_HOW_MANY = "KEY_HOW_MANY";
    private static int how_many_days;
    private static int selectedDays = 0;
    //private Set<Integer> selection = new HashSet<>();
    private boolean[] selection = new boolean[7];
    private LocalDate startDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_workout_days);

        /*
        Intent intent = getIntent();
        how_many_days = intent.getIntExtra(KEY_HOW_MANY, 1);
        */

        //ez változhat - TODO
        // onResume-ban is frissíteni kell!!
        final Workout activeWorkout = DataManager.getActiveWorkout();
        how_many_days = activeWorkout.getRequiredDaysPerWeek();

        final Button goButton = (Button) findViewById(R.id.goButton);

        //defaultból legyen a gomb disabled
        goButton.setEnabled(false);

        goButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //validáció - elég nap ki van-e választva
                //TODO - bár, végül is megvan...

                //teszt!!
                startDate = new LocalDate(new Date());

                // ki kell számolni, hogy a megadott napok függvényében
                //   melyik napokra esik edzés
                activeWorkout.setWorkoutDays(selection, startDate);

                //started-ra állítjuk a Workout-ot
                activeWorkout.start();
                activeWorkout.save();

                Intent intent = new Intent(SelectWorkoutDaysActivity.this, CalendarActivity.class);
                //intent.putExtra(SelectWorkoutDaysActivity.KEY_HOW_MANY, days);
                startActivity(intent);

            }
        });

        //toggles

        for (int i=0; i<7; i++) {
            selection[i] = false;
        }

        ToggleButton tgButton  = (ToggleButton) findViewById(R.id.toggleButton);
        tgButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selection[0] = true;
                    selectedDays++;
                } else {
                    selection[0] = false;
                    selectedDays--;
                }

                if (how_many_days == selectedDays) {
                    goButton.setEnabled(true);
                } else
                    goButton.setEnabled(false);
            }
        });

        ToggleButton tgButton2 = (ToggleButton) findViewById(R.id.toggleButton2);
        tgButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selection[1] = true;
                    selectedDays++;
                } else {
                    selection[1] = false;
                    selectedDays--;
                }

                if (how_many_days == selectedDays) {
                    goButton.setEnabled(true);
                } else
                    goButton.setEnabled(false);
            }
        });

        ToggleButton tgButton3 = (ToggleButton) findViewById(R.id.toggleButton3);
        tgButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selection[2] = true;
                    selectedDays++;
                } else {
                    selection[2] = false;
                    selectedDays--;
                }

                if (how_many_days == selectedDays) {
                    goButton.setEnabled(true);
                } else
                    goButton.setEnabled(false);
            }
        });

        ToggleButton tgButton4 = (ToggleButton) findViewById(R.id.toggleButton4);
        tgButton4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selection[3] = true;
                    selectedDays++;
                } else {
                    selection[3] = false;
                    selectedDays--;
                }

                if (how_many_days == selectedDays) {
                    goButton.setEnabled(true);
                } else
                    goButton.setEnabled(false);
            }
        });

        ToggleButton tgButton5 = (ToggleButton) findViewById(R.id.toggleButton5);
        tgButton5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selection[4] = true;
                    selectedDays++;
                } else {
                    selection[4] = false;
                    selectedDays--;
                }

                if (how_many_days == selectedDays) {
                    goButton.setEnabled(true);
                } else
                    goButton.setEnabled(false);
            }
        });
        ToggleButton tgButton6 = (ToggleButton) findViewById(R.id.toggleButton6);
        tgButton6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selection[5] = true;
                    selectedDays++;
                } else {
                    selection[5] = false;
                    selectedDays--;
                }

                if (how_many_days == selectedDays) {
                    goButton.setEnabled(true);
                } else
                    goButton.setEnabled(false);
            }
        });

        ToggleButton tgButton7 = (ToggleButton) findViewById(R.id.toggleButton7);
        tgButton7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selection[6] = true;
                    selectedDays++;
                } else {
                    selection[6] = false;
                    selectedDays--;
                }

                if (how_many_days == selectedDays) {
                    goButton.setEnabled(true);
                } else
                    goButton.setEnabled(false);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //betölthetjük az elmentett adatokat, de egyelőre csak "frissítjük" a változókat
        selectedDays = 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
