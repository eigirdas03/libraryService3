package libraryService.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import libraryService.exceptions.LibraryServiceException;
import libraryService.interfaces.AddLibraryRequest;
import libraryService.interfaces.BookId;
import libraryService.interfaces.UpdateLibraryBooksRequest;
import libraryService.interfaces.UpdateLibraryRequest;
import libraryService.entities.Book;
import libraryService.entities.Library;
import libraryService.repositories.LibraryRepository;


@Service
public class LibraryService
{
	LibraryRepository libraryRepository;
	
	BookService bookService;
	BookLibraryMapperService bookLibraryMapperService;

	public LibraryService(LibraryRepository libraryRepository, BookService bookService, 
			BookLibraryMapperService bookLibraryMapperService) throws LibraryServiceException
	{
		this.libraryRepository = libraryRepository;
		
		this.bookService = bookService;
		this.bookLibraryMapperService = bookLibraryMapperService;
		
        libraryRepository.save(new Library("library1", "address 1-1", 1990));
        libraryRepository.save(new Library("library2", "address 1-2", 1991));
        libraryRepository.save(new Library("library3", "address 1-3", 1992));
	}
	
	public void checkIfLibrarykExists(Optional<Library> library) throws LibraryServiceException
	{
		if(library.isPresent() == false)
		{
			throw new LibraryServiceException("Library does not exist");
		}
	}
	
	public void checkIfLibrarykExists(long id) throws LibraryServiceException
	{
		checkIfLibrarykExists(libraryRepository.findById(id));
	}
	
	public void checkIfLibraryDoesNotExist(long id) throws LibraryServiceException
	{
		if(libraryRepository.findById(id).isPresent())
		{
			throw new LibraryServiceException("Library already exists");
		}
	}
	
	public List<Library> getAllLibraries() throws LibraryServiceException
	{
		List<Library> libraries = libraryRepository.findAll();
		
		if(libraries.size() == 0)
		{
			throw new LibraryServiceException("No libraries exist");
		}
		
		List<Long> booksIds = null;
		ArrayList<Book> books = null;
		
		for(int i = 0; i < libraries.size(); ++i)
		{
			booksIds = bookLibraryMapperService.getAllLibraryBooksIds(libraries.get(i).getId());
			books = new ArrayList<>();
			
			for(int j = 0; j < booksIds.size(); ++j)
			{
				books.add(bookService.getBookById(booksIds.get(j)));
			}
			
			libraries.get(i).setBooks(books);
		}
		
		return libraries;
	}
	
	
	public Library getLibraryById(long id) throws LibraryServiceException
	{
		Optional<Library> library = libraryRepository.findById(id);
		checkIfLibrarykExists(library);
		
		List<Long> booksIds = bookLibraryMapperService.getAllLibraryBooksIds(id);
		ArrayList<Book> books = new ArrayList<>();
		
		for(int i = 0; i < booksIds.size(); ++i)
		{
			books.add(bookService.getBookById(booksIds.get(i)));
		}
		
		library.get().setBooks(books);
		
		return library.get();
	}
	
	private List<Long> getBooksFromRequest(List<BookId> booksTemp)
	{
		List<Long> books = new ArrayList<Long>();
		
		for(int i = 0; i < booksTemp.size(); ++i)
		{
			books.add(booksTemp.get(i).getId());
		}
		
		return books;
	}
	
	public Library addLibrary(AddLibraryRequest request) throws LibraryServiceException
	{
		List<Long> books = getBooksFromRequest(request.getBookId());
		
		bookService.checkIfBooksExist(books);
		bookLibraryMapperService.checkIfBooksAreNotInOtherLibraries(books);
		
		Library library = new Library(request.getName(), request.getAddress(), request.getOpened());
		
		Library newLibrary = libraryRepository.save(library);
		
		for(int i = 0; i < books.size(); ++i)
		{
			bookLibraryMapperService.addBookToLibrary(newLibrary.getId(), books.get(i));
		}
		
		return getLibraryById(newLibrary.getId());
	}
	
	public Library updateLibrary(UpdateLibraryRequest request) throws LibraryServiceException
	{
		long id = request.getId();
		
		Optional<Library> library = libraryRepository.findById(id);
		checkIfLibrarykExists(library);
		
		List<Long> books = getBooksFromRequest(request.getBookId());
		
		if(books.size() == 0)
		{
			throw new LibraryServiceException("Books array in request body cannot be empty");
		}
		
		bookService.checkIfBooksExist(books);
		bookLibraryMapperService.checkIfBooksAreNotInOtherLibraries(id, books);
		
		Library libraryData = library.get();
		
		libraryData.setName(request.getName());
		libraryData.setAddress(request.getAddress());
		libraryData.setOpened(request.getOpened());
		
		libraryRepository.save(libraryData);
		
		bookLibraryMapperService.deleteByLibraryFromRepository(id);
		
		for(int i = 0; i < books.size(); ++i)
		{
			bookLibraryMapperService.addBookToLibrary(id, books.get(i));
		}
		
		return getLibraryById(id);
	}
	
	public Library updateLibraryBooks(UpdateLibraryBooksRequest request) throws LibraryServiceException
	{
		long id = request.getId();
		List<Long> books = getBooksFromRequest(request.getBookId());
		
		Optional<Library> library = libraryRepository.findById(id);
		checkIfLibrarykExists(library);
		
		if(books.size() == 0)
		{
			throw new LibraryServiceException("Books array in request body cannot be empty");
		}
		
		bookService.checkIfBooksExist(books);
		bookLibraryMapperService.checkIfBooksAreNotInOtherLibraries(id, books);
		
		bookLibraryMapperService.deleteByLibraryFromRepository(id);
		
		for(int i = 0; i < books.size(); ++i)
		{
			bookLibraryMapperService.addBookToLibrary(id, books.get(i));
		}
		
		return getLibraryById(id);
	}
	
	public void deleteLibrary(long id) throws LibraryServiceException
	{
		Optional<Library> library = libraryRepository.findById(id);
		
		checkIfLibrarykExists(library);
		
		libraryRepository.deleteById(id);
		
		bookLibraryMapperService.deleteByLibraryFromRepository(id);	
	}
	
	public void removeBookFromLibrary(long libraryId, long book) throws LibraryServiceException
	{
		Optional<Library> library = libraryRepository.findById(libraryId);
		
		checkIfLibrarykExists(library);
		bookService.checkIfBookExists(book);
		
		bookLibraryMapperService.checkIfBookIsInThisLibrary(libraryId, book);
		
		bookLibraryMapperService.removeBookFromLibrary(libraryId, book);
	}

}
