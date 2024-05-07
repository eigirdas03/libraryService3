package libraryService.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import libraryService.exceptions.LibraryServiceException;
import libraryService.entities.Book;
import libraryService.entities.Plant;
import libraryService.repositories.BookRepository;

@Service
public class BookService
{
	BookRepository bookRepository;
	
	BookLibraryMapperService bookLibraryMapperService;
	
	PlantService plantService;
	BookPlantMapperService bookPlantMapperService;
	
	public BookService(BookRepository bookRepository, BookLibraryMapperService bookLibraryMapperService, 
			PlantService plantService, BookPlantMapperService bookPlantMapperService) throws LibraryServiceException
	{
		this.bookRepository = bookRepository;
		
		this.bookLibraryMapperService = bookLibraryMapperService;
		
		this.plantService = plantService;
		this.bookPlantMapperService = bookPlantMapperService;
		
        bookRepository.save(new Book("name1 surname1", "title1", 2000));
        bookRepository.save(new Book("name2 surname2", "title2", 2001));
        bookRepository.save(new Book("name3 surname3", "title3", 2002));
	}
	
	public void checkIfBookExists(Optional<Book> book) throws LibraryServiceException
	{
		if(book.isPresent() == false)
		{
			throw new LibraryServiceException("Book does not exist");
		}
	}
	
	public void checkIfBookExists(long id) throws LibraryServiceException
	{
		if(bookRepository.findById(id).isPresent() == false)
		{
			throw new LibraryServiceException("Book does not exist");
		}
	}
	
	public void checkIfBooksExist(List<Long> books) throws LibraryServiceException
	{
		for(int i = 0; i < books.size(); ++i)
		{
			if(bookRepository.findById(books.get(i)).isPresent() == false)
			{
				throw new LibraryServiceException("Book with id " + books.get(i) + " does not exist");
			}
		}
	}
	
	public void checkIfBookDoesNotExist(long id) throws LibraryServiceException
	{
		if(bookRepository.findById(id).isPresent())
		{
			throw new LibraryServiceException("Book already exists");
		}
	}
	
	public List<Book> getAllBooks() throws LibraryServiceException
	{
		List<Book> books = bookRepository.findAll();
		
		if(books.size() == 0)
		{
			throw new LibraryServiceException("No books exist");
		}
		
		for(int i = 0; i < books.size(); ++i)
		{
			Book book = books.get(i);
			List<Long> plantsIds = bookPlantMapperService.getPlantsIdsLinkedToBook(book.getId());
			List<Plant> plants = new ArrayList<>();
			
			for(int j = 0; j < plantsIds.size(); ++j)
			{
				Plant plant = plantService.getPlant(plantsIds.get(j));
				
				if(plant != null)
				{
					plants.add(plant);
				}
				else
				{
					plants.add(new Plant(plantsIds.get(j), null, null));
				}
			}
			
			book.setPlants(plants);
		}
		
		return books;
	}

	public Book getBookById(long id) throws LibraryServiceException
	{
		Optional<Book> book = bookRepository.findById(id);
		
		checkIfBookExists(book);
		
		List<Long> plantsIds = bookPlantMapperService.getPlantsIdsLinkedToBook(book.get().getId());
		List<Plant> plants = new ArrayList<>();
		
		for(int j = 0; j < plantsIds.size(); ++j)
		{
			Plant plant = plantService.getPlant(plantsIds.get(j));
			
			if(plant != null)
			{
				plants.add(plant);
			}
			else
			{
				plants.add(new Plant(plantsIds.get(j), null, null));
			}
		}
		
		book.get().setPlants(plants);
		
		return book.get();
	}
	
	@Transactional(rollbackFor = LibraryServiceException.class)
	public Book addBook(Book book) throws LibraryServiceException
	{
		Book newBook = bookRepository.save(new Book(book));
		
		long bookId = newBook.getId();
		
		List<Plant> plants = book.getPlants();
		
		for(int i = 0; i < plants.size(); ++i)
		{
			Plant plant = plants.get(i);
			plant = plantService.addPlant(plant);
			
			bookPlantMapperService.linkPlantToBook(bookId, plant.getId());
		}
		
		newBook.setPlants(plants);
		
		return newBook;
	}
	
	@Transactional(rollbackFor = LibraryServiceException.class)
	public Book updateBook(Book newBookData) throws LibraryServiceException
	{
		long id = newBookData.getId();
		
		Optional<Book> book = bookRepository.findById(id);
		checkIfBookExists(book);
		
		Book bookData = book.get();
		
		bookData.setAuthor(newBookData.getAuthor());
		bookData.setTitle(newBookData.getTitle());
		bookData.setPublished(newBookData.getPublished());
		
		Book updatedBook = bookRepository.save(bookData);
		
		bookPlantMapperService.deleteByBookFromRepository(id);
		
		List<Plant> plants = newBookData.getPlants();
		
		for(int i = 0; i < plants.size(); ++i)
		{
			Plant plant = plants.get(i);
			
			if(plantService.getPlant(plant.getId()) != null)
			{
				plantService.updatePlant(plant);
			}
			else
			{
				plant = plantService.addPlant(plant);
			}
			
			bookPlantMapperService.linkPlantToBook(id, plant.getId());
		}
		
		updatedBook.setPlants(plants);
		
		return updatedBook;
	}
	
	
	public void deleteBook(long id) throws LibraryServiceException
	{
		Optional<Book> book = bookRepository.findById(id);
		checkIfBookExists(book);
		
		bookRepository.deleteById(id);
		
		bookLibraryMapperService.deleteByBookFromRepository(id);
		
		List<Long> plantIds = bookPlantMapperService.getPlantsIdsLinkedToBook(id);
		bookPlantMapperService.deleteByBookFromRepository(id);
		
		for(int i = 0; i < plantIds.size(); ++i)
		{
			plantService.deletePlant(plantIds.get(i));
		}
	}
}
