package my.news.news;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class AdminProfile extends VerticalLayout implements View {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ComboBox<Profile> profilList = new ComboBox<Profile>();
	
	@SuppressWarnings("unchecked")
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
			deleteBtn.addClickListener(e->delete(profilList.getSelectedItem()));
		
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
			if(count < 5) {
				if(count%2 == 0) {
				TextField textFieldKey = (TextField) currentProfile.keywords.getComponent(count);
				textFieldKey.setValue(component);	
				}else {
					@SuppressWarnings("unchecked")
					NativeSelect<String> nativeSelect = (NativeSelect<String>) currentProfile.keywords.getComponent(count);
					nativeSelect.setValue(component);
				}
				
			}else {
				if(count%2 == 0) {
					TextField textFieldKeyNew = new TextField();
					textFieldKeyNew.setValue(component);
					currentProfile.keywords.addComponent(textFieldKeyNew);
					}else {
						
						NativeSelect<String> nativeSelectNew = new NativeSelect<String>();
						nativeSelectNew.setItems("OR","AND","NOT");
						nativeSelectNew.setValue(component);
						currentProfile.keywords.addComponent(nativeSelectNew);
					}
				
				
				
			}
			count++;
		}
		
		
		currentProfile.sources.setItems(selectedProfile.get().getSourcesUserList());
		for (Sources src : selectedProfile.get().getSources()) {
			currentProfile.sources.select(src);
		}
		
		
	}

	/**
	 * Delete.
	 *
	 * @param selectedProfile the selected profile
	 */
	private void delete(Optional<Profile> selectedProfile) {
		//VaadinService.getCurrentRequest().getWrappedSession().removeAttribute("Profile");		
		@SuppressWarnings("unchecked")
		ArrayList<Profile> profileList = (ArrayList<Profile>) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("Profile");
		for (Profile profile : profileList) {
			if (profile.getName().equals(selectedProfile.get().getName())) {
				profileList.remove(profile);
			}
		}
		
		VaadinService.getCurrentRequest().getWrappedSession().setAttribute("Profile", profileList);
		Page.getCurrent().reload();
	}
	
	
	
}
