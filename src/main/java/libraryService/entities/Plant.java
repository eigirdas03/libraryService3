package libraryService.entities;


import org.json.JSONPropertyIgnore;

import libraryService.interfaces.PlantInfo;
import libraryService.interfaces.PlantInfoWithOptionalId;
import libraryService.interfaces.PlantInfoWithoutId;

public class Plant
{
	long id;
	
	String name;
	String type;
	
	Plant()
	{
		
	}

	public Plant(long id, String name, String type)
	{
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	public Plant(PlantInfo plantInfo)
	{
		this.id = plantInfo.getId();
		this.name = plantInfo.getName();
		this.type = plantInfo.getType();
	}
	
	public Plant(PlantInfoWithOptionalId plantInfo)
	{
		Long id = plantInfo.getId();
		
		if(id == null)
		{
			this.id = -1;
		}
		else
		{
			this.id = id;
		}
		
		this.name = plantInfo.getName();
		this.type = plantInfo.getType();
	}
	
	public Plant(PlantInfoWithoutId plantInfo)
	{
		this.name = plantInfo.getName();
		this.type = plantInfo.getType();
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
	
	@JSONPropertyIgnore
	public PlantInfo getPlantInfo()
	{
		PlantInfo plantInfo = new PlantInfo();
		plantInfo.setId(id);
		plantInfo.setName(name);
		plantInfo.setType(type);
		
		return plantInfo;
	}
}
