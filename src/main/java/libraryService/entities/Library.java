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
import libraryService.interfaces.LibraryInfo;

@Entity
@Table(name = "Libraries")
public class Library
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	
	@Column(name = "name")
	String name;
	
	@Column(name = "address")
	String address;
	
	@Column(name = "opened")
	int opened;
	
	@Transient
	List<Book> books;
	
	public Library()
	{

	}
	
	public Library(LibraryInfo libraryInfo)
	{
		this(libraryInfo.getId(), libraryInfo.getName(), libraryInfo.getAddress(), libraryInfo.getOpened());
		setBooksFromInfo(libraryInfo.getBookInfo());
	}

	public Library(long id, String name, String address, int opened) throws LibraryServiceException
	{
		this(name ,address, opened);
		this.id = id;
	}
	
	public Library(String name, String address, int opened) throws LibraryServiceException
	{
		this.name = name;
		this.address = address;
		setOpened(opened);
	}
	
	public Library(Library other) throws LibraryServiceException
	{
		this.name = other.getName();
		this.address = other.getAddress();
		setOpened(other.getOpened());
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

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public int getOpened()
	{
		return opened;
	}

	public void setOpened(int opened) throws LibraryServiceException
	{
		if(opened < 0 || opened > Year.now().getValue())
		{
			throw new LibraryServiceException("invalid year value");
		}
		this.opened = opened;
	}

	public List<Book> getBooks()
	{
		return books;
	}

	public void setBooks(List<Book> books)
	{
		this.books = books;
	}
	
	public void setBooksFromInfo(List<BookInfo> bookInfo)
	{
		books = new ArrayList<Book>();
		
		for(int i = 0; i < bookInfo.size(); ++i)
		{
			books.add(new Book(bookInfo.get(i)));
		}
	}
	
	public LibraryInfo getLibraryInfo()
	{
		LibraryInfo libraryInfo = new LibraryInfo();
		
		libraryInfo.setId(id);
		libraryInfo.setName(name);
		libraryInfo.setAddress(address);
		libraryInfo.setOpened(opened);
		
		List<BookInfo> bookInfo = libraryInfo.getBookInfo();
		
		for(int i = 0; i < books.size(); ++i)
		{
			bookInfo.add(books.get(i).getBookInfo());
		}
		
		return libraryInfo;
	}
}
