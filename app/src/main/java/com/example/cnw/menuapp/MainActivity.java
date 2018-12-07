package com.example.cnw.menuapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
ListView lview;
ArrayList<String> courses;
ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       initAdapter();
       registerForContextMenu(lview);
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        new MenuInflater(this).inflate(R.menu.context,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        new MenuInflater(this).inflate(R.menu.options,menu);
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.add:
                Add();
                return true;
            case R.id.reset:
              /*  AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("You want to capitalize");
                builder.setTitle("Alert");
                builder.setNeutralButton("click here",new DialogInterface.OnClickListener()
                {
                    public void onClick( DialogInterface interfaceD, int i)
                    {
                        Toast.makeText(MainActivity.this,"reset is requested",Toast.LENGTH_LONG).show();
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();*/
              initAdapter();
             return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Add() {
        final View addView =getLayoutInflater().inflate(R.layout.add,null);
        new AlertDialog.Builder(this)
                .setTitle("Add a word")
                . setView(addView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       ArrayAdapter<String> adapter =MainActivity.this.adapter;
                        EditText toadd=addView.findViewById((R.id.title));
                                adapter.add(toadd.getText().toString());

                    }
                })
                .setNegativeButton("CAncel",null)
                .show();
    }

    private void initAdapter()
    {
        lview=findViewById(R.id.list);
        InputStream in =getResources().openRawResource(R.raw.courses);
        Scanner scanner=new Scanner(in);
        courses =new ArrayList<>();
        while(scanner.hasNext())
        {
            courses.add(scanner.nextLine());

        }
        adapter =new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,courses);
        lview.setAdapter(adapter);


    }
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        ;
        switch(item.getItemId())
        {
            case R.id.cap:
                String word =courses.get(info.position);
                word=word.toUpperCase();
                adapter.remove(courses.get(info.position));
                adapter.insert(word,info.position);
                return true;
            case R.id.Remove:
                adapter.remove(courses.get(info.position));
                return true;
        }
        return super.onContextItemSelected(item);
    }
}

