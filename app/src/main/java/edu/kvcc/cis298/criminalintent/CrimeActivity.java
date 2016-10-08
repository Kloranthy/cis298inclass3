package edu.kvcc.cis298.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class CrimeActivity
	extends SingleFragmentActivity
{
	// public variables
	public static final String EXTRA_CRIME_ID = "edu.kvcc.cis298.criminalintent.crime_id";

	// public methods
	public static Intent newIntent(
		Context packageContext,
		UUID crimeId
											)
	{
		Intent intent = new Intent(
			packageContext,
			CrimeActivity.class
		);
		intent.putExtra(
			EXTRA_CRIME_ID,
			crimeId
							);
		return intent;
	}

	// protected methods
	@Override
	protected Fragment createFragment()
	{
		return new CrimeFragment();
	}
}
