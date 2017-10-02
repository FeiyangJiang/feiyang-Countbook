package com.example.feiyang_countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
 * Edit activity
 * Consult Yiding Fan, Zijian He, Qikai Lu
 */
public class Edit extends Activity {
    private static final String FILENAME = "file.sav";
    private int editLocation=0;
    private ArrayList<CounterBook> counterBook_list;
    private CounterBook editCounterBook;
    private EditText nameT;
    private EditText initT;
    private EditText curT;
    private EditText comT;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_counter);
        Button subButton = (Button) findViewById(R.id.submit);
        Intent intent = getIntent();
        editLocation =  intent.getIntExtra(Display.EXTRA_MESSAGE,0);
        nameT = (EditText) findViewById(R.id.name);
        initT = (EditText) findViewById(R.id.init);
        comT = (EditText) findViewById(R.id.com);
        curT = (EditText) findViewById(R.id.current);
        subButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK);
                String name=nameT.getText().toString();
                int init=(int) Integer.parseInt(initT.getText().toString());
                int cur=(int) Integer.parseInt(curT.getText().toString());
                String com=comT.getText().toString();
                editCounterBook.Comment(com);
                editCounterBook.Init(init);
                editCounterBook.C_Value(cur);
                editCounterBook.Name(name);
                saveInFile();
                end();
            }
        });
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

    /**
     * Loading the file when activity starts
     */
    protected void onStart() {
        super.onStart();
        loadFromFile();
        editCounterBook = counterBook_list.get(editLocation);
        nameT.setText(editCounterBook.getName(), TextView.BufferType.EDITABLE);
        initT.setText(Integer.toString(editCounterBook.getInit()), TextView.BufferType.EDITABLE);;
        comT.setText(editCounterBook.getComment(), TextView.BufferType.EDITABLE);;
        curT.setText(Integer.toString(editCounterBook.getCurr()), TextView.BufferType.EDITABLE);
    }
    /**
     * end this activity
     */
    private void end(){
        this.finish();
    }
}