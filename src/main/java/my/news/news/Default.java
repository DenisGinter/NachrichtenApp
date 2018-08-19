package my.news.news;

import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;

public class Default extends Composite implements View {
	
	public Default() {
		setCompositionRoot(new Label("Default"));
	}

}
