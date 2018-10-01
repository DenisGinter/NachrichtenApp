package my.news.news;


import java.awt.Component.BaselineResizeBehavior;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import elemental.json.Json;



// TODO: Auto-generated Javadoc
/**
 * The Class AddProfile.
 */
public class AddProfile extends AddProfileDesign  implements View{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The source list. */
	private List<Sources> sourceList = new ArrayList<>();
	
	/** The source topic list. */
	private List<Sources> sourceTopicList = new ArrayList<>();
	
	/** The source language list. */
	private List<Sources> sourceLanguageList = new ArrayList<>();
	
	/** The source disabled. */
	private List<String> sourceDisabled = new ArrayList<>();
	
	/**
	 * Gets the source topic list.
	 *
	 * @return the source topic list
	 */
	public List<Sources> getSourceTopicList() {
		return sourceTopicList;
	}


	/**
	 * Sets the source topic list.
	 *
	 * @param sourceTopicList the new source topic list
	 */
	public void setSourceTopicList(List<Sources> sourceTopicList) {
		this.sourceTopicList = sourceTopicList;
	}


	/**
	 * Gets the source language list.
	 *
	 * @return the source language list
	 */
	public List<Sources> getSourceLanguageList() {
		return sourceLanguageList;
	}


	/**
	 * Sets the source language list.
	 *
	 * @param sourceLanguageList the new source language list
	 */
	public void setSourceLanguageList(List<Sources> sourceLanguageList) {
		this.sourceLanguageList = sourceLanguageList;
	}

	/** The source list user. */
	private List<Sources>sourceListUser = new ArrayList<>();
	
	/** The all selected. */
	private boolean allSelected = false;
	
	/**
	 * Gets the source list user.
	 *
	 * @return the source list user
	 */
	public List<Sources> getSourceListUser() {
		return sourceListUser;
	}


	/**
	 * Sets the source list user.
	 *
	 * @param sourceListUser the new source list user
	 */
	public void setSourceListUser(List<Sources> sourceListUser) {
		this.sourceListUser = sourceListUser;
	}

	/** The add source twin select. */
	protected TwinColSelect<Sources> addSourceTwinSelect = new TwinColSelect<Sources>();
	
	/** The profiles. */
	private ArrayList<Profile> profiles;
	
	/** The all source. */
	private Sources allSource = new Sources("all", "ALL", "All", "all");
	
	/**
	 * Gets the all source.
	 *
	 * @return the all source
	 */
	public Sources getAllSource() {
		return allSource;
	}


	/**
	 * Sets the all source.
	 *
	 * @param allSource the new all source
	 */
	public void setAllSource(Sources allSource) {
		this.allSource = allSource;
	}


	/**
	 * Instantiates a new adds the profile.
	 */
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
	
	/**
	 * Sources selection change.
	 */
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


	/**
	 * Change basis topic.
	 *
	 * @param optional the optional
	 */
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
	
	/**
	 * Change basis language.
	 *
	 * @param optional the optional
	 */
	private void changeBasisLanguage(Optional<Sources> optional) {
		int i = 0;
		List<Sources> basisList = new ArrayList<Sources>();
		if(language.getSelectedItem().get() == allSource) {
			if(topic.getSelectedItem().get() == allSource) {
					basisList.addAll(sourceList);
					setSourceItemDiasable(basisList);
			}else {
					while( i < sourceList.size()) {
					if (sourceList.get(i).getCategory().equals(topic.getSelectedItem().get().getCategory())) {
						basisList.add(sourceList.get(i));
					}
					i++;
				}
			}
			
			setTopic(sourceList);
			changeBasisLanguageSources(sourceList);
			if (topic.getSelectedItem().get() == allSource) {
				setSourceItemDiasable(basisList);
			}
		}else {System.out.println(sourceList.size());
				while( i < sourceList.size()) {
				if (sourceList.get(i).getLanguage().equals(optional.get().getLanguage())) {
					basisList.add(sourceList.get(i));
				}
				i++;
			}
		changeBasisLanguageSources(basisList);
		setTopic(basisList);
		if (topic.getSelectedItem().get() == allSource) {
			setSourceItemDiasable(basisList);
		}
		for (Sources sources : basisList) {
			System.out.println(sources.getName());
		}
		System.out.println(basisList.size());
		int k = 0;
			while( k < basisList.size()) {
				if (!basisList.get(k).getCategory().equals(optional.get().getCategory())) {
					basisList.remove(basisList.get(k));
				}else {
					k++;	
				}
				
			}
		}
		
		if (topic.getSelectedItem().get() != allSource) {
			setSourceItemDiasable(basisList);
		}
		
	}
	
	private void changeBasisLanguageSources(List<Sources> basisList) {
		System.out.println(basisList.size());
		ArrayList<Sources> arraylist = new ArrayList<>();
		arraylist.addAll(basisList);
		addSourceTwinSelect.setItems(arraylist);
		
	}
	
