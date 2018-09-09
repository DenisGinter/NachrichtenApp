package my.news.news;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Component;
import com.vaadin.ui.ItemCaptionGenerator;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import elemental.json.Json;



public class AddProfile extends AddProfileDesign  implements View{

	private Profile profil = new Profile();
	
	public AddProfile() {
		
		List<Sources> sourcesList = new ArrayList<>();
		sourcesList = getSources("https://newsapi.org/v2/sources?language=de&apiKey=4346700d77a84e42891f9c2dfef158bc");
		sources.setItems(sourcesList);
		
		sources.setItemCaptionGenerator(Sources::getName);
		
		
		save.setClickShortcut(KeyCode.ENTER);
		save.addClickListener(e -> save());
		stop.addClickListener(e -> stop());
		add.addClickListener(e -> add());

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
			
			if(sources.getValue()!= null) {
				ArrayList<String> src = new ArrayList<>();
				src.add(sources.getValue().toString());
				profil.setSources(src);
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
