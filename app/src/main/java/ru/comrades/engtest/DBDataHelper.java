package ru.comrades.engtest;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


public class DBDataHelper {
	
	private static final String DB_NAME = "bank.sqlite";
	
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

	public int getSizeTempTable() {
		int question = 0;
		String query = "SELECT COUNT(*) FROM temp";
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

	public void clearTableTemp(){
		database.execSQL("DELETE FROM " + TABLE_NAME);
	}

	public ArrayList<ListItem> getListOfUserAnswer() {

		ArrayList<ListItem> list = new ArrayList<ListItem>();

		String query2 = "SELECT id_desc, id_answer FROM temp";

		//String query = "SELECT desc_quest FROM questions WHERE id_quest = (SELECT id_desc FROM temp)";
		Cursor cursor2 = database.rawQuery(query2, null);

		Cursor cursor1 = null;
		ListItem item;
		if (cursor2.moveToFirst()) {
			do {
				item = new ListItem();
				item.setQuestion(Integer.toString(cursor2.getInt(0)));
				item.setAnswer(Integer.toString(cursor2.getInt(1)));

				//Log.d("my_app", Integer.toString(cursor2.getInt(0)));
				String query1 = "SELECT answer FROM answers WHERE id_desc =" + cursor2.getString(0) +  " ORDER BY id_desc";
				cursor1 = database.rawQuery(query1, null);
				cursor1.moveToFirst();

				Log.d("my_app", item.getAnswer() + " : "+cursor1.getString(0)) ;
				if (item.getAnswer().equals(cursor1.getString(0))) {
					item.setRightAnswer(true);
				} else {
					item.setRightAnswer(false);
				}

				Log.d("my_app", Boolean.toString(item.isRightAnswer()));
				list.add(item);

				cursor1.close();
			} while (cursor2.moveToNext());
		}
		cursor2.close();

		return list;
	}



}
