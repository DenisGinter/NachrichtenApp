package my.news.news.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HorizontalPanel;
import my.news.news.client.MyUI;



public class MyEntryPoint implements EntryPoint {
	@Override
	public void onModuleLoad() {
	    MyUI myui = new MyUI();

	   /* HorizontalPanel hp = new HorizontalPanel();
	    hp.add(b);
	    hp.add(textBox);
	    hp.setCellHorizontalAlignment(b, HorizontalPanel.ALIGN_LEFT);
	    hp.setCellHorizontalAlignment(textBox, HorizontalPanel.ALIGN_RIGHT);

	    hp.setWidth("100%");*/

	    RootPanel.get().add(myui);
	  }
	}