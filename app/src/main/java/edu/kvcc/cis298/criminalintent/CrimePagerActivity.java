package edu.kvcc.cis298.criminalintent;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

public
class CrimePagerActivity
	extends AppCompatActivity
{
	// private variables
	// model variables
	private static final String
		EXTRA_CRIME_ID
		=
		"edu.kvcc.cis298.criminalintent.crime_id";
	private List< Crime >
		listCrimes;

	// view variables
	private ViewPager
		viewPager;

	// public methods
	public static
	Intent newIntent(
		Context packageContext,
		UUID crimeId
						 )
	{
		Intent
			intent
			= new Intent(
			packageContext,
			CrimePagerActivity.class
		);
		intent.putExtra(
			EXTRA_CRIME_ID,
			crimeId
							);
		return intent;
	}

	@Override
	protected
	void onCreate(
		@Nullable
			Bundle savedInstanceState
					 )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_crime_pager );

		UUID
			crimeId
			= (UUID) getIntent()
			.getSerializableExtra( EXTRA_CRIME_ID );

		viewPager
			= (ViewPager) findViewById(
			R.id.activity_crime_pager_view_pager
											  );

		listCrimes
			= CrimeLab
			.get( this )
			.getCrimes();
		FragmentManager
			fragmentManager
			= getSupportFragmentManager();
		viewPager.setAdapter(
			new FragmentStatePagerAdapter( fragmentManager )
			{
				@Override
				public
				Fragment getItem(int position)
				{
					Crime
						crime
						= listCrimes.get( position );
					return CrimeFragment.newInstance( crime.getId() );
				}

				@Override
				public
				int getCount()
				{
					return listCrimes.size();
				}
			}
								  );

		for (
			int
			i
			= 0;
			i
			< listCrimes.size();
			i++
			)
		{
			if (
				listCrimes
					.get( i )
					.getId()
					.equals( crimeId )
				)
			{
				viewPager.setCurrentItem( i );
				break;
			}
		}
	}


}
