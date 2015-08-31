package ru.comrades.engtest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class DBDataHelper {
	
	private static final String DB_NAME = "eng_bank.sqlite";
	
	private SQLiteDatabase database;
	private Context context;

	
	public DBDataHelper(Context context) {
		this.context = context;
		openDB();
	}
	
	public void openDB() {
	 	DBOpenHelper dbOpenHelper = new DBOpenHelper(context, DB_NAME);
	 	database = dbOpenHelper.openDataBase();
	}
		
	///////////////////////
	public String getQuestion(int id) {
		 	
	        String question = null;
	        String query = "SELECT desc_quest FROM questions WHERE id_quest=" + id;
		 	Cursor cursor = database.rawQuery(query, null);
		 	cursor.moveToFirst();
            question = cursor.getString(0);
		 	cursor.close();
		 	return question;
	}

	public int getSize() {

		int question = 0;
		String query = "SELECT COUNT(*) FROM questions";
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
		question = cursor.getInt(0);
		cursor.close();
		return question;
	}

	public ArrayList<String> getVariantsOfTheAnswer(int id) {

	 	ArrayList<String> list = new ArrayList<String>();
        String query = "SELECT desc_choice FROM choice WHERE id_quest=" + id;
	 	Cursor cursor = database.rawQuery(query, null);
	 	if (cursor.moveToFirst()) {
	            do {
                    list.add(cursor.getString(0));
	            } while (cursor.moveToNext());
	    }
	 	cursor.close();
	 	return list;
	}

    public int getRightAnswer(int id) {

        int rightAnswer = 0;
        String query = "SELECT answer FROM answers WHERE id_desc=" + id;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        rightAnswer = cursor.getInt(0);
        cursor.close();
        return rightAnswer;
    }



}
