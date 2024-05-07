package libraryService.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@IdClass(BookPlantMapperId.class)
@Table(name = "BooksAboutPlants")
public class BookPlantMapper
{
	@Id
	@Column(name = "book")
	long book;
	
	@Id
	@Column(name = "plant")
	long plant;
	
	public BookPlantMapper()
	{
		
	}

	public BookPlantMapper(long book, long plant)
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
