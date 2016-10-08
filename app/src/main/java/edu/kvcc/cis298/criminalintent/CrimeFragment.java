package edu.kvcc.cis298.criminalintent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;


public class CrimeFragment
	extends Fragment
{
	// model variables
	private Crime    crime;
	// view variables
	private EditText etTitle;
	private Button   btnDate;
	private CheckBox cbSolved;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		crime = new Crime();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
									 @Nullable Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.fragment_crime, container, false);

		etTitle = (EditText) v.findViewById(R.id.fragment_crime_et_title);
		etTitle.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
				// do nothing
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				crime.setTitle(s.toString());
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				// do nothing
			}
		});

		btnDate = (Button) v.findViewById(R.id.fragment_crime_btn_crime_date);
		btnDate.setText(
			crime.getDate()
				  .toString()
							);
		btnDate.setEnabled(false);

		cbSolved = (CheckBox) v.findViewById(R.id.fragment_crime_cb_crime_solved);
		cbSolved.setOnCheckedChangeListener(
			new CompoundButton.OnCheckedChangeListener()
			{
				@Override
				public void onCheckedChanged(CompoundButton btnView, boolean isChecked)
				{
					crime.setSolved(isChecked);
				}
			}
													  );

		return v;
	}
}
