package libraryService.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import libraryService.entities.BookPlantMapper;
import libraryService.entities.BookPlantMapperId;

@Repository
public interface BookPlantMapperRepository extends JpaRepository<BookPlantMapper, BookPlantMapperId>
{
	List<BookPlantMapper> findByBook(long book);
	
	@Transactional
	long deleteByBook(long book);
}
