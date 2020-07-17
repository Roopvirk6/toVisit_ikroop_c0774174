package com.example.tovisit_ikroop_c0774174;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PersonAdapter extends ArrayAdapter {

    Context mContext;
    int layoutresources;
    List<Person> persons;

    DatabaseHelperPerson mDataBase;

    public PersonAdapter(@NonNull Context mContext, int layoutresources , List<Person> persons , DatabaseHelperPerson mDataBase) {
        super(mContext,layoutresources,persons);

        this.mContext = mContext;
        this.layoutresources = layoutresources;
        this.persons = persons;
        this.mDataBase = mDataBase;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(layoutresources, null);
        TextView firstname = v.findViewById(R.id.tv_firstname);
        TextView lastname = v.findViewById(R.id.tv_lastname);
        TextView emailaddress = v.findViewById(R.id.tv_email);
        TextView phone = v.findViewById(R.id.tv_phone);
        TextView address = v.findViewById(R.id.tv_address);

        final Person person = persons.get(position);
        firstname.setText(person.getFirstname());
        lastname.setText(person.getLastname());
        emailaddress.setText(person.getEmailaddress());
        phone.setText(person.getPhone());
        address.setText(person.getAddress());


        v.findViewById(R.id.btn_edit_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePerson(person);
            }
        });

        v.findViewById(R.id.btn_delete_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePerson(person);
            }
        });
        return v;
    }

    private void deletePerson(final Person person) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Do you want to delete the contact? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if(mDataBase.deletePerson(person.getId()))
                    loadPersons();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadPersons() {

        Cursor cursor = mDataBase.getAllPerson();

        if(cursor.moveToFirst()){
            persons.clear();
            do{
                persons.add(new Person(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ));
            }while (cursor.moveToNext());

            cursor.close();
        }
        notifyDataSetChanged();



    }



    private void updatePerson(final Person person) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.dialog_layout_update_person, null);
        builder.setView(v);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


        final EditText etfirstname = v.findViewById(R.id.edittextfirstname);
        final EditText etlastname = v.findViewById(R.id.edittextlastname);
        final EditText etemail = v.findViewById(R.id.edittextemail);
        final EditText etphone = v.findViewById(R.id.edittextphone);
        final EditText etaddress = v.findViewById(R.id.edittextaddress);



        etfirstname.setText(person.getFirstname());
        etlastname.setText(person.getLastname());
        etemail.setText(person.getEmailaddress());
        etphone.setText(person.getPhone());
        etaddress.setText(person.getAddress());

        v.findViewById(R.id.btn_update_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name = etfirstname.getText().toString().trim();
                String last_name = etlastname.getText().toString().trim();
                String email_address = etemail.getText().toString().trim();
                String phone_number = etphone.getText().toString().trim();
                String address_person = etaddress.getText().toString().trim();



                if(first_name.isEmpty()){
                    etfirstname.setError("name field is empty");
                    etfirstname.requestFocus();
                    return;
                }

                if(last_name.isEmpty()){
                    etlastname.setError("name field is empty");
                    etlastname.requestFocus();
                    return;
                }
                if(email_address.isEmpty())
                {
                    etemail.setError("name field is empty");
                    etemail.requestFocus();
                    return;
                }
                if(phone_number.isEmpty()){
                    etphone.setError("name field is empty");
                    etphone.requestFocus();
                    return;
                }

                if(address_person.isEmpty()){
                    etaddress.setError("name field is empty");
                    etaddress.requestFocus();
                    return;
                }


                if(mDataBase.updatePerson(person.getId(),first_name,last_name, email_address, phone_number,address_person)){
                    Toast.makeText(mContext, "Contact updated", Toast.LENGTH_SHORT).show();
                    loadPersons();
                }
                else
                    Toast.makeText(mContext, "Contact not updated", Toast.LENGTH_SHORT).show();


                alertDialog.dismiss();

            }
        });



    }
}
