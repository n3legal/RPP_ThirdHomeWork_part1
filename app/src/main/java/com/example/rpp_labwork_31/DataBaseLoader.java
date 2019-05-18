package com.example.rpp_labwork_31;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;

public class DataBaseLoader extends SQLiteOpenHelper {

    public DataBaseLoader(Context context){
        super(context, "StudentsDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Students" +
                "(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "FIO TEXT NOT NULL," +
                "Time REAL NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void changeStudent(){
        SQLiteDatabase db = getWritableDatabase();
        Date date = new Date();
        Cursor c = db.rawQuery("SELECT MAX(id) FROM Students", null);
        c.moveToFirst();
        int id = c.getInt(0);
        String t = "Иванов Иван Иванович";
        long time = date.getTime();
        db.execSQL("UPDATE Students SET FIO = '" + t + "', Time = '" + time + "' WHERE id = " + id);
    }

    public void addStudent(){
        String arraySurname[] = {
                "Чекановкин", "Шачнов", "Карчевский", "Шардаков", "Кунегин", "Светличный", "Нефедов",
                "Щербаков", "Бембеев", "Бадаев", "Есиков", "Резников", "Алаторцев", "Шапошников", "Мурадян",
                "Ангелюк", "Ахов", "Шуляк", "Кузнецов", "Крючков", "Ким", "Жмышенко", "Попцов"
        };
        String arrayName[] = {
                "Никита", "Анатолий", "Игорь", "Алексадр", "Руслан", "Леонид", "Филипп",
                "Матвей", "Фёдор", "Григорий", "Андрей", "Сергей", "Даниил", "Дмитрий",
                "Эдуард", "Ярослав", "Василий", "Михаил", "Евгений", "Виктор", "Замба"
        };
        String arrayPatronymic [] = {
                "Максимович", "Анатольевич", "Игоревич", "Александрович", "Русланович", "Леонидович", "Филиппович",
                "Матвеевич", "Фёдорович", "Григорьевич", "Андреевич", "Сергеев", "Даниилович", "Дмитриевич", "Эдуардович",
                "Алексеевич", "Ярославоввич", "Васильевич", "Михаилович", "Евгеньевич", "Викторович"
        };
        SQLiteDatabase db = getWritableDatabase();
        Date date = new Date();
        Cursor c = db.rawQuery("SELECT MAX(id) FROM Students", null);
        c.moveToFirst();
        int id = c.getInt(0);
        ContentValues cv = new ContentValues();
        int randSurname = (int)(Math.random() * arraySurname.length), randName = (int)(Math.random() * arrayName.length), randPatronymic = (int)(Math.random() * arrayPatronymic.length);
        String FullName = arraySurname[randSurname] + " " + arrayName[randName] + " " + arrayPatronymic[randPatronymic];
        cv.put("id", id + 1);
        cv.put("FIO", FullName);
        cv.put("Time", date.getTime());
        db.insert("Students", null, cv);
        c.close();
        db.close();
    }

    public void addFiveStudents(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Students");

        for (int i = 0; i < 5; i++)
            addStudent();
        db.close();
    }

    public ArrayList<Student> readAll(){
        ArrayList<Student> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Students ORDER BY Time", null);
        if (c.moveToFirst()){
            do{
                list.add(new Student(c.getInt(0), c.getString(1)));
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }
}
