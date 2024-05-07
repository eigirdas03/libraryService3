package libraryService.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import libraryService.exceptions.LibraryServiceException;
import libraryService.entities.BookLibraryMapper;
import libraryService.entities.BookLibraryMapperId;
import libraryService.repositories.BookLibraryMapperRepository;

@Service
public class BookLibraryMapperService
{
	BookLibraryMapperRepository bookLibraryMapperRepository;
	
	public BookLibraryMapperService(BookLibraryMapperRepository bookLibraryMapperRepository)
	{
		this.bookLibraryMapperRepository = bookLibraryMapperRepository;
		
        bookLibraryMapperRepository.save(new BookLibraryMapper(1, 1));
        bookLibraryMapperRepository.save(new BookLibraryMapper(1, 2));
	}
	
	public List<BookLibraryMapper> getAllMappings()
	{
		return bookLibraryMapperRepository.findAll();
	}
	
	public List<Long> getAllLibraryBooksIds(long libraryId) throws LibraryServiceException
	{
		List<BookLibraryMapper> mapperData = bookLibraryMapperRepository.findByLibrary(libraryId);
		
		List <Long> libraryBooks = new ArrayList<>();
		
		for(int i = 0; i < mapperData.size(); ++i)
		{
			libraryBooks.add(mapperData.get(i).getBook());
		}
		
		if(libraryBooks.size() == 0)
		{
			return new ArrayList<>();
		}
		
		return libraryBooks;
	}
	
	public void checkIfBooksAreNotInOtherLibraries(List<Long> books) throws LibraryServiceException
	{
		for(int i = 0; i < books.size(); ++i)
		{
			List<BookLibraryMapper> allMapperData = bookLibraryMapperRepository.findByBook(books.get(i));
			
			if(allMapperData.size() != 0)
			{
				throw new LibraryServiceException("Book with id " + books.get(i) + " is in another library");
			}
		}
	}
	
	public void checkIfBooksAreNotInOtherLibraries(long id, List<Long> books) throws LibraryServiceException
	{
		for(int i = 0; i < books.size(); ++i)
		{
			List<BookLibraryMapper> allMapperData = bookLibraryMapperRepository.findByBook(books.get(i));
			
			if(allMapperData.size() == 1)
			{
				if(allMapperData.get(0).getLibrary() != id)
				{
					throw new LibraryServiceException("Book with id " + books.get(i) + " is in another library");
				}
			}
			else if(allMapperData.size() != 0)
			{
				throw new LibraryServiceException("Book with id " + books.get(i) + " is in another library");
			}
		}
	}
	
	public void addBookToLibrary(long library, long book) throws LibraryServiceException
	{
		bookLibraryMapperRepository.save(new BookLibraryMapper(library, book));
	}
	
	public void checkIfBookIsInThisLibrary(long library, long book) throws LibraryServiceException
	{
		Optional<BookLibraryMapper> bookInLibrary = bookLibraryMapperRepository.findById(new BookLibraryMapperId(library, book));
		
		if(bookInLibrary.isPresent() == false)
		{
			throw new LibraryServiceException("Book is not in this library");
		}
	}
	
	public void removeBookFromLibrary(long library, long book)
	{
		bookLibraryMapperRepository.deleteById(new BookLibraryMapperId(library, book));
	}
	
	public void deleteByLibraryFromRepository(long library)
	{
		bookLibraryMapperRepository.deleteByLibrary(library);
	}
	
	public void deleteByBookFromRepository(long book)
	{
		bookLibraryMapperRepository.deleteByBook(book);
	}
}
