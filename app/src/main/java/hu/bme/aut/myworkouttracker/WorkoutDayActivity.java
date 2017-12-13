package hu.bme.aut.myworkouttracker;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import model.DataManager;
import model.Exercise;
import model.ExerciseListAdapter;
import model.ExerciseListItem;
import model.WorkoutDay;

public class WorkoutDayActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExerciseListAdapter adapter;

    private Button doneButton;

    private WorkoutDay activeDay;

    @Override
    protected void onPause() {
        super.onPause();
        activeDay.save();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_day);

        activeDay = DataManager.getActiveWorkoutDay();

        doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //gombnyomásra kitöröljük a lista első elemét

                if (adapter.getItemCount() != 0) {

                    if (!activeDay.hasStarted()) activeDay.startDay();

                    activeDay.finishExercise();

                    adapter.removeFirstItemFromList();
                    //list.remove(position);
                    recyclerView.removeViewAt(0);
                    adapter.notifyItemRemoved(0);
                    adapter.notifyItemRangeChanged(0, adapter.getItemCount());

                    recyclerView.smoothScrollToPosition(0);
                }

                if (adapter.getItemCount() == 0) {
                    activeDay.finishDay();

                    //TODO - egyelőre a Calendar-ba, amúgy a ProgressActivitybe!!
                    Intent intent = new Intent(WorkoutDayActivity.this, CalendarActivity.class);
                    startActivity(intent);
                }
            }
        });

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.MainRecyclerView);
        adapter = new ExerciseListAdapter();
        //TODO loadItemsInBackground();

        //egyelőre
        //loadItems();
        loadItemsInBackground();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    private void loadItemsInBackground() {
        new AsyncTask<Void, Void, List<ExerciseListItem>>() {

            @Override
            protected List<ExerciseListItem> doInBackground(Void... voids) {
                List<ExerciseListItem> exercises = new ArrayList<>();
                WorkoutDay activeWorkoutDay = DataManager.getActiveWorkoutDay();
                List<Exercise> list = activeWorkoutDay.getTodaysExercises();
                for (Exercise e : list) {
                    ExerciseListItem eli = new ExerciseListItem(e);
                    exercises.add(eli);
                }
                return exercises;
                //return ExerciseListItem.listAll(ExerciseListItem.class);
            }

            @Override
            protected void onPostExecute(List<ExerciseListItem> exercises) {
                super.onPostExecute(exercises);
                adapter.update(exercises);
            }
        }.execute();
    }

    /*
    private void loadItems() {
        List<ExerciseListItem> exercises = new ArrayList<>();
        WorkoutDay activeWorkoutDay = DataManager.getActiveWorkoutDay();
        List<Exercise> list = activeWorkoutDay.getTodaysExercises();
        for (Exercise e : list) {
            ExerciseListItem eli = new ExerciseListItem(e);
            exercises.add(eli);
        }

        adapter.update(exercises);
    }
    */
}