package my.news.news;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TooManyListenersException;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.vaadin.teemusa.sidemenu.SideMenu;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractMultiSelect;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import android.R.id;
import elemental.json.Json;



public class AddProfile extends AddProfileDesign  implements View{

	private Profile profil = new Profile();
	private List<Sources> sourceList = new ArrayList<>();
	private List<Sources> sourceTopicList = new ArrayList<>();
	private List<Sources> sourceLanguageList = new ArrayList<>();
	private List<String> sourceDisabled = new ArrayList<>();
	public List<Sources> getSourceTopicList() {
		return sourceTopicList;
	}


	public void setSourceTopicList(List<Sources> sourceTopicList) {
		this.sourceTopicList = sourceTopicList;
	}


	public List<Sources> getSourceLanguageList() {
		return sourceLanguageList;
	}


	public void setSourceLanguageList(List<Sources> sourceLanguageList) {
		this.sourceLanguageList = sourceLanguageList;
	}

	private List<Sources>sourceListUser = new ArrayList<>();
	private List<Sources>sourceListUserAll = new ArrayList<>();
	private boolean allSelected = false;
	public List<Sources> getSourceListUser() {
		return sourceListUser;
	}


	public void setSourceListUser(List<Sources> sourceListUser) {
		this.sourceListUser = sourceListUser;
	}

	protected TwinColSelect<Sources> addSourceTwinSelect = new TwinColSelect<Sources>();
	private Profile profile;
	private ArrayList<Profile> profiles;
	private Sources allSource = new Sources("all", "ALL", "All", "all");
	
	public Sources getAllSource() {
		return allSource;
	}


	public void setAllSource(Sources allSource) {
		this.allSource = allSource;
	}


	public AddProfile() {
		
		/*if(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("sourceList")!=null) {
			Object sessionSourceList = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("sourceList");
		}*/
		
		
		sourceList = getSources("https://newsapi.org/v2/sources?apiKey=4346700d77a84e42891f9c2dfef158bc");
		sourceListUser.add(allSource);
		if(sourceList != null) {
			if(sourceList.size() >= 4) {
				int i = 0;
				while (i < 3) {
					sourceListUser.add(sourceList.get(i));
					i++;
				}
			}else {	
					sourceListUser.addAll(sourceList);
			}
		
		sourceTopicList.add(allSource);
		topic.setItemCaptionGenerator(Sources::getCategory);
		topic.setValue(allSource);
		topic.setEmptySelectionAllowed(false);
		setTopic(sourceList);
		
		sourceLanguageList.add(allSource);
		language.setItemCaptionGenerator(Sources::getLanguage);
		language.setValue(allSource);
		language.setEmptySelectionAllowed(false);
		setLanguage(sourceList);
		
		
		sources.setItems(sourceListUser);
		sources.setItemCaptionGenerator(Sources::getName);
		
	
		}else {
			sources.setCaption("Keine Quellen verfügbar");
		}
		
		
		addSourceTwinSelect.setItems(sourceList);
		addSourceTwinSelect.setItemCaptionGenerator(Sources::getName);
		
		language.addValueChangeListener(e -> changeBasisLanguage(language.getSelectedItem()));
		topic.addValueChangeListener(e -> changeBasisTopic(topic.getSelectedItem()));
		save.setClickShortcut(KeyCode.ENTER);
		save.addClickListener(e -> save());
		stop.addClickListener(e -> stop());
		add.addClickListener(e -> add());
		btnAddSrc.addClickListener(e -> addSource());
		sources.addSelectionListener(e -> sourcesSelectionChange());

	}
	
	private void sourcesSelectionChange() {
		List<String> selection = sources.getSelectedItems().stream().map(o -> o.getId()).collect(Collectors.toList());
			if (selection.contains(allSource.getId()) && !allSelected) {
				if (sourceDisabled.isEmpty()) {
					for (Sources src : sourceListUser) {
						sources.select(src);
					}
			}else {
				for (Sources src : sourceListUser) {
					if (sourceDisabled.contains(src.getId())) {
						sources.select(src);
					}else {
						sources.deselect(src);
					}
					
				}
			}
			allSelected = true;
		}else if (!selection.contains(allSource.getId()) && allSelected == true) {
			sources.deselectAll();
			allSelected = false;
		}
	}


