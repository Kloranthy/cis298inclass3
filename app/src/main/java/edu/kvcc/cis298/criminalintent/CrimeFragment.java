package edu.kvcc.cis298.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;


public
class CrimeFragment
	extends Fragment
{
	// private variables
	// model variables
	private static final String
		ARG_CRIME_ID
		= "crime_id";
	private static final String
		DIALOG_DATE
		= "DialogDate";
	private static final int
		REQUEST_DATE
		= 0;
	private Crime
		crime;
	// view variables
	private EditText
		etTitle;
	private Button
		btnDate;
	private CheckBox
		cbSolved;

	// public methods
	public static
	CrimeFragment newInstance(UUID crimeId)
	{
		Bundle
			args
			= new Bundle();
		args.putSerializable(
			ARG_CRIME_ID,
			crimeId
								  );

		CrimeFragment
			fragment
			= new CrimeFragment();
		fragment.setArguments( args );
		return fragment;
	}

	@Override
	public
	void onActivityResult(
		int requestCode,
		int resultCode,
		Intent data
								)
	{
		if ( resultCode
			  != Activity.RESULT_OK )
		{
			return;
		}
		if ( requestCode
			  == REQUEST_DATE )
		{
			Date
				date
				= (Date) data
				.getSerializableExtra(
					DatePickerFragment.EXTRA_DATE
											);
			crime.setDate( date );
			updateDate();
		}
	}

	@Override
	public
	void onCreate(
		@Nullable
			Bundle savedInstanceState
					 )
	{
		super.onCreate( savedInstanceState );

		UUID
			crimeId
			= (UUID) getArguments()
			.getSerializable( ARG_CRIME_ID );
		crime
			= CrimeLab
			.get( getActivity() )
			.getCrime( crimeId );
	}

	@Override
	public
	void onPause()
	{
		super.onPause();
		CrimeLab.get( getActivity() )
				  .updateCrime( crime );
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
			R.layout.fragment_crime,
			container,
			false
									);

		etTitle
			= (EditText) view.findViewById(
			R.id.fragment_crime_et_title
													);
		etTitle.setText( crime.getTitle() );
		etTitle.addTextChangedListener(
			new TextWatcher()
			{
				@Override
				public
				void beforeTextChanged(
					CharSequence s,
					int start,
					int count,
					int after
											 )
				{
					// do nothing
				}

				@Override
				public
				void onTextChanged(
					CharSequence s,
					int start,
					int before,
					int count
										)
				{
					crime.setTitle( s.toString() );
				}

				@Override
				public
				void afterTextChanged(Editable s)
				{
					// do nothing
				}
			}
												);

		btnDate
			= (Button) view.findViewById(
			R.id.fragment_crime_btn_crime_date
												 );
		updateDate();
		btnDate.setOnClickListener(
			new View.OnClickListener()
			{
				@Override
				public
				void onClick(View view)
				{
					FragmentManager
						fragmentManager
						= getFragmentManager();
					DatePickerFragment
						dialog
						=
						DatePickerFragment
							.newInstance( crime.getDate() );
					dialog.setTargetFragment(
						CrimeFragment.this,
						REQUEST_DATE
													);
					dialog.show(
						fragmentManager,
						DIALOG_DATE
								  );
				}
			}
										  );

		cbSolved
			= (CheckBox) view.findViewById(
			R.id.fragment_crime_cb_crime_solved
													);
		cbSolved.setChecked( crime.isSolved() );
		cbSolved.setOnCheckedChangeListener(
			new CompoundButton.OnCheckedChangeListener()
			{
				@Override
				public
				void onCheckedChanged(
					CompoundButton btnView,
					boolean isChecked
											)
				{
					crime.setSolved( isChecked );
				}
			}
													  );

		return view;
	}

	// private methods
	private
	void updateDate()
	{
		btnDate.setText(
			crime
				.getDate()
				.toString()
							);
	}

	public
	void returnResult()
	{
		getActivity()
			.setResult(
				Activity.RESULT_OK,
				null
						 );
	}
}
