package my.news.news;


import java.util.ArrayList;
import java.util.List;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button;

public class AdminProfile extends VerticalLayout implements View {
	
	public AdminProfile() {
		
		Profile p1 = (Profile) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("Profile");
		
		
		if(p1 != null) {
			List<String> items = new ArrayList<String>();
			items.add(p1.getName());
			
			ComboBox<String> profilList = new ComboBox<String>();
			profilList.setItems(items);
			profilList.setEmptySelectionAllowed(false);
			profilList.setSelectedItem(items.get(0));
			addComponent(profilList);
			
			AddProfile addProfileView = new AddProfile();
			
			Button deleteBtn = new Button("Löschen");
			deleteBtn.addClickListener(e->delete());
		
			addProfileView.buttons.addComponent(deleteBtn);
			addComponent(addProfileView);
			
		}else {
			Label lbl = new Label("Kein Interessenprofile vorhanden");
			addComponent(lbl);
		}
		
		
	}
	
	private void delete() {
		VaadinService.getCurrentRequest().getWrappedSession().removeAttribute("Profile");		
		Page.getCurrent().reload();
	}
}
