package libraryService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import libraryService.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>
{ 

}
