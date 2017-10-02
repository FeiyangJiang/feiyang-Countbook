package com.example.feiyang_countbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
 * This class is the main activity
 * Consult Yiding Fan, Zijian He, Qikai Lu
 */

public class Display extends Activity {

    private static final String FILENAME = "file.sav";
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private ListView counter_List;
    private int editLocation=-1;
    private ArrayList<CounterBook> CounterBooks;
    private ArrayAdapter<CounterBook> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_counter);
        final Button editButton = (Button) findViewById(R.id.edit);
        Button addButton = (Button) findViewById(R.id.add);
        Button incButton = (Button) findViewById(R.id.increase);
        Button decButton = (Button) findViewById(R.id.decrease);
        Button delButton = (Button) findViewById(R.id.delete);
        Button resButton = (Button) findViewById(R.id.reset);
        counter_List= (ListView) findViewById(R.id.counterList);
        AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                editLocation=position;
            }
        };
        counter_List.setOnItemClickListener(mMessageClickedHandler);
        addButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {setResult(RESULT_OK);
                add_view(v);
                loadFromFile();
                adapter.notifyDataSetChanged();
            }

        });
        editButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (editLocation != -1){
                    setResult(RESULT_OK);
                    edit_view();
                    loadFromFile();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        incButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (editLocation != -1){
                    setResult(RESULT_OK);
                    CounterBook s= CounterBooks.get(editLocation);
                    s.increase();
                    saveInFile();
                    adapter.notifyDataSetChanged();
                }
            }

        });
        decButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (editLocation != -1){
                    setResult(RESULT_OK);
                    CounterBook s= CounterBooks.get(editLocation);
                    s.decrease();
                    adapter.notifyDataSetChanged();
                    saveInFile();
                }
            }

        });
        delButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (editLocation != -1){
                    setResult(RESULT_OK);
                    CounterBooks.remove(editLocation);
                    adapter.notifyDataSetChanged();
                    saveInFile();
                }
            }

        });
        resButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (editLocation != -1){
                    setResult(RESULT_OK);
                    CounterBook s= CounterBooks.get(editLocation);
                    int init=s.getInit();
                    s.C_Value(init);
                    adapter.notifyDataSetChanged();
                    saveInFile();
                }
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
            CounterBooks = gson.fromJson(in, listType);
        } catch (FileNotFoundException e) {
            CounterBooks = new ArrayList<CounterBook>();
        }
    }

    /**
     * Save the result to file
     */
    private void saveInFile() {
        try {FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter( new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(CounterBooks,out);
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
     * start the activity and create the adapter
     */
    protected void onStart() {
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<CounterBook>(this, R.layout.list_counter_item, CounterBooks);
        counter_List.setAdapter(adapter);
    }

    /**
     * Switch edit activity
     */
    public void edit_view(){
        Intent intent = new Intent(this, Edit.class);
        intent.putExtra(EXTRA_MESSAGE, editLocation);
        startActivityForResult(intent,RESULT_OK);
    }

    /**
     * Switch add activity
     */
    public void add_view(View view) {
        Intent intent = new Intent(this, Add.class);
        startActivityForResult(intent, RESULT_OK);
    }
}
