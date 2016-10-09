package edu.kvcc.cis298.criminalintent;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.kvcc.cis298.criminalintent.database.CrimeBaseHelper;
import edu.kvcc.cis298.criminalintent.database.CrimeCursorWrapper;
import edu.kvcc.cis298.criminalintent.database.CrimeDbScema.CrimeTable;


public
class CrimeLab
{
	// private variables
	// singleton instance
	private static CrimeLab
		instance;
	private String
		TAG
		= this
		.getClass()
		.getName();
	private Context
		context;
	private SQLiteDatabase
		datebase;

	// public methods
	// singleton access
	public static
	CrimeLab get( Context context )
	{

		if ( CrimeLab.instance
			  == null )
		{
			instance
				= new CrimeLab( context );
		}

		return instance;
	}

	public
	void addCrime( Crime crime )
	{
		ContentValues
			values
			= getContentValues( crime );

		datebase.insert(
			CrimeTable.NAME,
			null,
			values
							);
	}

	public
	List<Crime> getCrimes()
	{
		List<Crime> listCrimes = new ArrayList<Crime>();

		CrimeCursorWrapper cursor = queryCrimes( null, null );

		try
		{
			cursor.moveToFirst();
			while(!cursor.isAfterLast())
			{
				listCrimes.add( cursor.getCrime() );
				cursor.moveToNext();
			}
		}
		finally
		{
			cursor.close();
		}
		return listCrimes;
	}

	public
	Crime getCrime( UUID id )
	{
		CrimeCursorWrapper cursor = queryCrimes(
			CrimeTable.Cols.UUID + " = ?",
			new String[]
				{
					id.toString()
				}
															);
		try
		{
			if ( cursor.getCount() == 0 )
			{
				return null;
			}

			cursor.moveToFirst();
			return cursor.getCrime();
		}
		finally
		{
			cursor.close();
		}
	}

	public
	void updateCrime( Crime crime )
	{
		String
			uuid
			= crime
			.getId()
			.toString();

		ContentValues
			values
			= getContentValues( crime );

		datebase.update(
			CrimeTable.NAME,
			values,
			CrimeTable.Cols.UUID
			+ " = ?",
			new String[]
				{
					uuid
				}
							);
	}

	// private methods
	// singleton constructor
	private
	CrimeLab( Context context )
	{
		this.context
			= context.getApplicationContext();
		datebase
			= new CrimeBaseHelper( context )
			.getWritableDatabase();

	}

	private static
	ContentValues
	getContentValues( Crime crime )
	{
		ContentValues
			values
			= new ContentValues();
		values.put(
			CrimeTable.Cols.UUID,
			crime
				.getId()
				.toString()
					 );
		values.put(
			CrimeTable.Cols.TITLE,
			crime.getTitle()
					 );
		values.put(
			CrimeTable.Cols.DATE,
			crime
				.getDate()
				.getTime()
					 );
		values.put(
			CrimeTable.Cols.SOLVED,
			crime.isSolved()
			? 1
			: 0
					 );
		return values;
	}

	private
	CrimeCursorWrapper
	queryCrimes(
		String whereClause,
		String[] whereArgs
				  )
	{
		Cursor
			cursor
			= datebase.query(
				CrimeTable.NAME,
				null,			// Columns - null selects all columns
				whereClause,
				whereArgs,
				null,			// groupBy
				null,			// having
				null			// orderBy
								 );
		return
			new CrimeCursorWrapper(
				cursor
			);
	}
}
