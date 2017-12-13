package model;

import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;

/**
 * Created by Balint on 2017. 11. 27..
 */

public class Workout extends SugarRecord<Workout> {

    private boolean started = false;

    //PERZISZTENCIA miatt kell
    private String workoutName;

    private int requiredDaysPerWeek;    // hetente hány napra van program benne
    private int lengthInWeeks;

    // lehet hogy erre a perzisztencia miatt nem is lesz szükség
    @Ignore
    private Set<String> exercise_types = new HashSet<>(); // az előrehaladás és a többi miatt... -> bővíthetőség miatt jobb lenne osztályokat használni!

    @Ignore
    private ArrayList<WorkoutDay> workoutDays = new ArrayList<>();

    @Ignore
    private HashMap<LocalDate, WorkoutDay > schedule = new HashMap<>();    // edzésnapok listája

    //TODO - biztos nem kell az alábbi?
    @Ignore
    private WorkoutDay activeWorkoutDay;    // ez csak addig kell, amíg fut a program

    private void setupExercisesSet() {
        // database query: SELECT DISTINCT
        List<Exercise> exerciseList = Exercise.listAll(Exercise.class);
        for (Exercise e: exerciseList) {
            exercise_types.add(e.getName());    // mivel set, csak akkor adja hozzá, ha új
        }
    }



    public String getWorkoutName() {
        return workoutName;
    }

    public Workout() {}

    public Workout(String name) {
        workoutName = name;
        this.lengthInWeeks = lengthInWeeks;
    }

    // TODO - hibakezelés
    public void setLengthInWeeks(int n) {
        if (n >= 0) lengthInWeeks = n;
    }

    public WorkoutDay getActiveWorkoutDay() { return activeWorkoutDay; }

    public void setActiveWorkoutDay(WorkoutDay activeWorkoutDay) {
        this.activeWorkoutDay = activeWorkoutDay;
    }

    public boolean hasStarted() {return started;}

    public void setRequiredDaysPerWeek(int n) {
        requiredDaysPerWeek = n;
    }

    public void addWorkoutDay(WorkoutDay wd) {
        workoutDays.add(wd);
    }

    public int getRequiredDaysPerWeek() { return requiredDaysPerWeek; }

    public HashMap<LocalDate, WorkoutDay> getSchedule() { return schedule; }

    private void setupWorkoutDaysList() {
        List<WorkoutDay> workoutDayList = WorkoutDay.getWorkoutDaysListForWorkout(this);
        workoutDays.addAll(workoutDayList);
    }

    private void setupSchedule() {
        // már megvan a workoutDay-ek listája, amik ehhez a Workouthoz tartoznak
        if (workoutDays.isEmpty()) {
            //TODO - throw exception?
        }
        else {
            //végigmegyünk a listán, kiolvassuk a dátumokat, aszerint mappeljük a napokat
            for (WorkoutDay wd : workoutDays) {
                LocalDate ld = wd.getDate();
                schedule.put(ld, wd);
            }
        }

    }

    public void readScheduleFromDB() {
        setupWorkoutDaysList();
        setupSchedule();
    }

    //Ez a függvény pontosan 1x fut le egy Workout-ot nézve,
    // legközelebb már csak ki kell olvasni a dátumokat
    public void setWorkoutDays(boolean[] days, LocalDate startDate) {

        // TODO: beteszünk még egy DatePickert a napválasztó activityre...

        //get startDate as day of week
        Date date = startDate.toDateTimeAtStartOfDay().toDate();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        setupWorkoutDaysList();

       // if (workoutDays.size() == 0) Log.i("azonosito", "size: 0");

        for (int i=0; i < lengthInWeeks * 7; i++) {

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) -2; // 1-7 terjedő szám

            if (dayOfWeek == -1) dayOfWeek = 6;

            //Log.i("azonosito", "day of week valtozo erteke: " + dayOfWeek);

            if (days[dayOfWeek] == true) {
                Date dt = calendar.getTime();
                LocalDate ld = new LocalDate(dt);
                if (workoutDays.isEmpty() == false) {
                    WorkoutDay wd = workoutDays.remove(0);
                    schedule.put(ld, wd);

                    wd.setDate(ld);
                    wd.save();

                }

            }

            calendar.add(Calendar.DATE, 1);

        }

        //setupSchedule();

    }

    public void addExerciseType(String s) {
        exercise_types.add(s);
    }

    // nem biztos, hogy meg lesz valósítva...
    private void parseWorkoutFromXML()  {
        //TODO
    }

    public void start() {
        started = true;

    }

    //a progresst is itt kéne eltárolni - tipikusan perzisztens dolog...

}
