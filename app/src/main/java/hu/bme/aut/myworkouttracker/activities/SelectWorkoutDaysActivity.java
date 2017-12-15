package hu.bme.aut.myworkouttracker.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import org.joda.time.LocalDate;

import java.util.Date;

import hu.bme.aut.myworkouttracker.R;
import hu.bme.aut.myworkouttracker.data.DataManager;
import hu.bme.aut.myworkouttracker.models.Workout;

public class SelectWorkoutDaysActivity extends AppCompatActivity {

    private static int how_many_days;
    private static int selectedDays = 0;
    private boolean[] selection = new boolean[7];
    private LocalDate startDate;
    private Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_workout_days);

        final Workout activeWorkout = DataManager.getActiveWorkout();
        how_many_days = activeWorkout.getRequiredDaysPerWeek();

        goButton = (Button) findViewById(R.id.goButton);

        //by default legyen a gomb disabled
        goButton.setEnabled(false);

        goButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startDate = new LocalDate(new Date());

                // ki kell számolni, hogy a megadott napok függvényében
                //   mely dátumokra esik edzés
                activeWorkout.setWorkoutDays(selection, startDate);

                activeWorkout.start();
                activeWorkout.save();

                Intent intent = new Intent(SelectWorkoutDaysActivity.this, CalendarActivity.class);
                startActivity(intent);

            }
        });

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

               checkEnoughDaysSelected();
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

                checkEnoughDaysSelected();
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

                checkEnoughDaysSelected();
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

                checkEnoughDaysSelected();
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

                checkEnoughDaysSelected();
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

                checkEnoughDaysSelected();
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

                checkEnoughDaysSelected();
            }
        });

    }

    private void checkEnoughDaysSelected() {
        if (how_many_days == selectedDays) {
            goButton.setEnabled(true);
        } else
            goButton.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedDays = 0;   //problémás lenne, ha vissza lehetne ide lépni
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
