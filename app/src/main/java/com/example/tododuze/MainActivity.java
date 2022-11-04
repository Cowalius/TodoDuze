package com.example.tododuze;

import static com.example.tododuze.R.string.empty_string_msg;
import static com.example.tododuze.R.string.task_removed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button button;
    private View snackBar;
     int zrobione =0;
     int ile=0;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.addTask:
                Toast.makeText(this, "Add button is at the bottom of the app", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.deleteTask:
                Toast.makeText(this, " Hold the element of the list you want to delete", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.Process:
                int dozrobienia = ile - zrobione;
                Toast.makeText(this, "ammount of acctions left to do:"+dozrobienia, Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tool=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tool);
        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);
        snackBar = findViewById(R.id.snackBar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(view);
            }
        });
        items = new ArrayList<>();
        itemsAdapter= new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked,items);

        listView.setAdapter(itemsAdapter);
        setUpListViewListener();
    }

    private void setUpListViewListener() {
        //jesli uzytkownik trzyma palec na jednym z zadan zostanie ono usuniety
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //toast wiadomosci
                Context context = getApplicationContext();
                Toast.makeText(context, task_removed,Toast.LENGTH_SHORT).show();

                items.remove(i);//usuniecie zadania wybranego
                itemsAdapter.notifyDataSetChanged();//zeby napewno bylo odswiezone
                return true;
            }
        });



    }



    private void addItem(View view) {
        EditText input=findViewById(R.id.editText2);
        input.setRawInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        String itemtext=input.getText().toString();

        //by nie wyslal pustego stringa
        if(!(itemtext.equals(""))){
            itemsAdapter.add(itemtext);
            input.setText("");
            Snackbar mySnackBar = Snackbar.make(snackBar, R.string.Task_added,Snackbar.LENGTH_SHORT);
            mySnackBar.show();
            ile++;
        }else{
            Toast.makeText(getApplicationContext(), empty_string_msg,Toast.LENGTH_LONG).show();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView check = (CheckedTextView) view;
                //jesli tekst z inputu = youtbue to przenies na strone youtuba
                if(itemtext.equals("Youtube ")||itemtext.equals("youtube.com ")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.google.android.youtube");
                    startActivity(intent);
                    check.setChecked(!check.isChecked());
                }else{
                    check.setChecked(!check.isChecked());
                    if(check.isChecked()==true){
                        zrobione++;
                    }else{
                        zrobione--;
                    }
                }

            }
        });

    }

}