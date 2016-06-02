package com.siddhant.routine.utilities;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siddhant.routine.classes.Course;
import com.siddhant.routine.classes.Project;

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

    public StringBuilder readFromFile() throws IOException {
        BufferedReader reader = null;
        try {
            InputStream in = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            return jsonString;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null) {
                reader.close();
            }
        }
        return null;
    }

    public ArrayList<Course> loadCourseList() throws IOException {
        ArrayList<Course> list;
        StringBuilder jsonString = readFromFile();
        if(jsonString != null) {
            list = gson.fromJson(jsonString.toString(),
                    new TypeToken<ArrayList<Course>>() {
                    }.getType());
        } else {
            list = new ArrayList<>();
        }
        return list;
    }

    public ArrayList<Project> loadProjectList() throws IOException {
        ArrayList<Project> list;
        StringBuilder jsonString = readFromFile();
        list = gson.fromJson(jsonString.toString(),
                new TypeToken<ArrayList<Course>>(){}.getType());
        return list;
    }

    public void saveList(ArrayList<?> courseList) throws IOException {

        String jsonString = gson.toJson(courseList,
                                        new TypeToken<ArrayList<?>>(){}.getType());
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
