package com.gegas;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ActivityIniciacao extends Activity{
	Button btComecar;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comecar);
		btComecar = (Button) findViewById(R.id.btComecar);
		
		
		try {
			CopiarDatabase();
			btComecar.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getBaseContext(),ActivityGame.class));
					finish();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.i("Erro", e.toString());
		}
	}
	
	private void CopiarDatabase()
			throws IOException
			{
		try
		{
			InputStream localInputStream = getAssets().open("db/BancoDados.db");
			File file = new File(android.os.Environment.getExternalStorageDirectory()+"/Gegas");
			file.mkdir();
			
			FileOutputStream localFileOutputStream = new FileOutputStream(android.os.Environment.getExternalStorageDirectory()+"/Gegas/BancoDados.db");
			byte[] buffer = new byte[1024];
			int length;
			while ((length = localInputStream.read(buffer)) > 0) {
				localFileOutputStream.write(buffer, 0, length);
			}

			// Close the streams
			localFileOutputStream.flush();
			localFileOutputStream.close();
			localInputStream.close();
		}
		catch (Exception localException)
		{
			Log.e("error", localException.toString());
		}
			}
}
