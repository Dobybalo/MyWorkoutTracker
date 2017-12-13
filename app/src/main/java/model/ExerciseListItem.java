package model;

import android.support.annotation.DrawableRes;

/**
 * Created by Balint on 2017. 12. 02..
 */

public class ExerciseListItem {

    public ExerciseListItem(Exercise e) {
        exercise = e;
    }

    private Exercise exercise;

    public String getExerciseName() {
        return exercise.getName();
    }

    public String getExerciseReps() {
        return exercise.getReps();
    }


    public @DrawableRes
    int getImageResource() {

        //TODO
        // ha fekvőtámasz, annak az ikonja
        // ha felülés, azé...

        if (exercise.getName().equals("fekvőtámasz")) return DataManager.pushupIcon;
        else if (exercise.getName().equals("felülés")) return DataManager.situpIcon;
        return 0;
    }

}
