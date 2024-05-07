package libraryService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import libraryService.entities.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Long>
{

}
