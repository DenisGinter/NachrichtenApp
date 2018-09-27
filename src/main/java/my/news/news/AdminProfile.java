package my.news.news;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button;

public class AdminProfile extends VerticalLayout implements View {
	
	public AdminProfile() {
		ArrayList<Profile> p1;
		p1 =  (ArrayList<Profile>) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("Profile");
				
		if(p1 != null) {
			System.out.println(p1.size());
			List<String> items = new ArrayList<String>();
			
			for (int i = 0; i < p1.size(); i++) {
				items.add(p1.get(i).getName());
			}
			
			ComboBox<Profile> profilList = new ComboBox<Profile>();
			profilList.setItems(p1);
			profilList.setItemCaptionGenerator(Profile::getName);
			profilList.setEmptySelectionAllowed(false);
			profilList.setSelectedItem(p1.get(0));
			addComponent(profilList);
			
			AddProfile addProfileView = new AddProfile();
			Optional<Profile> selectedProfile = profilList.getSelectedItem();
			
			selection(addProfileView,selectedProfile);
			
			Button deleteBtn = new Button("Löschen");
			deleteBtn.addClickListener(e->delete());
		
			addProfileView.addComponent(deleteBtn);
			addComponent(addProfileView);
			
			profilList.addSelectionListener(e -> selection(addProfileView,profilList.getSelectedItem()));
			
		}else {
			Label lbl = new Label("Kein Interessenprofile vorhanden");
			addComponent(lbl);
		}
		
		
		
		
	}
	
	private void selection(AddProfile currentProfile, Optional<Profile> selectedProfile) {
		
		currentProfile.topic.setValue(selectedProfile.get().getTopic());
		currentProfile.name.setValue(selectedProfile.get().getName());
		
		int count = 0;
		for (String component : selectedProfile.get().getWords()) {
			if(count < 3) {
				System.out.println("counasdt: "+ count);
				TextField textFieldKey = (TextField) currentProfile.keywords.getComponent(0, count);
				textFieldKey.setValue(component);
			}else {
				TextField textFieldKeyNew = new TextField();
				textFieldKeyNew.setValue(component);
				currentProfile.keywords.addComponent(textFieldKeyNew);
			}
			
			
			count++;
			System.out.println(count);
		}
		
		
		currentProfile.sources.setItems(selectedProfile.get().getSourcesUserList());
		for (Sources src : selectedProfile.get().getSources()) {
			currentProfile.sources.select(src);
		}
		
		
	}

	private void delete() {
		VaadinService.getCurrentRequest().getWrappedSession().removeAttribute("Profile");		
		Page.getCurrent().reload();
	}
}
