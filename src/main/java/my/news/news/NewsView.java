package my.news.news;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import elemental.json.Json;

public class NewsView extends NewsViewDesign implements View{

	public NewsView() {	
       
		//nachrichten1.setValue("Nachrichten text Test 1");
		//nachrichten2.setValue("Nachrichten text Test 2");
	
		//newsContainer.addTab(texts, "Nachrichtenprofile 1");
		//newsContainer.addTab(textss ,"Nachrichtenprofile 2");
		ArrayList<String> fill = new ArrayList<String>(executePost("https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=4346700d77a84e42891f9c2dfef158bc"));
		int i = 0;
		while ( i < fill.size()) {
			Layout tab1 = new VerticalLayout(); // Wrap in a layout	
			Accordion newAccordion = new Accordion();
			Link link = new Link("Weiterlesen!",
			        new ExternalResource(fill.get(i)));
			TextField source1 = new TextField();
			source1.setValue("BBC");
			source1.setCaption("Quelle:");
			source1.setReadOnly(true);
			TextArea textnews1 = new TextArea();
			textnews1.setCaption("Nachricht: ");
			textnews1.setValue(fill.get(i+1));
			Layout tabnews1 = new VerticalLayout(); // Wrap in a layout
			tabnews1.addComponent(textnews1);
			tabnews1.addComponent(source1);
			tabnews1.addComponent(link);
			textnews1.setReadOnly(true);
			newAccordion.addTab(tabnews1, fill.get(i+2));
			Link link2 = new Link("Weiterlesen!",
			        new ExternalResource(fill.get(i+3)));
			TextField source2 = new TextField();
			source2.setValue("BBC");
			source2.setCaption("Quelle:");
			source2.setReadOnly(true);
			TextArea textnews2 = new TextArea();
			textnews2.setCaption("Nachricht: ");
			textnews2.setValue(fill.get(i+4));
			Layout tabnews2 = new VerticalLayout(); // Wrap in a layout
			tabnews2.addComponent(textnews2);
			tabnews2.addComponent(source2);
			tabnews2.addComponent(link2);
			textnews2.setReadOnly(true);
			newAccordion.addTab(tabnews2, fill.get(i+5));
			
			tab1.addComponent(newAccordion);
			newsArea.addTab(tab1,"Profile");
			
			i = i +6;
		}
		/*Layout tab1 = new VerticalLayout(); // Wrap in a layout	
		Accordion newAccordion = new Accordion();
		Link link = new Link("Weiterlesen!",
		        new ExternalResource("https://www.nytimes.com/2018/09/04/us/politics/arizona-senate-mccain.html"));
		TextField source1 = new TextField();
		source1.setValue("BBC");
		source1.setCaption("Quelle:");
		source1.setReadOnly(true);
		TextArea textnews1 = new TextArea();
		textnews1.setCaption("Nachricht: ");
		textnews1.setValue("Gov. Doug Ducey, who made the selection, was facing a difficult balancing act in trying to please Trump supporters while not alienating McCain’s family and loyalists.");
		Layout tabnews1 = new VerticalLayout(); // Wrap in a layout
		tabnews1.addComponent(textnews1);
		tabnews1.addComponent(source1);
		tabnews1.addComponent(link);
		textnews1.setReadOnly(true);
		newAccordion.addTab(tabnews1,"Jon Kyl, Former Senator, Will Replace McCain in Arizona");
		TextArea textnews2 = new TextArea();
		textnews2.setValue("Anybody who’s ever published even the slightest of critiques of Tesla’s Elon Musk can probably relate to this response from his supporters: “Don’t try to understand a mindset you will never have.");
		Layout tabnews2 = new VerticalLayout(); // Wrap in a layout
		tabnews2.addComponent(textnews2);
		textnews2.setReadOnly(true);
		newAccordion.addTab(tabnews2,"Poll: Elon Musk fans are the worst");
		
		tab1.addComponent(newAccordion);
		newsArea.addTab(tab1,"Profile 1");	
		
				
		Layout tab2 = new VerticalLayout(); // Wrap in a layout
		Accordion newAccordion2 = new Accordion();
		TextArea textnews3 = new TextArea();
		textnews3.setValue("The Mercedes-Benz EQC will be the brand's first all-electric production model SUV. It will go on sale in the United States in 2020.");
		Layout tabnews3 = new VerticalLayout(); // Wrap in a layout
		tabnews3.addComponent(textnews3);
		textnews3.setReadOnly(true);
		newAccordion2.addTab(tabnews3,"Mercedes-Benz reveals its first all-electric SUV");
		TextArea textnews4 = new TextArea();
		textnews4.setValue("American factory activity in August expanded at the strongest pace in more than 14 years, despite rising tensions with some of the U.S.’s largest trade partners.");
		Layout tabnews4 = new VerticalLayout(); // Wrap in a layout
		tabnews4.addComponent(textnews4);
		textnews4.setReadOnly(true);
		newAccordion2.addTab(tabnews4,"US Factory Sector Clocks Strongest Growth in 14 Years");
		
		tab2.addComponent(newAccordion2);
		newsArea.addTab(tab2,"Profile 2");	
		 
		//System.out.println(Json.parse(response.toString()).getArray("articles").getObject(1).getString("author"));*/
	}
	public static ArrayList<String> executePost(String targetURL) {
		  HttpURLConnection connection = null;

		  try {
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("GET");
		    connection.setRequestProperty("Content-Type", 
		        "application/x-www-form-urlencoded");
		   
		    
		    connection.setRequestProperty("Content-Language", "en-US");  

		    connection.setUseCaches(false);
		    connection.setDoOutput(true);

		    //Send request
		    DataOutputStream wr = new DataOutputStream (
		        connection.getOutputStream());
		    wr.close();

		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    ArrayList<String> a1 = new ArrayList<String>();
		    int i = 0;
		    while(i < 4){
		    	a1.add(Json.parse(response.toString()).getArray("articles").getObject(i).getString("url"));
		    	a1.add(Json.parse(response.toString()).getArray("articles").getObject(i).getString("description"));
		    	a1.add(Json.parse(response.toString()).getArray("articles").getObject(i).getString("title"));
		    	
		    	
		    	
		    	i++;
		    }
		  
		   
		    return a1;
		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  } finally {
		    if (connection != null) {
		      connection.disconnect();
		    }
		  }
		}
}
