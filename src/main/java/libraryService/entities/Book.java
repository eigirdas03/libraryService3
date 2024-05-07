package libraryService.entities;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import libraryService.exceptions.LibraryServiceException;
import libraryService.interfaces.BookInfo;
import libraryService.interfaces.BookInfoPlantIdOptional;
import libraryService.interfaces.PlantInfo;
import libraryService.interfaces.PlantInfoWithOptionalId;
import libraryService.interfaces.PlantInfoWithoutId;

@Entity
@Table(name = "Books")
public class Book
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	@Column(name = "author")
	String author;
	
	@Column(name = "title")
	String title;
	
	@Column(name = "published")
	int published;
	
	@Transient
	List<Plant> plants;
	
	public Book()
	{
		
	}
	
	public Book(BookInfo bookInfo)
	{
		this(bookInfo.getId(), bookInfo.getAuthor(), bookInfo.getTitle(), bookInfo.getPublished());
		setPlantsFromInfo(bookInfo.getPlantInfo());
	}
	
	public Book(BookInfoPlantIdOptional bookInfo)
	{
		this(bookInfo.getId(), bookInfo.getAuthor(), bookInfo.getTitle(), bookInfo.getPublished());
		setPlantsWithOptionalIdFromInfo(bookInfo.getPlantInfo());
	}

	public Book(long id, String author, String title, int published) throws LibraryServiceException
	{
		this(author, title, published);
		this.id = id;
	}
	
	public Book(String author, String title, int published) throws LibraryServiceException
	{
		this.author = author;
		this.title = title;
		setPublished(published);
	}
	
	public Book(Book other) throws LibraryServiceException
	{
		this.author = other.getAuthor();
		this.title = other.getTitle();
		setPublished(other.getPublished());
	}
	
	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public int getPublished()
	{
		return published;
	}

	public void setPublished(int published) throws LibraryServiceException
	{
		if(published < 0 || published > Year.now().getValue())
		{
			throw new LibraryServiceException("invalid year value");
		}
		this.published = published;
	}

	public List<Plant> getPlants()
	{
		return plants;
	}

	public void setPlants(List<Plant> plants)
	{
		this.plants = plants;
	}
	
	public void setPlantsFromInfo(List<PlantInfo> plantInfo)
	{
		plants = new ArrayList<Plant>();
		
		for(int i = 0; i < plantInfo.size(); ++i)
		{
			plants.add(new Plant(plantInfo.get(i)));
		}
	}
	
	public void setPlantsWithOptionalIdFromInfo(List<PlantInfoWithOptionalId> plantInfo)
	{
		plants = new ArrayList<Plant>();
		
		for(int i = 0; i < plantInfo.size(); ++i)
		{
			plants.add(new Plant(plantInfo.get(i)));
		}
	}
	
	public void setPlantsFromInfoWithoutId(List<PlantInfoWithoutId> plantInfo)
	{
		plants = new ArrayList<Plant>();
		
		for(int i = 0; i < plantInfo.size(); ++i)
		{
			plants.add(new Plant(plantInfo.get(i)));
		}
	}
	
	public BookInfo getBookInfo()
	{
		BookInfo bookInfo = new BookInfo();
		
		bookInfo.setId(id);
		bookInfo.setAuthor(author);
		bookInfo.setTitle(title);
		bookInfo.setPublished(published);
		
		List<PlantInfo> plantInfo = bookInfo.getPlantInfo();
		
		for(int i = 0; i < plants.size(); ++i)
		{
			plantInfo.add(plants.get(i).getPlantInfo());
		}
		
		return bookInfo;
	}
	
}
