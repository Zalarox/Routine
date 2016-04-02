package com.siddhant.routine.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.siddhant.routine.Course;
import com.siddhant.routine.R;

import java.text.DateFormatSymbols;

/**
 * Created by Siddhant on 02-Apr-16.
 */
public class EditCourseDialogFragment extends DialogFragment {
    String[] daysOfWeek = DateFormatSymbols.getInstance().getShortWeekdays();
    Course course;
    int moduleCount;

    LinearLayout checkboxHolder;
    EditText courseName, totalModules;
    Button buttonMinus, buttonPlus;

    private class ButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.dialog_button_minus) {
                moduleCount--;
            } else {
                moduleCount++;
            }

            totalModules.setText(moduleCount);
            // TODO update actual course stuff here too!
            // Later make it so moduleCount == actual int in Course obj
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_course_edit, container);
        checkboxHolder = (LinearLayout) view.findViewById(R.id.dialog_checkbox_holder);
        courseName = (EditText) view.findViewById(R.id.dialog_course_name);
        totalModules = (EditText) view.findViewById(R.id.dialog_total_modules);
        buttonMinus = (Button) view.findViewById(R.id.dialog_button_minus);
        buttonPlus = (Button) view.findViewById(R.id.dialog_button_plus);
        moduleCount = Integer.parseInt(totalModules.getText().toString());

        // Add checkboxes to select days
        for(int i=1; i<=7; i++) {
            CheckBox cb = new CheckBox(getContext());
            cb.setText(daysOfWeek[i]);
            checkboxHolder.addView(cb);
        }
        buttonMinus.setOnClickListener(new ButtonOnClickListener());
        buttonPlus.setOnClickListener(new ButtonOnClickListener());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }
}
