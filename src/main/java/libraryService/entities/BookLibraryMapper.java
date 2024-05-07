package libraryService.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;


@Entity
@IdClass(BookLibraryMapperId.class)
@Table(name = "LibraryBooks")
public class BookLibraryMapper
{
	@Id
	@Column(name = "library")
	long library;
	
	@Id
	@Column(name = "book")
	long book;
	
	public BookLibraryMapper()
	{
		
	}

	public BookLibraryMapper(long library, long book)
	{
		this.library = library;
		this.book = book;
	}

	public long getLibrary()
	{
		return library;
	}

	public void setLibrary(long library)
	{
		this.library = library;
	}

	public long getBook()
	{
		return book;
	}

	public void setBook(long book)
	{
		this.book = book;
	}

}
