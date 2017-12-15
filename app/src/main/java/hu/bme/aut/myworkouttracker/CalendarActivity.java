package hu.bme.aut.myworkouttracker;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.joda.time.LocalDate;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import model.DataManager;
import model.Workout;
import model.WorkoutDay;

public class CalendarActivity extends AppCompatActivity {

    CaldroidFragment caldroidFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY);
        caldroidFragment.setArguments(args);

        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {

                LocalDate ld = new LocalDate(date);
                WorkoutDay wd = DataManager.getActiveWorkout().getSchedule().get(ld);

                if (wd != null && !wd.hasFinished()) {
                    DataManager.setActiveWorkoutDay(wd);

                    Intent intent = new Intent(CalendarActivity.this, WorkoutDayActivity.class);
                    startActivity(intent);
                }

            }
        };

        caldroidFragment.setCaldroidListener(listener);

        setBackgroundColors();

        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.fragmentToSwap, caldroidFragment);
        t.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(caldroidFragment);
        setBackgroundColors();
        ft.attach(caldroidFragment);
        ft.commit();
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void setBackgroundColors() {
        Workout activeWorkout = DataManager.getActiveWorkout();
        HashMap<LocalDate, WorkoutDay> schedule = activeWorkout.getSchedule();
        int s = schedule.size();

        Iterator it = schedule.entrySet().iterator();
        while (it.hasNext()) {

            Map.Entry pair = (Map.Entry)it.next();

            LocalDate ld = (LocalDate) pair.getKey();
            Date dt = ld.toDateTimeAtStartOfDay().toDate();

            WorkoutDay wd = (WorkoutDay) pair.getValue();

            if (wd.hasFinished()) {
                Drawable finishedDay = ResourcesCompat.getDrawable(getResources(), R.drawable.finishedday, null);
                caldroidFragment.setBackgroundDrawableForDate(finishedDay, dt);
            }
            else if (wd.hasStarted() && (wd.hasFinished() == false)) {
                Drawable startedDay = ResourcesCompat.getDrawable(getResources(), R.drawable.startedday, null);
                caldroidFragment.setBackgroundDrawableForDate(startedDay, dt);
            }
            else {
                //még nem kezdődött el
                Drawable unstartedDay = ResourcesCompat.getDrawable(getResources(), R.drawable.starteddayshape, null);
                caldroidFragment.setBackgroundDrawableForDate(unstartedDay, dt);
            }

        }
    }

}
