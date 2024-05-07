package libraryService.entities;

import java.io.Serializable;

public class BookLibraryMapperId implements Serializable
{
	private static final long serialVersionUID = 1L;
	long library;
	long book;
	
	BookLibraryMapperId()
	{
		
	}

	public BookLibraryMapperId(long library, long book)
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
