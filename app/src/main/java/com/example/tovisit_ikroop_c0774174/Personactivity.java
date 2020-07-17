package com.example.tovisit_ikroop_c0774174;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class Personactivity extends AppCompatActivity {

    DatabaseHelperPerson mDataBase;
    List<Person> personList;

    List<Person> userSearch;
    ListView listView;

    ArrayList<String> arrayList = new ArrayList<>();


    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personactivity);
        searchView = findViewById(R.id.searchbar);

        listView = findViewById(R.id.lvpersons);
        personList = new ArrayList<>();
        mDataBase =   new DatabaseHelperPerson(this);

        userSearch = new ArrayList<>();


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(arrayAdapter);


        loadPerson();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()){
                    userSearch.clear();
                    for(int i = 0; i< personList.size(); i++) {
                        Person contact = personList.get(i);
                        if (contact.firstname.contains(newText)) {
                            userSearch.add(contact);
                        }
                    }
                    PersonAdapter pAdapter = new PersonAdapter(Personactivity.this, R.layout.list_layout_person, userSearch, mDataBase);
                    listView.setAdapter(pAdapter);
                }
                if(newText.isEmpty()) {
                    PersonAdapter pAdapter = new PersonAdapter(Personactivity.this, R.layout.list_layout_person, userSearch, mDataBase);
                    listView.setAdapter(pAdapter);
                }
                return  false;
            }

        });


    }






    private void loadPerson() {
        Cursor cursor = mDataBase.getAllPerson();
        if(cursor.moveToFirst()){
            do {
                personList.add(new Person(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ));


            }while (cursor.moveToNext());
            cursor.close();


            PersonAdapter personAdapter = new PersonAdapter(this, R.layout.list_layout_person, personList, mDataBase);
            listView.setAdapter(personAdapter);

        }
    }



}