/* ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
 * Author: Loutjie Joubert
 * Date: 28 Aug 2020
 * Project: Truelayer challenge API
 * ---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

package api.embedded.translator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class AppController {

 @GetMapping("/pokemon/{name}")
 public String getTranslation(@PathVariable String name) throws HttpException, IOException{
	 
	String response ="";
	
	/* ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * GET Pokemon description (flavorText in pokemon api)
	 * ---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	
	String flavorText = "";
	
	flavorText = getPokemon(name);
	
	/* ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * GET Shakespearean translation
	 * ---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	
	String translation = "";
	
	translation = getShakespearean(flavorText);
	
	/* ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	 * Process and generate JSON response
	 * ---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
	
	StringBuffer sb = new StringBuffer();
	
	if(translation!=null && !translation.equals("")){
		response = translation;
	}else{	
		response = "No translation found. Try another pokemon name.";
	}	
		
	sb.append("{");
	sb.append("\n");
	sb.append("\"name\":\""+name+"\",");
	sb.append("\n");
	sb.append("\"description\":\""+response+"\"");
	sb.append("\n");
	sb.append("}");
	
	response = sb.toString();

	return response ;
	
 }
 public String getShakespearean(String flavorText) throws IOException{
		
		String translation = "Waiting for translation";
		
		String endpoint = "https://api.funtranslations.com/translate/shakespeare.json";
		
		PostMethod post = new PostMethod(endpoint);
		post.setParameter( "text", flavorText );
		
		HttpClient client = new HttpClient();
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		client.executeMethod(post);
		
		InputStream is = post.getResponseBodyAsStream();
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		try{
			int len;
			byte[] buffer = new byte[1024];
			new String(buffer,"UTF-8");
			while ((len = is.read(buffer))!=-1){
				result.write(buffer,0,len);
				buffer = new byte[1024];			
			}
		}catch(Exception e){}
		
		String resp = result.toString("UTF-8");
		
		int respCode = post.getStatusCode();

		post.releaseConnection();
		
		/* ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
		 * Process JSON response
		 * ---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/		
		try {
			
			JSONObject obj = new JSONObject(resp);
			
			if(obj.has("contents")){
				JSONObject contents = obj.getJSONObject("contents");
				translation = contents.getString("translated");
			}else{
				translation = resp;
			}
			
		} catch (JSONException e) {
			translation = e.getMessage(); 
			e.printStackTrace();
		}
		
		return translation;
		
	}
 
	public String getPokemon (String name) throws HttpException,IOException {
		
		String endpoint = "https://pokeapi.co/api/v2/pokemon/";
		
		HttpClient client = new HttpClient();
		client.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
		
		GetMethod get = new GetMethod(endpoint+name);
		get.setRequestHeader("User-Agent",	"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");    
		
		client.executeMethod(get);
		
		InputStream is = get.getResponseBodyAsStream();
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		try{
			int len;
			byte[] buffer = new byte[1024];
			new String(buffer,"UTF-8");
			while ((len = is.read(buffer))!=-1){
				result.write(buffer,0,len);
				buffer = new byte[1024];			
			}
		}catch(Exception e){}
		
		String resp = result.toString("UTF-8");
		
		get.releaseConnection();
		
		String flavorText = "default";
		String speciesUrl = "";
		
		/* ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
		 * First get the species url in order to retrieve the flavor_text for that species
		 * ---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		
		try { 

			JSONObject obj = new JSONObject(resp);

			JSONObject pokemonSpecies = obj.getJSONObject("species");
			
			speciesUrl = pokemonSpecies.getString("url");
			
		} catch (JSONException e) {
			flavorText = e.getMessage();
			e.printStackTrace();
		}
		
		get = new GetMethod(speciesUrl);
		get.setRequestHeader("User-Agent",	"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
		
		client.executeMethod(get);
		
		is = get.getResponseBodyAsStream();
		result = new ByteArrayOutputStream();
		try{
			int len;
			byte[] buffer = new byte[1024];
			new String(buffer,"UTF-8");
			while ((len = is.read(buffer))!=-1){
				result.write(buffer,0,len);
				buffer = new byte[1024];			
			}
		}catch(Exception e){
			
		}
		resp = result.toString("UTF-8");
		
		get.releaseConnection();
		
		/* ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
		 * Get flavor_text for the species with the specific color and language = english (en)
		 * ---------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
		try { 

			JSONObject obj = new JSONObject(resp);
			
			JSONArray pokemonFlavorText = obj.getJSONArray("flavor_text_entries");
			JSONObject pokemonSpeciesColor = obj.getJSONObject("color");
			
			String color = pokemonSpeciesColor.getString("name");
		
			for(int i=0;i<pokemonFlavorText.length();i++)
	        {
				
	            JSONObject flavor = pokemonFlavorText.getJSONObject(i);
	            
	            JSONObject flavorVersion = flavor.getJSONObject("version");
	            String flavorName = flavorVersion.optString("name");
	            
	            JSONObject flavorLanguage = flavor.getJSONObject("language");
	            String flavorLang = flavorLanguage.optString("name");
	            
	            if(color.equals(flavorName) && flavorLang.equals("en")){
	            		flavorText = flavor.optString("flavor_text");
	            		break;
	            }
	              
	        }
			
		} catch (JSONException e) {
			flavorText = e.getMessage();
			e.printStackTrace();
		}
		
		return flavorText;
		
	}
}