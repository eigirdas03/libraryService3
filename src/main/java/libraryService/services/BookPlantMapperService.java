package libraryService.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import libraryService.exceptions.LibraryServiceException;
import libraryService.entities.BookPlantMapper;
import libraryService.repositories.BookPlantMapperRepository;

@Service
public class BookPlantMapperService
{
	BookPlantMapperRepository bookPlantMapperRepository;
	

	public BookPlantMapperService(BookPlantMapperRepository bookPlantMapperRepository)
	{
		this.bookPlantMapperRepository = bookPlantMapperRepository;

		bookPlantMapperRepository.save(new BookPlantMapper(1, 1));
		bookPlantMapperRepository.save(new BookPlantMapper(1, 2));
	}
	
	public List<Long> getPlantsIdsLinkedToBook(long book) throws LibraryServiceException
	{	
		List<BookPlantMapper> mapperData = bookPlantMapperRepository.findByBook(book);
		
		List<Long> plantIds = new ArrayList<>();
	
		for(int i = 0; i < mapperData.size(); ++i)
		{
			plantIds.add(mapperData.get(i).getPlant());
		}
		
		if(plantIds.size() == 0)
		{
			return new ArrayList<>();
		}
		
		return plantIds;
	}
	
	public void linkPlantToBook(long book, long plant) throws LibraryServiceException
	{
		bookPlantMapperRepository.save(new BookPlantMapper(book, plant));
	}
	
	public void deleteByBookFromRepository(long book)
	{
		bookPlantMapperRepository.deleteByBook(book);
	}
}