	private void changeBasisTopic(Optional<Sources> optional) {
		int i = 0;
		List<Sources> basisList = new ArrayList<Sources>();
		if(optional.get() == allSource) {
			if (language.getSelectedItem().get() == allSource) {
				basisList.addAll(sourceList);	
			}else {
				while( i < sourceList.size()) {
				if (sourceList.get(i).getLanguage().equals(language.getSelectedItem().get().getLanguage())) {
					basisList.add(sourceList.get(i));
				}
				i++;
			}
			}
			setLanguage(sourceList);
		}else {
				while( i < sourceList.size()) {
					if (sourceList.get(i).getCategory().equals(optional.get().getCategory())) {

						basisList.add(sourceList.get(i));
					}
					i++;
				}
			setLanguage(basisList);
			int k = 0;
			while( k < basisList.size()) {
			if (!basisList.get(k).getLanguage().equals(optional.get().getLanguage())) {
				basisList.remove(basisList.get(k));
			}else {
				k++;
			}
			
		}
			
		}
		setSourceItemDiasable(basisList);
		
		//topic.setItems(sourceTopicList);
	}
	
	private void changeBasisLanguage(Optional<Sources> optional) {
		int i = 0;
		List<Sources> basisList = new ArrayList<Sources>();
		if(language.getSelectedItem().get() == allSource) {
			if(topic.getSelectedItem().get() == allSource) {
					basisList.addAll(sourceList);	
			}else {
					while( i < sourceList.size()) {
					if (sourceList.get(i).getCategory().equals(topic.getSelectedItem().get().getCategory())) {
						basisList.add(sourceList.get(i));
					}
					i++;
				}
			}
			setTopic(sourceList);
		}else {
				while( i < sourceList.size()) {
				if (sourceList.get(i).getLanguage().equals(optional.get().getLanguage())) {
					basisList.add(sourceList.get(i));
				}
				i++;
			}
		setTopic(basisList);
		int k = 0;
			while( k < basisList.size()) {
				if (!basisList.get(k).getCategory().equals(optional.get().getCategory())) {
					basisList.remove(basisList.get(k));
				}else {
					k++;	
				}
				
			}
		}
		setSourceItemDiasable(basisList);
		
	}
	
	private void setSourceItemDiasable (List<Sources> basisList) {
		sources.clear();
		
		List<Sources> helpList = new ArrayList<>();
		if (sourceListUser.get(0).getId() != allSource.getId()) {
			helpList.add(allSource);
		}
		helpList.addAll(sourceListUser);
		sources.setItems(helpList);
		
		List<String> idSourceList =  sourceList.stream().map(o -> o.getId()).collect(Collectors.toList());
		List<String> idBasisList =  basisList.stream().map(o -> o.getId()).collect(Collectors.toList());
		
		
		sources.setItemEnabledProvider(item -> idSourceList.contains(item.getId()) || item.getId() == allSource.getId());
		sources.setItemEnabledProvider(item -> idBasisList.contains(item.getId()) || item.getId() == allSource.getId());
		sourceDisabled.clear();
		sourceDisabled.add(allSource.getId());
		sourceDisabled.addAll(idBasisList);
		//sources.setItemEnabledProvider(item -> sourceList.contains(item) || item == allSource);
		//sources.setItemEnabledProvider(item -> basisList.contains(item.getId()) || item == allSource );
	}
	
	private void setTopic (List<Sources> basisList) {
		sourceTopicList.clear();
		sourceTopicList.add(allSource);
		
			sourceTopicList.addAll(basisList);
				
		for (int i = 0; i < sourceTopicList.size(); i++) {
			int k = i+1;
			while(k < sourceTopicList.size()) {
				if (sourceTopicList.get(i).getCategory().equals(sourceTopicList.get(k).getCategory())) {
					sourceTopicList.remove(sourceTopicList.get(k));
				}else{
				k++;	
				}
				
			}
		}
		
		topic.setItems(sourceTopicList);
		
	}
	
	private void setLanguage (List<Sources> basisList) {
		sourceLanguageList.clear();
		sourceLanguageList.add(allSource);
		sourceLanguageList.addAll(basisList);
		
		for (int i = 0; i < sourceLanguageList.size(); i++) {
			int k = i+1;
			while(k < sourceLanguageList.size()) {
				if (sourceLanguageList.get(i).getLanguage().equals(sourceLanguageList.get(k).getLanguage())) {
					sourceLanguageList.remove(sourceLanguageList.get(k));
				}else{
				k++;	
				}
				
			}
		}
		
		
		language.setItems(sourceLanguageList);
		
	}


	private void addSource() {
		
		Window addSourceWindow = new Window("Quellen");
		addSourceWindow.center();
		addSourceWindow.setModal(true);
		addSourceWindow.setResizable(true);
		addSourceWindow.setClosable(true);
		
		
		VerticalLayout vL = new VerticalLayout();
		vL.setMargin(true);

		for (Sources src : sourceListUser) {
			for (Sources component : sourceList) {
				if(component.getId().equals(src.getId())) {
					addSourceTwinSelect.select(component);
				}
			}
			
		}
		
		Button sourceSave = new Button("Speichern");
		sourceSave.addClickListener(e -> addSourceSelection(addSourceTwinSelect.getSelectedItems()));
		
		vL.addComponents(addSourceTwinSelect,sourceSave);
		addSourceTwinSelect.setWidth("500px");
	
		addSourceWindow.setContent(vL);
		
		
		MyUI.getCurrent().addWindow(addSourceWindow);
		
	}
	
