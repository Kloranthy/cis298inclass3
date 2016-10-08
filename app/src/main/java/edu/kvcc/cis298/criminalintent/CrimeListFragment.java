package edu.kvcc.cis298.criminalintent;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public
class CrimeListFragment
	extends Fragment
{
	// private variables
	// model variables
	private static final String SAVED_SUBTITLE_VISIBLE="subtitle";
	private static final int
		REQUEST_CRIME
		= 1;
	// view variables
	private RecyclerView
		recyclerView;
	private CrimeAdapter
		adapter;
	private boolean
		subtitleVisible;


	// public methods


	@Override
	public
	void onCreate(
		@Nullable
			Bundle savedInstanceState
					 )
	{
		super.onCreate( savedInstanceState );
		setHasOptionsMenu( true );
	}

	@Nullable
	@Override
	public
	View onCreateView(
		LayoutInflater inflater,
		@Nullable
			ViewGroup container,
		@Nullable
			Bundle savedInstanceState
						  )
	{
		View
			view
			= inflater.inflate(
			R.layout.fragment_crime_list,
			container,
			false
									);
		recyclerView
			= (RecyclerView) view.findViewById(
			R.id.fragment_crime_list_recycler_view
														 );
		recyclerView.setLayoutManager(
			new LinearLayoutManager( getActivity() )
											  );

		if(savedInstanceState != null)
		{
			subtitleVisible = savedInstanceState.getBoolean(
				SAVED_SUBTITLE_VISIBLE
																		  );
		}

		updateUI();

		return view;
	}

	@Override
	public
	void onResume()
	{
		super.onResume();
		updateUI();
	}

	@Override
	public
	void onSaveInstanceState( Bundle savedInstanceState )
	{
		super.onSaveInstanceState( savedInstanceState );
		savedInstanceState.putBoolean(
			SAVED_SUBTITLE_VISIBLE,
			subtitleVisible
											  );
	}

	@Override
	public
	void onActivityResult(
		int requestCode,
		int resultCode,
		Intent data
								)
	{
		if ( requestCode
			  == REQUEST_CRIME )
		{
			// handle result
		}
	}

	@Override
	public
	void onCreateOptionsMenu(
		Menu menu,
		MenuInflater inflater
									)
	{
		super.onCreateOptionsMenu(
			menu,
			inflater
										 );

		inflater.inflate(
			R.menu.fragment_crime_list,
			menu
							 );

		MenuItem itemSubtitle = menu.findItem(
			R.id.fragment_crime_list_menu_item_show_subtitle
														 );
		if(subtitleVisible)
		{
			itemSubtitle.setTitle(
				R.string.fragment_crime_list_menu_item_hide_subtitle
										);
		}
		else
		{
			itemSubtitle.setTitle(
				R.string.fragment_crime_list_menu_item_show_subtitle
										);
		}
	}

	@Override
	public
	boolean onOptionsItemSelected( MenuItem item )
	{
		switch(item.getItemId())
		{
			case R.id.fragment_crime_list_menu_item_new_crime :
				Crime crime = new Crime();
				CrimeLab
					.get(getActivity())
					.addCrime(crime);
				Intent intent = CrimePagerActivity
					.newIntent(
						getActivity(),
						crime.getId()
								 );
				startActivity(intent);
				return true;
			case R.id.fragment_crime_list_menu_item_show_subtitle :
				subtitleVisible = !subtitleVisible;
				getActivity().invalidateOptionsMenu();
				updateSubtitle();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	// private methods
	private
	void updateUI()
	{
		CrimeLab
			crimeLab
			= CrimeLab.get( getActivity() );
		List< Crime >
			listCrimes
			= crimeLab.getCrimes();

		if ( adapter
			  == null )
		{
			adapter
				= new CrimeAdapter( listCrimes );
			recyclerView.setAdapter( adapter );
		}
		else
		{
			adapter.notifyDataSetChanged();
		}
		updateSubtitle();
	}

	private void updateSubtitle()
	{
		CrimeLab crimeLab = CrimeLab.get( getActivity() );
		int crimeCount = crimeLab
			.getCrimes()
			.size();
		String subtitle = getString(
			R.string.subtitle_format,
			crimeCount
											);

		if(!subtitleVisible)
		{
			subtitle = null;
		}

		AppCompatActivity activity =
			(AppCompatActivity) getActivity();
		activity.getSupportActionBar().setSubtitle( subtitle );
	}

	// private classes
	private
	class CrimeHolder
		extends RecyclerView.ViewHolder
		implements View.OnClickListener
	{
		// private variables
		// model variables
		private Crime
			crime;
		// view variables
		private TextView
			tvTitle,
			tvDate;
		private CheckBox
			cbSolved;

		// public constructors
		public
		CrimeHolder(View viewItem)
		{
			super( viewItem );

			viewItem.setOnClickListener( this );

			tvTitle
				= (TextView) viewItem.findViewById(
				R.id.list_item_crime_tv_title
															 );
			tvDate
				= (TextView) viewItem.findViewById(
				R.id.list_item_crime_tv_date
															 );
			cbSolved
				= (CheckBox) viewItem.findViewById(
				R.id.list_item_crime_cb_solved
															 );
		}

		// public methods
		@Override
		public
		void onClick(View view)
		{
			Intent
				intent
				= CrimePagerActivity.newIntent(
				getActivity(),
				crime.getId()
														);
			startActivityForResult(
				intent,
				REQUEST_CRIME
										 );
		}


		public
		void bindCrime(Crime crime)
		{
			this.crime
				= crime;
			tvTitle.setText( crime.getTitle() );
			tvDate.setText(
				crime
					.getDate()
					.toString()
							  );
			cbSolved.setChecked( crime.isSolved() );
		}
	}

	private
	class CrimeAdapter
		extends RecyclerView.Adapter< CrimeHolder >
	{
		// private variables
		private List< Crime >
			listCrimes;

		// public constructors
		public
		CrimeAdapter(List< Crime > listCrimes)
		{
			this.listCrimes
				= listCrimes;
		}

		// public methods
		@Override
		public
		CrimeHolder onCreateViewHolder(
			ViewGroup parent,
			int viewType
												)
		{
			LayoutInflater
				layoutInflater
				= LayoutInflater.from( getActivity() );
			View
				view
				= layoutInflater.inflate(
				R.layout.fragment_crime_list_item_crime,
				parent,
				false
												);

			return new CrimeHolder( view );
		}

		@Override
		public
		void onBindViewHolder(
			CrimeHolder holder,
			int position
									)
		{
			Crime
				crime
				= listCrimes.get( position );
			holder.bindCrime( crime );
		}

		@Override
		public
		int getItemCount()
		{
			return listCrimes.size();
		}
	}
}
