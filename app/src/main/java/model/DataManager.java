package model;

import android.support.annotation.DrawableRes;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import hu.bme.aut.myworkouttracker.R;

/**
 * Created by Balint on 2017. 11. 27..
 */

public class DataManager {

    // Singleton!
    // TODO - rosszul használtam a Singletont, csupa statikus függvénye van

    private static DataManager instance = new DataManager();
    public static @DrawableRes int pushupIcon = R.drawable.pushup_icon;
    public static @DrawableRes int situpIcon = R.drawable.situp_icon;

    public static DataManager getInstance() {

        if (instance == null) {
            instance = new DataManager();
        }

        return instance;
    }

    private DataManager() {
        // ...
    }

    private static HashMap<String, Workout> workouts = new HashMap<>();    //ezt listázzuk majd ki az Activityn, ahol kiválaszthatja a user, melyiket akarja nézni most

    private static Workout activeWorkout;

   // private Set<Exercise> exercise_types;     // ?? - progresshez, rekordokhoz


    //PERZISZTENCIA miatt relációk alapján építem fel a kollekciókat
    private static void setupWorkoutsMap() {
        //get all workouts, map them by name
        List<Workout> workoutList = Workout.listAll(Workout.class);
        for (Workout wo : workoutList) {
            String key = wo.getWorkoutName();
            workouts.put(key, wo);
        }
    }

    // progress, summary of the week ...

    public static Workout getActiveWorkout() { return activeWorkout; }
    public static WorkoutDay getActiveWorkoutDay() { return activeWorkout.getActiveWorkoutDay(); }
    public static void setActiveWorkoutDay(WorkoutDay wd) {activeWorkout.setActiveWorkoutDay(wd);}

    private static void addWorkoutsToDB() {
        Workout wo = new Workout("100pushups");
        wo.setLengthInWeeks(3);
        wo.setRequiredDaysPerWeek(2);
        wo.save();

        for (int i=0; i<6; i++) {
            WorkoutDay wd = new WorkoutDay(wo);
            wd.save();  // a LocalDate itt még null, mivel új workout, a SelectWDActivityben lesz kitöltve

            for (int j=0; j<10; j++) {
                Exercise e = new Exercise("fekvőtámasz", wd, j, "10");
                e.save();   // Exercise adatai kész vannak, elmentjük
            }


        }

          // setupWorkoutsMap-et kell használni!
        //workouts.put("100pushups", wo);


        Workout wo2 = new Workout("200situps");
        wo2.setLengthInWeeks(4);
        wo2.setRequiredDaysPerWeek(3);
        wo2.save();

        for (int i=0; i<12; i++) {
            WorkoutDay wd = new WorkoutDay(wo2);
            wd.save();  // a LocalDate itt még null, mivel új workout, a SelectWDActivityben lesz kitöltve

            for (int j=0; j<10; j++) {
                Exercise e = new Exercise("felülés", wd, j, "10");
                Log.i("adding", "situp added");
                e.save();   // Exercise adatai kész vannak, elmentjük
            }


        }

         // setupWorkoutsMap-et kell használni!
        //workouts.put("100pushups", wo);
    }

    public static void setupWorkouts() {

        //csak akkor adjuk hozzá a cuccokat az adatbázishoz, hogyha üres!
        List<Workout> list = Workout.listAll(Workout.class);
        if (list.isEmpty()) {
            addWorkoutsToDB();
        }

        //itt már biztosan benne vannak az adatbázisban, kiolvassuk, mappeljük
        setupWorkoutsMap();

    }

    public static void setActiveWorkout(String s) {

        if (s == null) {
            return;
        }
        Workout wo = workouts.get(s);   // here is da problem!
        if (wo != null) {
            activeWorkout = wo;
            //wo.start();
        }

    }


}
