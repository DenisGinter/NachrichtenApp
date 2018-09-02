package my.news.news;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

public class NewsView extends NewsViewDesign implements View{

	public NewsView() {
		
		
		nachrichten1.setValue("Nachrichten text Test 1");
		nachrichten2.setValue("Nachrichten text Test 2");
	
		//newsContainer.addTab(texts, "Nachrichtenprofile 1");
		//newsContainer.addTab(textss ,"Nachrichtenprofile 2");
		
		
       
		
	}
}
