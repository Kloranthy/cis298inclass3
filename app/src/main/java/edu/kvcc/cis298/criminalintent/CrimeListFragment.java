package edu.kvcc.cis298.criminalintent;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CrimeListFragment
	extends Fragment
{
	// private variables
	private RecyclerView recyclerView;
	private CrimeAdapter adapter;

	// public methods
	@Nullable
	@Override
	public View onCreateView(
		LayoutInflater inflater,
		@Nullable ViewGroup container,
		@Nullable Bundle savedInstanceState
									)
	{
		View view = inflater.inflate(
			R.layout.fragment_crime_list,
			container,
			false
											 );
		recyclerView = (RecyclerView) view.findViewById(
			R.id.fragment_crime_list_recycler_view
																	  );
		recyclerView.setLayoutManager(
			new LinearLayoutManager(getActivity())
											  );
		updateUI();

		return view;
	}

	// private methods
	private void updateUI()
	{
		CrimeLab    crimeLab   = CrimeLab.get(getActivity());
		List<Crime> listCrimes = crimeLab.getCrimes();

		adapter = new CrimeAdapter(listCrimes);
		recyclerView.setAdapter(adapter);
	}

	// private classes
	private class CrimeHolder
		extends RecyclerView.ViewHolder
		implements View.OnClickListener
	{
		// private variables
		// model variables
		private Crime crime;
		// view variables
		private TextView tvTitle, tvDate;
		private CheckBox cbSolved;

		// public constructors
		public CrimeHolder(View viewItem)
		{
			super(viewItem);

			viewItem.setOnClickListener(this);

			tvTitle = (TextView) viewItem.findViewById(
				R.id.list_item_crime_tv_title
																	);
			tvDate = (TextView) viewItem.findViewById(
				R.id.list_item_crime_tv_date
																	);
			cbSolved = (CheckBox) viewItem.findViewById(
				R.id.list_item_crime_cb_solved
																				  );
		}
		@Override
		public void onClick(View view)
		{
			Intent intent = CrimeActivity.newIntent(
				getActivity(),
				crime.getId()
			);
			startActivity(intent);
		}
		// public methods
		public void bindCrime(Crime crime)
		{
			this.crime = crime;
			tvTitle.setText(crime.getTitle());
			tvDate.setText(
				crime
					.getDate()
					.toString()
							  );
			cbSolved.setChecked(crime.isSolved());
		}
	}

	private class CrimeAdapter
		extends RecyclerView.Adapter<CrimeHolder>
	{
		// private variables
		private List<Crime> listCrimes;

		// public constructors
		public CrimeAdapter(List<Crime> listCrimes)
		{
			this.listCrimes = listCrimes;
		}

		// public methods
		@Override
		public CrimeHolder onCreateViewHolder(
			ViewGroup parent,
			int viewType
														 )
		{
			LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
			View view = layoutInflater.inflate(
				R.layout.list_item_crime,
				parent,
				false
														 );

			return new CrimeHolder(view);
		}

		@Override
		public void onBindViewHolder(
			CrimeHolder holder,
			int position
											 )
		{
			Crime crime = listCrimes.get(position);
			holder.bindCrime(crime);
		}

		@Override
		public int getItemCount()
		{
			return listCrimes.size();
		}
	}
}
