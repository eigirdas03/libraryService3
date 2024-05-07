package libraryService.entities;

import java.io.Serializable;

public class BookPlantMapperId implements Serializable
{
	private static final long serialVersionUID = 7221940047673228866L;
	
	long book;
	long plant;
	
	public BookPlantMapperId()
	{
		
	}

	public BookPlantMapperId(long book, long plant)
	{
		this.book = book;
		this.plant = plant;
	}

	public long getBook()
	{
		return book;
	}

	public void setBook(long book)
	{
		this.book = book;
	}

	public long getPlant()
	{
		return plant;
	}

	public void setPlant(long plant)
	{
		this.plant = plant;
	}
}