	private void addSourceSelection(Set<Sources> sourceListUserNew) {
				Set<Sources> selectedItems = sources.getSelectedItems();
				List<Sources> basisList = new ArrayList<Sources>();
				sourceListUser.clear();
				sourceListUser.addAll(sourceListUserNew);

				for (Window sourceWindow : MyUI.getCurrent().getWindows()) {
					sourceWindow.close();
				}
				basisList.add(allSource);
				basisList.addAll(sourceListUser);
				sources.setItems(basisList);
				for (Sources src : selectedItems) {
					for (Sources source : sourceListUser) {
						if(source.getId().equals(src.getId())) {
							sources.select(source);
						}
					}
					
					
				}				
	}


	public static List<Sources> getSources(String targetURL){
		 
		 
		 try {
			 URL obj = new URL(targetURL);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();

				// optional default is GET
				con.setRequestMethod("GET");
				con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");

				int responseCode = con.getResponseCode();				
				System.out.println("\nSending 'GET' request to URL : " + targetURL);
				System.out.println("Response Code : " + responseCode);

				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream(),"UTF-8"));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				
				List<Sources> sourceList = new ArrayList<Sources>();

			    int i = 0;
			    while(i < Json.parse(response.toString()).getArray("sources").length()){
			    	String id = Json.parse(response.toString()).getArray("sources").getObject(i).getString("id");
			    	String name = Json.parse(response.toString()).getArray("sources").getObject(i).getString("name");
			    	String category = Json.parse(response.toString()).getArray("sources").getObject(i).getString("category");
			    	String language = Json.parse(response.toString()).getArray("sources").getObject(i).getString("language");
			    	Sources newSource = new Sources(id,name,category,language);
			    	sourceList.add(newSource);
				    i++;
			    }
				//print result
			    return sourceList;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Request News failed");
		}
		return null;
		 
			
	}
	
	private void save() {
	
		if(topic.getValue() == null || name.getValue().isEmpty() ) {
			Notification message = new Notification("Interessensprofile muss Name und Themenbereich besitzen",Notification.TYPE_WARNING_MESSAGE);
			message.show(Page.getCurrent());
			
		}else {
			Profile profil = new Profile();			
			profil.setName(name.getValue());
			profil.setTopic(topic.getValue().getCategory());
			profil.setLanguage(language.getValue().getLanguage());
			
			if(sources.getSelectedItems()!= null) {
				ArrayList<Sources> src = new ArrayList<>();
				src.addAll(sources.getSelectedItems());
				
				profil.setSources(src);
				profil.setSourcesUserList(sourceListUser);
			}
			
			ArrayList<String> arraywords = new ArrayList<String>();
			Iterator<Component> iterate = keywords.iterator();
			while(iterate.hasNext()) {
				String nextword = ((AbstractTextField) iterate.next()).getValue();
				if(!nextword.isEmpty()) {
				arraywords.add(nextword);
				}
			}
			
			profil.setWords(arraywords);
			
			profiles =  (ArrayList<Profile>) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("Profile");	
			Notification message = new Notification("Interessensprofile gespeichert",Notification.TYPE_HUMANIZED_MESSAGE);
			
			if(profiles == null) {
				
				profiles = new ArrayList<Profile>();
				profiles.add(profil);
				
			}else if(getParent().getClass() != VerticalLayout.class) {
				
				AdminProfile adminProfile = (AdminProfile) getParent();
				Profile updateProfile = adminProfile.profilList.getValue();
				profiles.remove(updateProfile);
				profiles.add(profil);
				
				message.setCaption("Interessenpofile geändert");
			}
			else {			
				profiles.add(profil);	
			}
			
			VaadinService.getCurrentRequest().getWrappedSession().setAttribute("Profile", profiles);	
			
			message.setDelayMsec(1500);
			message.show(Page.getCurrent());	
			
		}

	}
	
	
	private void add() {
		/*Profile p1 = (Profile) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("Profile");
		Notification message = new Notification("Profile "+p1.getName(),Notification.TYPE_WARNING_MESSAGE);
		message.show(Page.getCurrent());*/
		
		TextField textField = new TextField();
		
		keywords.addComponent(textField);
	}
	
	//reloaded
	private void stop() {
		Page.getCurrent().reload();
	}
}
