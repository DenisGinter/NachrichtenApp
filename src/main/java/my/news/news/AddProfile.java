package my.news.news;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbsoluteLayout;
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

import elemental.json.Json;



public class AddProfile extends AddProfileDesign  implements View{

	private Profile profil = new Profile();
	private List<Sources> sourceList = new ArrayList<>();
	private List<Sources>sourceListUser = new ArrayList<>();
	
	public AddProfile() {
		
		/*if(VaadinService.getCurrentRequest().getWrappedSession().getAttribute("sourceList")!=null) {
			Object sessionSourceList = VaadinService.getCurrentRequest().getWrappedSession().getAttribute("sourceList");
		}*/
		
		
		sourceList = getSources("https://newsapi.org/v2/sources?apiKey=4346700d77a84e42891f9c2dfef158bc");
		
		if(sourceList != null) {
			if(sourceList.size() >= 4) {
				sourceListUser = sourceList.subList(0, 3);
				
			}else {
				sourceListUser = sourceList;
			}
		
		sources.setItems(sourceListUser);
		sources.setItemCaptionGenerator(Sources::getName);
		System.out.println(sourceList.size());
		
		
		}else {
			sources.setCaption("Keine Quellen verfügbar");
		}
		
		
		save.setClickShortcut(KeyCode.ENTER);
		save.addClickListener(e -> save());
		stop.addClickListener(e -> stop());
		add.addClickListener(e -> add());
		btnAddSrc.addClickListener(e -> addSource());
		

	}
	
	
	private void addSource() {
		
		Window addSourceWindow = new Window("Quellen");
		addSourceWindow.center();
		addSourceWindow.setModal(true);
		addSourceWindow.setResizable(true);
		addSourceWindow.setClosable(true);
		
		
		VerticalLayout vL = new VerticalLayout();
		vL.setMargin(true);
		TwinColSelect<Sources> addSourceTwinSelect = new TwinColSelect<>();
		addSourceTwinSelect.setItems(sourceList);
		
		for (Sources component : sourceListUser) {
			addSourceTwinSelect.select(component);
		}
		
		
		addSourceTwinSelect.setItemCaptionGenerator(Sources::getName);
		Button sourceSave = new Button("Save");
		sourceSave.addClickListener(e -> addSourceSelection(addSourceTwinSelect.getSelectedItems()));
		
		vL.addComponents(addSourceTwinSelect,sourceSave);
		addSourceTwinSelect.setWidth("500px");
	
		addSourceWindow.setContent(vL);
		
		
		MyUI.getCurrent().addWindow(addSourceWindow);
		
	}
	
	private void addSourceSelection(Set<Sources> sourceListUserNew) {
				sourceListUser.clear();
				sourceListUser.addAll(sourceListUserNew);
				
				for (Window sourceWindow : MyUI.getCurrent().getWindows()) {
					sourceWindow.close();
				}
				sources.setItems(sourceListUser);
				
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
			    	
			    	Sources newSource = new Sources(id,name);
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
			profil.setTopic(topic.getValue());
			
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
			Page.getCurrent();
			ArrayList<Profile> profiles;
			profiles =  (ArrayList<Profile>) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("Profile");	
			
			if(profiles != null) {
				profiles.add(profil);
				
			}else {
				profiles = new ArrayList<Profile>();
				profiles.add(profil);
					
			}
			
			VaadinService.getCurrentRequest().getWrappedSession().setAttribute("Profile", profiles);	
			
			
			
			
		      		    
			Notification message = new Notification("Interessensprofile gespeichert",Notification.TYPE_HUMANIZED_MESSAGE);
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
