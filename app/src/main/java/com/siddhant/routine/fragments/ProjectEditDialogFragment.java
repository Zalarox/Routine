package com.siddhant.routine.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.siddhant.routine.R;
import com.siddhant.routine.classes.Course;
import com.siddhant.routine.classes.Project;
import com.siddhant.routine.utilities.DataManager;

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

    EditText title;
    EditText projectNotes;
    Button dueButton;
    Date dueDate;
    SimpleDateFormat sdf;
    CheckBox linkedCourse;
    Spinner courseSelector;
    ViewGroup root;

    Project project;
    DataManager dm;

    OnProjectDialogCloseListener callback;

    public interface OnProjectDialogCloseListener {
        void OnProjectDialogClose();
    }

    class DateSetListener implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, monthOfYear, dayOfMonth);
            dueDate.setTime(cal.getTimeInMillis());
            dueButton.setText(sdf.format(dueDate));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (OnProjectDialogCloseListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        project.setProjectName(title.getText().toString().trim());
        project.setDueDate(dueDate);
        project.setNotes(projectNotes.getText().toString().trim());
        project.setLinkedCourse(linkedCourse.isChecked());
        dm.updateProject(project.getProjectId(), project);
        callback.OnProjectDialogClose();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        final Dialog dialog = new Dialog(getActivity());
        dialog.setTitle("Project Information");

        if((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        dialog.setContentView(root);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        DisplayMetrics displayMetrics = new DisplayMetrics();
        dialog.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        double height = displayMetrics.heightPixels / 1.5f;
        dialog.getWindow().setLayout(displayMetrics.widthPixels, (int) height);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container);

        root = (ViewGroup) view.findViewById(R.id.dialog_root);
        title = (EditText) view.findViewById(R.id.project_title);
        dueButton = (Button) view.findViewById(R.id.project_due_button);
        courseSelector = (Spinner) view.findViewById(R.id.project_linked_course);
        linkedCourse = (CheckBox) view.findViewById(R.id.course_linked_checkbox);
        projectNotes = (EditText) view.findViewById(R.id.project_notes_edittext);

        Bundle args = getArguments();
        final String projectId = args.getString("projectId");
        dm = DataManager.getInstance(getContext());
        project = dm.getProject(UUID.fromString(projectId));
        title.setText(project.getProjectName());
        sdf = new SimpleDateFormat("E, d MMMM", Locale.US);

        if(project.getDueDate() != null) {
            String date = sdf.format(project.getDueDate());
            dueButton.setText(date);
        }
        projectNotes.setText(project.getNotes());
        final ArrayList<Course> courseList = dm.getCourseList();


        ArrayAdapter<Course> adapter = new ArrayAdapter<>(getContext(),
                R.layout.support_simple_spinner_dropdown_item, courseList);
        courseSelector.setAdapter(adapter);
        initializeSpinner();

        final Calendar calendar = Calendar.getInstance();
        dueDate = project.getDueDate();

        dueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dueDate == null)
                    dueDate = new Date(calendar.getTimeInMillis());
                else {
                    String date = sdf.format(dueDate);
                    calendar.setTime(dueDate);
                    dueButton.setText(date);
                }

                DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                        new DateSetListener(), calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis()-1000);
                datePicker.show();
            }
        });

        linkedCourse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    courseSelector.setEnabled(true);
                    Course course = (Course) courseSelector.getSelectedItem();
                    project.setCourseId(course.getCourseId());
                } else {
                    courseSelector.setEnabled(false);
                }
            }
        });

        courseSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UUID courseId = courseList.get(position).getCourseId();
                if(project.getCourseId() != null) {
                    UUID oldCourseId = project.getCourseId();
                    Course course = dm.getCourse(oldCourseId);
                    course.removeDue(project.getProjectId());
                }
                project.setCourseId(courseId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    public void initializeSpinner() {
        ArrayList<Course> courseList = dm.getCourseList();
        UUID projectCourseId = project.getCourseId();
        if(projectCourseId != null && project.isLinkedCourse()) {
            int position=0;
            courseSelector.setEnabled(true);
            linkedCourse.setChecked(true);
            linkedCourse.setEnabled(true);
            for(Course course : courseList) {
                if(course.getCourseId().equals(projectCourseId)) {
                    position = courseList.indexOf(course);
                    break;
                }
            }
            courseSelector.setSelection(position);
        } else if (!courseList.isEmpty() && !project.isLinkedCourse()) {
            linkedCourse.setEnabled(true);
            linkedCourse.setChecked(false);
            courseSelector.setEnabled(false);
        } else {
            courseSelector.setEnabled(false);
        }
    }
}
