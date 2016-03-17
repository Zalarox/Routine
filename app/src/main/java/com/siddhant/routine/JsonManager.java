package com.siddhant.routine;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Siddhant on 12-Mar-16.
 */
public class JsonManager {
    private String fileName;
    private Context context;
    Gson gson = new Gson();

    public JsonManager(Context context, String fileName) {
        this.fileName = fileName;
        this.context = context;
    }

    public ArrayList<Course> loadCourses() throws IOException {
        ArrayList<Course> courseList = new ArrayList<Course>();
        BufferedReader reader = null;
        try {
            InputStream in = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;

            while((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            courseList = gson.fromJson(jsonString.toString(),
                    new TypeToken<ArrayList<Course>>(){}.getType());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null) {
                reader.close();
            }
        }

        return courseList;
    }

    public void saveCourses(ArrayList<Course> courseList) throws IOException {

        String jsonString = gson.toJson(courseList,
                                        new TypeToken<ArrayList<Course>>(){}.getType());
        Writer writer = null;
        try {
            OutputStream out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(jsonString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "File was not found.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "IO Exception occurred!", Toast.LENGTH_SHORT).show();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

    }

}
