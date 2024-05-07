package libraryService.endpoints;

import java.util.List;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import libraryService.entities.Book;
import libraryService.interfaces.AddBookRequest;
import libraryService.interfaces.AddBookResponse;
import libraryService.interfaces.BookInfo;
import libraryService.interfaces.DeleteBookRequest;
import libraryService.interfaces.DeleteBookResponse;
import libraryService.interfaces.GetAllBooksRequest;
import libraryService.interfaces.GetAllBooksResponse;
import libraryService.interfaces.GetBookByIdRequest;
import libraryService.interfaces.GetBookByIdResponse;
import libraryService.interfaces.UpdateBookRequest;
import libraryService.interfaces.UpdateBookResponse;
import libraryService.services.BookService;

@Endpoint
public class BookEndpoint
{
	public static final String NAMESPACE = "http://www.library_service.com/book";
	
	private BookService bookService;
	
	public BookEndpoint(BookService bookService)
	{
		this.bookService = bookService;
	}

	@PayloadRoot(namespace = NAMESPACE, localPart = "getAllBooksRequest")
	@ResponsePayload
	public GetAllBooksResponse getAllBooks(@RequestPayload GetAllBooksRequest request)
	{
		GetAllBooksResponse response = new GetAllBooksResponse();
		
		List<Book> books = bookService.getAllBooks();
		List<BookInfo> bookInfo = response.getBookInfo();
		
		for(int i = 0; i < books.size(); ++i)
		{
			bookInfo.add(books.get(i).getBookInfo());
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "getBookByIdRequest")
	@ResponsePayload
	public GetBookByIdResponse getBookById(@RequestPayload GetBookByIdRequest request)
	{
		GetBookByIdResponse response = new GetBookByIdResponse();
		
		BookInfo bookInfo = bookService.getBookById(request.getId()).getBookInfo();
		response.setBookInfo(bookInfo);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "addBookRequest")
	@ResponsePayload
	public AddBookResponse addBook(@RequestPayload AddBookRequest request)
	{
		AddBookResponse response = new AddBookResponse();
		
		Book book = new Book(request.getAuthor(), request.getTitle(), request.getPublished());
		book.setPlantsFromInfoWithoutId(request.getPlantInfo());
		
		response.setBookInfo(bookService.addBook(book).getBookInfo());
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "updateBookRequest")
	@ResponsePayload
	public UpdateBookResponse updateBook(@RequestPayload UpdateBookRequest request)
	{
		UpdateBookResponse response = new UpdateBookResponse();
		
		Book book = new Book(request.getBookInfo());
		
		response.setBookInfo(bookService.updateBook(book).getBookInfo());
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "deleteBookRequest")
	@ResponsePayload
	public DeleteBookResponse deleteBook(@RequestPayload DeleteBookRequest request)
	{
		DeleteBookResponse response = new DeleteBookResponse();
		
		bookService.deleteBook(request.getId());
		
		return response;
	}
}
