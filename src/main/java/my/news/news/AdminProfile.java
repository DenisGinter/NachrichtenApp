package my.news.news;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;

public class AdminProfile extends VerticalLayout implements View {
	
	ComboBox<Profile> profilList = new ComboBox<Profile>();
	
	public AdminProfile() {
		ArrayList<Profile> profileList;
		profileList =  (ArrayList<Profile>) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("Profile");
				
		if(profileList != null) {
			List<String> items = new ArrayList<String>();
			
			for (int i = 0; i < profileList.size(); i++) {
				items.add(profileList.get(i).getName());
			}
			
			
			profilList.setItems(profileList);
			profilList.setItemCaptionGenerator(Profile::getName);
			profilList.setEmptySelectionAllowed(false);
			profilList.setSelectedItem(profileList.get(0));
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
	
		currentProfile.setSourceListUser(selectedProfile.get().getSourcesUserList());
		currentProfile.name.setValue(selectedProfile.get().getName());
		
		for (Sources src : currentProfile.getSourceTopicList()) {
			if (selectedProfile.get().getTopic().equals(src.getCategory())) {
				currentProfile.topic.setValue(src);
				break;
			}
		}	
		
		for (Sources src : currentProfile.getSourceLanguageList()) {
			if (selectedProfile.get().getLanguage().equals(src.getLanguage())) {
				currentProfile.language.setValue(src);
				break;
			}
		}	
		
		for (Sources src : selectedProfile.get().getSourcesUserList()) {
			currentProfile.sources.select(src);	
		}
		
				
		int count = 0;
		for (String component : selectedProfile.get().getWords()) {
			if(count < 3) {
				TextField textFieldKey = (TextField) currentProfile.keywords.getComponent(0, count);
				textFieldKey.setValue(component);
			}else {
				TextField textFieldKeyNew = new TextField();
				textFieldKeyNew.setValue(component);
				currentProfile.keywords.addComponent(textFieldKeyNew);
			}
			count++;
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
