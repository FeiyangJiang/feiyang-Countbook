package com.example.feiyang_countbook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Feiyang Jiang on 2017-09-30.
 * The calss is for adding a new CounterBook
 * Consult Yiding Fan, Zijian He, Qikai Lu
 */
public class Add extends Activity {

    private static final String FILENAME = "file.sav";

    private ArrayList<CounterBook> counterBook_list;
    private EditText nameT;
    private EditText initT;
    private EditText comT;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_counter);
        Button finButton = (Button) findViewById(R.id.finish);
        nameT = (EditText) findViewById(R.id.name);
        initT = (EditText) findViewById(R.id.init);
        comT = (EditText) findViewById(R.id.com);
        finButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {setResult(RESULT_OK);
                if (!(initT.getText().toString().equals("")) && !(nameT.getText().toString().equals(""))){
                String name=nameT.getText().toString();
                int init=(int) Integer.parseInt(initT.getText().toString());
                String com=comT.getText().toString();
                CounterBook newcounter =new CounterBook(name,init,com);
                counterBook_list.add(newcounter);
                saveInFile();}
                end();
            }
        });
    }
    /**
     * Loading the file when activity starts
     */
    protected void onStart() {
        super.onStart();
        loadFromFile();
    }

    /**
     * End of the activity
     */
    private void end(){
        this.finish();
    }

    /**
     * Loading the old CounterBook
     */
    private void loadFromFile() {
        try {FileInputStream fis=openFileInput(FILENAME);
            BufferedReader in= new BufferedReader(new InputStreamReader(fis));
            Gson gson= new Gson();
            Type listType =new TypeToken<ArrayList<CounterBook>>(){}.getType();
            counterBook_list = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            counterBook_list = new ArrayList<CounterBook>();
        }
    }

    /**
     * Save the result to file
     */
    private void saveInFile() {
        try {FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter( new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(counterBook_list,out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}