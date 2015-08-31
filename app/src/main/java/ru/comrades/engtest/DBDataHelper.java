package ru.comrades.engtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


public class DBDataHelper {
	
	private static final String DB_NAME = "eng_bank.sqlite";
	
	private SQLiteDatabase database;
	private Context context;

	public static final String TABLE_NAME = "temp";
	public static final String COLUMN_NAME_ID_DESC = "id_desc";
	public static final String COLUMN_NAME_ID_ANSWER = "id_answer";
	
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


	public int getUserAnswer(int id_desc) {

		int userAnswer = 0;
		String query = "SELECT id_answer FROM temp WHERE id_desc=" + id_desc;
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
		userAnswer = cursor.getInt(0);
		cursor.close();
		return userAnswer;
	}


	public boolean getUserAnswerBoolean(int id_desc) {

		String query = "SELECT id_answer FROM temp WHERE id_desc=" + id_desc;
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
		if (cursor.getCount()==0) {
			cursor.close();
			return false;
		} else {
			cursor.close();
			return true;
		}

	}

	public void addUserAnswer(int id_desc, int id_answer) {


		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME_ID_DESC, id_desc);
		values.put(COLUMN_NAME_ID_ANSWER, id_answer);

		String query = "SELECT id_desc FROM temp WHERE id_desc=" + id_desc;
		Cursor cursor = database.rawQuery(query, null);
		if (cursor.getCount()==0) {
			database.insert(
					TABLE_NAME,
					null,
					values);
		} else {
			database.execSQL("UPDATE temp SET id_answer=" + id_answer + " WHERE id_desc=" + id_desc);
		}
			cursor.close();
	}
	public void setPrio(int id_desc, int id_answer) {

	//	database.execSQL("insert into temp set id_answer = " + id_answer + " where id_desc = " + id_desc +"on dublicate key update id_answer =  " + id);
	}


}
