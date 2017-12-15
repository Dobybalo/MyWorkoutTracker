package model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class WorkoutDay extends SugarRecord<WorkoutDay> {

    private Integer numExercisesDone = 0;      // hány feladatot végeztünk már el, a maiak közül
    private boolean startedDay = false;
    private boolean finishedDay = false;

    //PERZISZTENCIA miatt
    private Workout ownerWorkout;
    private long dateLong;  // az adatbázisban úgyis longként lesz eltárolva, ilyen módon számolunk

    public static List<WorkoutDay> getWorkoutDaysListForWorkout(Workout wo) {
        List<WorkoutDay> list = WorkoutDay.find(WorkoutDay.class, "owner_workout = ?",  wo.getId().toString()+"");
        return list;
    }

    @Ignore
    private List<Exercise> todaysExercises = new ArrayList<>();

    public WorkoutDay() {}

    public WorkoutDay(Workout wo) {
        ownerWorkout = wo;
    }

    private void setupTodaysExercises() {

        List<Exercise> exerciseList =
                Exercise.find(Exercise.class,
                "workout_day = ? and sequence >= ?",
                this.getId().toString(),
                numExercisesDone.toString());

        //rendezzük a sequence szerint növekvő sorrendbe
        Collections.sort(exerciseList, new Comparator<Exercise>() {
            @Override
            public int compare(Exercise o1, Exercise o2) {
                return o1.getSequence().compareTo(o2.getSequence());
            }
        });

        todaysExercises = exerciseList;
    }

    public void setDate(LocalDate date) {
        Date utilDate = date.toDateTimeAtStartOfDay().toDate();
        dateLong = utilDate.getTime();
    }

    public LocalDate getDate() {
        Date utilDate = new Date(dateLong);
        LocalDate date = new LocalDate(utilDate);
        return date;
    }

    public List<Exercise> getTodaysExercises() {
        setupTodaysExercises(); // DB olvasás
        return todaysExercises;
    }

    public boolean hasStarted() { return startedDay; }
    public boolean hasFinished() { return finishedDay; }

    public void finishExercise() {
        numExercisesDone++;
    }

    public void startDay() {
        startedDay = true;
    }

    //amikor elvégezte az utolsó feladatot is
    public void finishDay() {
        finishedDay = true;
    }

}