	/**
	 * Sets the source item diasable.
	 *
	 * @param basisList the new source item diasable
	 */
	private void setSourceItemDiasable (List<Sources> basisList) {
		sources.clear();
		
		List<Sources> helpList = new ArrayList<>();
		if (sourceListUser.get(0).getId() != allSource.getId()) {
			helpList.add(allSource);
		}
		helpList.addAll(sourceListUser);
		sources.setItems(helpList);
		
		List<String> idSourceList =  sourceList.stream().map(o -> o.getId()).collect(Collectors.toList());
		List<String> idBasisList = new ArrayList<>();
		idBasisList.addAll(basisList.stream().map(o -> o.getId()).collect(Collectors.toList()));
		System.out.println(idBasisList.size());
		
		sources.setItemEnabledProvider(item -> idSourceList.contains(item.getId()) || item.getId() == allSource.getId());
		sources.setItemEnabledProvider(item -> idBasisList.contains(item.getId()) || item.getId() == allSource.getId());
		sourceDisabled.clear();
		sourceDisabled.add(allSource.getId());
		sourceDisabled.addAll(idBasisList);
		//sources.setItemEnabledProvider(item -> sourceList.contains(item) || item == allSource);
		//sources.setItemEnabledProvider(item -> basisList.contains(item.getId()) || item == allSource );
	}
	
	/**
	 * Sets the topic.
	 *
	 * @param basisList the new topic
	 */
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
	
	/**
	 * Sets the language.
	 *
	 * @param basisList the new language
	 */
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


	/**
	 * Adds the source.
	 */
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
	
	/**
	 * Adds the source selection.
	 *
	 * @param sourceListUserNew the source list user new
	 */
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


	/**
	 * Gets the sources.
	 *
	 * @param targetURL the target URL
	 * @return the sources
	 */
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
	
	/**
	 * Save.
	 */
	@SuppressWarnings("unchecked")
	private void save() {
	
		if(topic.getValue() == null || name.getValue().isEmpty() || sources.getSelectedItems().isEmpty() ) {
			@SuppressWarnings("deprecation")
			Notification message = new Notification("Interessensprofile muss Name, Themenbereich und Quellen besitzen",Notification.TYPE_WARNING_MESSAGE);
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
			boolean ableWord = false;
			boolean ableLogic = true;
			for (int i = 0; i < keywords.getComponentCount(); i++) {
				String nextword = null;
			
				 Component a = keywords.getComponent(i);
				
				 if (a instanceof TextField ) {
					 TextField txtfield1 = (TextField) a;
					
					 String txtValue = txtfield1.getValue();
					
					 if (!txtValue.isEmpty()) {
							 if (arraywords.isEmpty()) {
						
						
							 arraywords.add(txtValue);
							 
						 }else { 
							 
							 if (ableWord ) {
								 arraywords.add(txtValue);
								 ableWord = false;
								 ableLogic = true;
								 System.out.println("Logic set: "+ableLogic);
							}
	
						 }
						}
					
					}else {
						if (arraywords.isEmpty())continue;				
						if (ableLogic) {
							NativeSelect<String> nativeSelect = (NativeSelect<String>) a;
								nextword = nativeSelect.getValue();
								arraywords.add(nextword);
								ableWord = true;
								ableLogic =false;
								System.out.println("Able logic "+ableLogic);
						}
					  
					}
						 
							
							 
						
						
						 }
				if (ableWord) {
					arraywords.remove(arraywords.size()-1);
				}
				 
				
				 
				
			profil.setWords(arraywords);
			
			
			
			
			profiles =  (ArrayList<Profile>) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("Profile");	
			@SuppressWarnings("deprecation")
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
				boolean stop = false;
				ArrayList<Profile> profileListCheck =(ArrayList<Profile>) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("Profile");
				if (profileListCheck != null) {
					for (Profile profile : profileListCheck) {
					if (profile.getName().equals(name.getValue())) {
						@SuppressWarnings("deprecation")
						Notification messagee = new Notification("Interessensprofile mit diesem Namen existiert bereits",Notification.TYPE_WARNING_MESSAGE);
						messagee.show(Page.getCurrent());
						stop = true;
					}
				}
				}
				if (!stop) {
					profiles.add(profil);
				}
					
			}
			
			VaadinService.getCurrentRequest().getWrappedSession().setAttribute("Profile", profiles);	
			
			message.setDelayMsec(1500);
			message.show(Page.getCurrent());	
				
			}
			
		

	}
	
	
	/**
	 * Adds the.
	 */
	public void add() {
		/*Profile p1 = (Profile) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("Profile");
		Notification message = new Notification("Profile "+p1.getName(),Notification.TYPE_WARNING_MESSAGE);
		message.show(Page.getCurrent());*/
		
		TextField textField = new TextField();
		NativeSelect<String> nativeSelect = new NativeSelect<String>();
		nativeSelect.setItems("OR","AND","NOT");
		nativeSelect.setValue("OR");
		nativeSelect.setEmptySelectionAllowed(false);
		keywords.addComponents(nativeSelect,textField);
	}
	
	/**
	 * Stop.
	 */
	//reloaded
	private void stop() {
		Page.getCurrent().reload();
	}
}
