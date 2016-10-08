package edu.kvcc.cis298.criminalintent;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab
{
	// private variables
	private String TAG = this.getClass()
									 .getName();
	private List<Crime> listCrimes;
	// singleton instance
	private static CrimeLab instance;

	// public methods
	// singleton access
	public static CrimeLab get(Context context)
	{

		if(CrimeLab.instance == null)
		{
			instance = new CrimeLab(context);
		}

		return instance;
	}

	public List<Crime> getCrimes()
	{
		return listCrimes;
	}

	public Crime getCrime(UUID id)
	{
		for(Crime crime : listCrimes)
		{
			if(crime.getId().equals(id))
			{
				return crime;
			}
		}
		return null;
	}

	// private singleton constructor
	private CrimeLab(Context context)
	{
		listCrimes = new ArrayList<Crime>();
		for(int i = 0; i < 100; i++)
		{
			Crime crime = new Crime();
			crime.setTitle("Crime #" + i);
			crime.setSolved(i%2==0);
			listCrimes.add(crime);
		}
	}
}
