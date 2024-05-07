package libraryService.services;

import java.net.ConnectException;
import java.net.UnknownHostException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;



import libraryService.exceptions.LibraryServiceException;
import libraryService.entities.Plant;

@Service
public class PlantService
{
	final static String resourceUrl = "http://plant_service:5000/plants";
	
	public PlantService()
	{
		
	}
	
	public Plant getPlant(long id) throws LibraryServiceException
	{
		RestTemplate restTemplate = new RestTemplate();
		
		ResponseEntity<Plant> response = null;
		
		try
		{
			response = restTemplate.getForEntity(resourceUrl + "/" + id, Plant.class);
		}
		catch(HttpClientErrorException.NotFound e)
		{
		    return null;
		}
		catch(RestClientException e)
		{
			if(e.getCause() instanceof ConnectException || e.getCause() instanceof UnknownHostException)
			{
				throw new LibraryServiceException("Plant service is down");
			}
		}
		
		if(response == null)
		{
			throw new LibraryServiceException("Plant service is down");
		}
		
		return response.getBody();
	}
	
	public Plant addPlant(Plant plant) throws LibraryServiceException
	{
		try
		{
			RestTemplate restTemplate = new RestTemplate();
			
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
			
			JSONObject requestJson = new JSONObject(plant);
			requestJson.put("sellers", new JSONArray());
			
		    HttpEntity<String> request = new HttpEntity<String>(requestJson.toString(), headers);
			
			ResponseEntity<String> response = restTemplate.postForEntity(resourceUrl, request, String.class);
			
			JSONObject responseJson = new JSONObject(response.getBody());
			
			plant.setId(responseJson.getLong("id"));
		}
		catch(RestClientException e)
		{
			if(e.getCause() instanceof ConnectException || e.getCause() instanceof UnknownHostException)
			{
				throw new LibraryServiceException("Plant service is down");
			}
		}
		
		return plant;
	}
	
	public Plant updatePlant(Plant plant) throws LibraryServiceException
	{
		try
		{
			RestTemplate restTemplate = new RestTemplate();
			
			HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
			
			JSONObject requestJson = new JSONObject(plant);
			requestJson.put("sellers", new JSONArray());
			
		    HttpEntity<String> request = new HttpEntity<String>(requestJson.toString(), headers);
			
			restTemplate.exchange(resourceUrl + "/" + plant.getId(), HttpMethod.PUT, request, Void.class);
			
		}
		catch(RestClientException e)
		{
			if(e.getCause() instanceof ConnectException || e.getCause() instanceof UnknownHostException)
			{
				throw new LibraryServiceException("Plant service is down");
			}
		}
		
		return plant;
	}
	
	public void deletePlant(long id) throws LibraryServiceException
	{
		try
		{
			RestTemplate restTemplate = new RestTemplate();
			
			restTemplate.delete(resourceUrl + "/" + id);
		}
		catch(HttpClientErrorException.NotFound e)
		{
			
		}
		catch(RestClientException e)
		{
			if(e.getCause() instanceof ConnectException || e.getCause() instanceof UnknownHostException)
			{
				throw new LibraryServiceException("Plant service is down");
			}
		}
	}

}
