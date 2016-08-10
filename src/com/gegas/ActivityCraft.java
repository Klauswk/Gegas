package com.gegas;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityCraft
  extends Activity
{
  private ArrayAdapter<String> adapter;
  private ListView lvCraftar;
  
  private void atribui()
  {
    this.lvCraftar = ((ListView)findViewById(2131230740));
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903042);
    atribui();
    this.adapter = new ArrayAdapter(this, 17367043, new String[] { "Espada de Ferro" });
    this.lvCraftar.setAdapter(this.adapter);
  }
  
  @Override
  protected void onPause()
  {
	  super.onPause();
	  finish();
  }
}

