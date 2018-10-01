package my.news.news;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Accordion;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.JavaScript;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Link;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import elemental.json.Json;

// TODO: Auto-generated Javadoc
/**
 * The Class NewsView.
 */
public class NewsView extends NewsViewDesign implements View{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new news view.
	 */
	public NewsView() {	
       
	
		
		@SuppressWarnings("unchecked")
		ArrayList<Profile> profiles =   (ArrayList<Profile>) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("Profile");
		if(profiles != null) {
			int k = 0;
			while (k< profiles.size()) {
			
		
		//TODO
			ArrayList<Sources> sources = new ArrayList<>();
			if(profiles.get(k).getSources() != null) {
			sources.addAll(profiles.get(k).getSources());
			}else {
				sources = null;
			}
			
		ArrayList<NewsObject> fill = new ArrayList<NewsObject>(getNews("https://newsapi.org/v2/everything?apiKey=4346700d77a84e42891f9c2dfef158bc", sources , profiles.get(k).getWords()));
		Layout tab1 = new VerticalLayout(); // Wrap in a layout	
		Accordion newAccordion = new Accordion();
		int i = 0;
		while ( i < fill.size()) {
			
			Button share = new Button("share");
			
			String shareLink = fill.get(i).getUrl();
			HorizontalLayout popupContent = new HorizontalLayout();
			
			TextField linkPopupContent = new TextField();
			linkPopupContent.setValue(shareLink);
			Button buttonCopyLink = new Button("Copy");
			buttonCopyLink.addClickListener(e->copyToClipboard(shareLink));
			popupContent.addComponents(linkPopupContent,buttonCopyLink);
			PopupView popupShare = new PopupView(null,popupContent);
			
			
			HorizontalLayout shareHorizontal = new HorizontalLayout();
			shareHorizontal.addComponents(share,popupShare);
			share.addClickListener(e -> popupShare.setPopupVisible(true));
			//share.addClickListener(e -> shareNews(shareLink));
			Link link = new Link("Weiterlesen!",new ExternalResource(fill.get(i).getUrl()));
			TextField source = new TextField();
			source.setValue(fill.get(i).getSource());
			source.setCaption("Quelle:");
			source.setReadOnly(true);
			TextArea textnews = new TextArea();
			textnews.setCaption("Nachricht: ");
			textnews.setReadOnly(true);
			textnews.setWidth("250px");
			textnews.setValue(fill.get(i).getDescription());			
			Layout tabnews = new VerticalLayout(); // Wrap in a layout
			
			tabnews.addComponent(textnews);
			tabnews.addComponent(source);
			tabnews.addComponent(link);
			tabnews.addComponent(shareHorizontal);	
			
			newAccordion.addTab(tabnews, fill.get(i).getTitle());

			
			i++;
		}
		tab1.addComponent(newAccordion);
		
		newsArea.addTab(tab1,profiles.get(k).getName());
		
		k++;
		}

		
	}
		
	}
	
	/**
	 * Gets the news.
	 *
	 * @param targetURL the target URL
	 * @param sources the sources
	 * @param keyWords the key words
	 * @return the news
	 */
	public static ArrayList<NewsObject> getNews(String targetURL, ArrayList<Sources> sources, ArrayList<String> keyWords){
		boolean firstadd = true;
		boolean firstaddString = true;
		int pagesize = 100;
		targetURL = targetURL + "&pageSize="+pagesize;
		if(sources != null) {
		for (int i = 0; i < sources.size() && i < 20; i++) {
			if (sources.get(i).getId() == "all") {
				continue;
			}
			 if(firstadd == true) {
				
				 targetURL = targetURL + "&sources="+sources.get(i).getId();
				 firstadd = false;
			 }else {
				 targetURL = targetURL + ","+sources.get(i).getId();
				
			}
		}
		}else {
			System.out.println("src null ");
		}
		
		
		if (!keyWords.isEmpty()) {
			for (String string : keyWords) {
				
				if(string ==" ") continue; 
				if (firstaddString == true) {
				targetURL = targetURL + "&q="+ string;	
				firstaddString = false;
				}else {
					
					targetURL = targetURL + "%20"+ string;
				}
				
			}
		}
		
		
		 
		 System.out.println(targetURL);
		 
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
				
				ArrayList<NewsObject> newsList = new ArrayList<NewsObject>();
				
			    int i = 0;
			    while(i < Json.parse(response.toString()).getArray("articles").length()){
			    	try {
			    		
				    	String title = Json.parse(response.toString()).getArray("articles").getObject(i).getString("title");
				    	String url = Json.parse(response.toString()).getArray("articles").getObject(i).getString("url");
				    	String description = Json.parse(response.toString()).getArray("articles").getObject(i).getString("description");
				    	String source = Json.parse(response.toString()).getArray("articles").getObject(i).getObject("source").getString("name");
				    	
				    	NewsObject newObj = new NewsObject(title, url, description, source);
				    	newsList.add(newObj);
	 	
					} catch (Exception e) {
						System.out.println("Error parsing Json String");
						
					}
			    	i++;
			    }
				//print result
			    return newsList;
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Request News failed");
		}
		return null;
		 
			
	}
	
	/**
	 * Execute post.
	 *
	 * @param targetURL the target URL
	 * @return the array list
	 */
	public static ArrayList<String> executePost(String targetURL) {
		  HttpURLConnection connection = null;

		  try {
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("GET");
		    

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
	
	/**
	 * Share news.
	 *
	 * @param link the link
	 */
	@SuppressWarnings("unused")
	private void shareNews(String link){
		System.out.println(link);
		
		JavaScript.getCurrent().execute("if (navigator.share !== undefined){let ttl ='Sample'; let txt ='Just shared';let url='';navigator.share( {title: ttl, text: txt}).then( _ => alert('success')).catch((err) => console.log(err));}else {alert('not supported');} ");
		//Page.getCurrent().getJavaScript().execute(" navigator.share({title: 'Web Fundamentals',      text: 'Check out Web Fundamentals — it rocks!',     url: 'https://developers.google.com/web',  }) .then(() => console.log('Successful share'))    .catch((error) => console.log('Error sharing', error));");
	}
	
	/**
	 * Copy to clipboard.
	 *
	 * @param link the link
	 */
	private void copyToClipboard(String link){
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
	               new StringSelection(link), null
	          );
	}
	
}
