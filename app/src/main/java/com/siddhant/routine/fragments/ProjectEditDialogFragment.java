package com.siddhant.routine.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.siddhant.routine.R;
import com.siddhant.routine.classes.Course;
import com.siddhant.routine.utilities.CourseManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Siddhant on 04-Jun-16.
 */
public class ProjectEditDialogFragment extends DialogFragment {

    Button dueButton;
    DatePicker datePicker;
    Date dueDate;
    SimpleDateFormat sdf;
    CheckBox linkedCourse;
    Spinner courseSelector;
    ViewGroup root;
    UUID courseId;

    public ProjectEditDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container);
        getDialog().setTitle("Project Information");
        root = (ViewGroup) view.findViewById(R.id.dialog_root);
        dueButton = (Button) view.findViewById(R.id.project_due_button);
        courseSelector = (Spinner) view.findViewById(R.id.project_linked_course);
        datePicker = (DatePicker) view.findViewById(R.id.project_date_picker);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        sdf = new SimpleDateFormat("E, d MMMM", Locale.US);

        linkedCourse = (CheckBox) view.findViewById(R.id.course_linked_checkbox);

        CourseManager cm = CourseManager.getInstance(getContext());
        final ArrayList<Course> list = cm.getCourseList();

        if(!list.isEmpty()) {
            linkedCourse.setEnabled(true);
            linkedCourse.setChecked(true);
            courseSelector.setEnabled(true);
            courseSelector.setSelection(0);
        } else {
            courseSelector.setEnabled(false);
        }

        ArrayAdapter<Course> adapter = new ArrayAdapter<>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, list);
        courseSelector.setAdapter(adapter);
        courseSelector.setPrompt("Select course");

        final Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                dueDate = new Date(calendar.getTimeInMillis());
                String date = sdf.format(dueDate);
                dueButton.setText(date);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    Fade fade = new Fade();
                    fade.setDuration(200);
                    TransitionManager.beginDelayedTransition(root, fade);
                }
                view.setVisibility(View.GONE);
                dueButton.setVisibility(View.VISIBLE);
            }
        });

        dueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    Fade fade = new Fade();
                    fade.setDuration(200);
                    TransitionManager.beginDelayedTransition(root, fade);
                }
                v.setVisibility(View.GONE);
                datePicker.setVisibility(View.VISIBLE);
            }
        });


        linkedCourse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    courseSelector.setEnabled(true);
                } else {
                    courseSelector.setEnabled(false);
                }

            }
        });

        courseSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                courseId = list.get(position).getCourseId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }
}
