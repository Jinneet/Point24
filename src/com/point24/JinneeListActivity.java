package com.point24;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * An activity representing a list of Jinnees. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a
 * {@link JinneeDetailActivity} representing item details. On tablets, the
 * activity presents the list of items and item details side-by-side using two
 * vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link JinneeListFragment} and the item details (if present) is a
 * {@link JinneeDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link JinneeListFragment.Callbacks} interface to listen for item selections.
 */
public class JinneeListActivity extends FragmentActivity
		implements
			JinneeListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;
	public TextView text2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);
		// setContentView(R.layout.activity_jinnee_list);
		FrameLayout frameLayout = new FrameLayout(this);
		frameLayout.setBackground(this.getResources()
				.getDrawable(R.drawable.bg));
		setContentView(frameLayout);

		TextView text1 = new TextView(this);
		text1.setText("联系在代码中控制UI");
		text1.setTextColor(Color.rgb(255, 255, 255));
		text1.setTextSize(TypedValue.COMPLEX_UNIT_PX, 24);
		frameLayout.addView(text1);

		text2 = new TextView(this);
		text2.setText("单击进入游戏。。。");
		text2.setTextSize(this.getResources().getDimension(R.dimen.text));
		text2.setTextColor(this.getResources().getColor(R.color.textWhite));
		LayoutParams params = new LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);

		params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
		text2.setLayoutParams(params);

		text2.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				new AlertDialog.Builder(JinneeListActivity.class)
						.setTitle("系统提示")
						.setMessage("游戏有风险，真要进入嘛？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO Auto-generated method stub
										Log.i("Point24", "进入游戏"); // 输出日志
									}
								})
						.setNegativeButton("退出",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// TODO
										// Auto-generated
										// method
										// stub
										Log.i("Point24", "退出游戏");
										finish();
									}
								}).show();
			}
		});

		frameLayout.addView(text2);

		if (findViewById(R.id.jinnee_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((JinneeListFragment) getSupportFragmentManager().findFragmentById(
					R.id.jinnee_list)).setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}
	/**
	 * Callback method from {@link JinneeListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(JinneeDetailFragment.ARG_ITEM_ID, id);
			JinneeDetailFragment fragment = new JinneeDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.jinnee_detail_container, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, JinneeDetailActivity.class);
			detailIntent.putExtra(JinneeDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
}
