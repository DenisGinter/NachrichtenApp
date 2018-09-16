package my.news.news.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import my.news.news.client.MyUI;



public class MyEntryPoint implements EntryPoint {
    @Override
    public void onModuleLoad() {
    	final MyUI myui = new MyUI();
        RootPanel.get().add(myui);
    }
}