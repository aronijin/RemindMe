package com.remindme;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewFood extends Activity {
	private dbhandler datasource;
	private FoodStuff fs;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addfoodstuff);
		datasource = new dbhandler(this);
		setContentView(R.layout.viewfood);

		fs = new FoodStuff();

		datasource.open();
		List<FoodStuff> dblist = datasource.getAllFoodStuff();
		long id = -1;

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			id = extras.getLong("food");
			System.out.println(id);
		} else {
			System.out.println("no extras in intent");
			// this.finish();
		}

		for (int i = 0; i < dblist.size(); i++) {
			if (dblist.get(i).getID() == id) {
				fs = dblist.get(i);
			}
		}

		TextView name = (TextView) findViewById(R.id.nameText);
		TextView descr = (TextView) findViewById(R.id.descrText);
		TextView added = (TextView) findViewById(R.id.addedText);
		TextView expire = (TextView) findViewById(R.id.expireText);

		name.setText(fs.getName());
		descr.setText(fs.getDescr());

		Calendar add = Calendar.getInstance(), expired = Calendar.getInstance();
		add.setTimeInMillis(fs.getAdded());
		expired.setTimeInMillis(fs.getExpire());
		added.setText(add.getDisplayName(Calendar.MONTH, Calendar.SHORT,
				Locale.US)
				+ " "
				+ add.get(Calendar.DAY_OF_MONTH)
				+ " "
				+ ", "
				+ add.get(Calendar.YEAR));
		expire.setText(expired.getDisplayName(Calendar.MONTH, Calendar.SHORT,
				Locale.US)
				+ " "
				+ expired.get(Calendar.DAY_OF_MONTH)
				+ " "
				+ ", " + expired.get(Calendar.YEAR));

		// File imgFile = new File(fs.getPath());
		// if(imgFile.exists()){
		//
		// Bitmap myBitmap =
		// BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		// myBitmap.
		// ImageView imgv = (ImageView) findViewById(R.id.imageView1);
		// imgv.setImageBitmap(myBitmap);
		// imgv.bringToFront();
		// }

		datasource.close();

	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				datasource.open();
				datasource.deleteFoodStuff(fs);
				datasource.close();
				if (fs.getPath() != null) {
					File file = new File(fs.getPath());
					boolean isDeleted = file.delete();
					if (isDeleted) {
						Toast.makeText(getApplicationContext(),
								"Item and picture deleted", Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(getApplicationContext(),
								"Picture not deleted!", Toast.LENGTH_SHORT)
								.show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "Item deleted",
							Toast.LENGTH_SHORT).show();
				}
				finish();
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				// Do your No progress
				break;
			}
		}
	};

	public void viewImage(View view) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + fs.getPath()), "image/*");
		startActivity(intent);
	}

	public void deleteItem(View view) {

		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setMessage("Are you sure to delete?")
				.setPositiveButton("Yes", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show();

	}

	public void goBack(View view) {
		this.finish();
	}

}
