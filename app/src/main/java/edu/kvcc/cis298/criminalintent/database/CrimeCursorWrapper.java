package edu.kvcc.cis298.criminalintent.database;


import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;
import java.util.UUID;

import edu.kvcc.cis298.criminalintent.Crime;

public
class CrimeCursorWrapper
	extends CursorWrapper
{

	public
	CrimeCursorWrapper( Cursor cursor )
	{
		super( cursor );
	}

	public
	Crime getCrime()
	{
		String uuid = getString(
			getColumnIndex(
				CrimeDbScema
					.CrimeTable
					.Cols
					.UUID
							  )
									  );
		String title = getString(
			getColumnIndex(
				CrimeDbScema
					.CrimeTable
					.Cols
					.TITLE
							  )
									  );
		long date = getLong(
			getColumnIndex(
				CrimeDbScema
					.CrimeTable
					.Cols
					.DATE
							  )
								 );
		int isSolved = getInt(
			getColumnIndex(
				CrimeDbScema
					.CrimeTable
					.Cols
					.SOLVED
							  )
									);

		Crime crime
			= new Crime(
			UUID
				.fromString( uuid )
		);
		crime.setTitle(title);
		crime.setDate(
			new Date(date)
						 );
		crime.setSolved(
			isSolved != 0
							);
		return crime;
	}
}
