package hu.bme.aut.myworkouttracker.data;

import android.content.Context;
import android.support.annotation.DrawableRes;

import java.util.HashMap;
import java.util.List;

import hu.bme.aut.myworkouttracker.R;
import hu.bme.aut.myworkouttracker.models.Exercise;
import hu.bme.aut.myworkouttracker.models.Workout;
import hu.bme.aut.myworkouttracker.models.WorkoutDay;


public class DataManager {

    // TODO - rosszul használtam a Singletont, csupa statikus függvénye van

    private static DataManager instance = new DataManager();
    // szükségmegoldás - innen lehet hivatkozni a képekre,
    // nyilván bővítési szempontból nem a legjobb
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

    //ezt listázzuk majd ki az Activityn, ahol kiválaszthatja a user, melyiket akarja nézni most
    private static HashMap<String, Workout> workouts = new HashMap<>();
    private static Workout activeWorkout;

    /*
        Különböző adatszerkezeteket is használok, de a SugarORM
        nem ezek szerint tárolja el az adatokat, ezért kiolvasom az adatokat
        az adatbázisból, és azok alapján építem fel ami szükséges
    */

    private static void setupWorkoutsMap() {
        //get all workouts, map them by name
        List<Workout> workoutList = Workout.listAll(Workout.class);
        for (Workout wo : workoutList) {
            String key = wo.getWorkoutName();
            workouts.put(key, wo);
        }
    }

    public static Workout getActiveWorkout() { return activeWorkout; }
    public static WorkoutDay getActiveWorkoutDay() { return activeWorkout.getActiveWorkoutDay(); }
    public static void setActiveWorkoutDay(WorkoutDay wd) {activeWorkout.setActiveWorkoutDay(wd);}

    private static void addWorkoutsToDB(Context context) {
        Workout wo = new Workout(context.getString(R.string.pushup100workoutProgram));
        wo.setLengthInWeeks(3);
        wo.setRequiredDaysPerWeek(2);
        wo.save();

        for (int i=0; i<6; i++) {
            WorkoutDay wd = new WorkoutDay(wo);
            wd.save();

            for (int j=0; j<10; j++) {
                Exercise e = new Exercise(context.getString(R.string.pushup), wd, j, "10");
                e.save();
            }

        }

        Workout wo2 = new Workout(context.getString(R.string.situp200workoutProgram));
        wo2.setLengthInWeeks(4);
        wo2.setRequiredDaysPerWeek(3);
        wo2.save();

        for (int i=0; i<12; i++) {
            WorkoutDay wd = new WorkoutDay(wo2);
            wd.save();

            for (int j=0; j<10; j++) {
                Exercise e = new Exercise(context.getString(R.string.situp), wd, j, "10");
                e.save();
            }


        }

    }

    public static void setupWorkouts(Context context) {

        //teszt
        Workout dummy = new Workout();
        dummy.save();

        //csak akkor adjuk hozzá az edzésterveket(workout) az adatbázishoz, hogyha üres!
        List<Workout> list = Workout.listAll(Workout.class);
       // if (list.isEmpty()) {
        if (list.size() == 1) {
            addWorkoutsToDB(context);
        }

        //itt már biztosan benne vannak az adatbázisban, kiolvassuk, mappeljük
        setupWorkoutsMap();

    }

    public static void setActiveWorkout(String s) {

        if (s == null) {
            return;
        }
        Workout wo = workouts.get(s);
        if (wo != null) {
            activeWorkout = wo;
        }

    }


}
