package edu.kvcc.cis298.criminalintent;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.kvcc.cis298.criminalintent.database.CrimeBaseHelper;

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
	private List<Crime>
						listCrimes;
	private Context context;
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
	void addCrime(Crime crime)
	{
		listCrimes.add( crime );
	}

	public
	List<Crime> getCrimes()
	{
		return listCrimes;
	}

	public
	Crime getCrime( UUID id )
	{
		for ( Crime crime : listCrimes )
		{
			if ( crime
				.getId()
				.equals( id ) )
			{
				return crime;
			}
		}
		return null;
	}

	// private methods
	// singleton constructor
	private
	CrimeLab( Context context )
	{
		this.context = context.getApplicationContext();
		datebase = new CrimeBaseHelper(context)
			.getWritableDatabase();
		listCrimes
			= new ArrayList<Crime>();
	}
}
