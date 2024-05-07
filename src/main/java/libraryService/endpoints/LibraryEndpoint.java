package libraryService.endpoints;

import java.util.List;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import libraryService.entities.Library;
import libraryService.interfaces.AddLibraryRequest;
import libraryService.interfaces.AddLibraryResponse;
import libraryService.interfaces.DeleteLibraryRequest;
import libraryService.interfaces.DeleteLibraryResponse;
import libraryService.interfaces.GetAllLibrariesRequest;
import libraryService.interfaces.GetAllLibrariesResponse;
import libraryService.interfaces.GetLibraryByIdRequest;
import libraryService.interfaces.GetLibraryByIdResponse;
import libraryService.interfaces.LibraryInfo;
import libraryService.interfaces.RemoveBookFromLibraryRequest;
import libraryService.interfaces.RemoveBookFromLibraryResponse;
import libraryService.interfaces.UpdateLibraryBooksRequest;
import libraryService.interfaces.UpdateLibraryBooksResponse;
import libraryService.interfaces.UpdateLibraryRequest;
import libraryService.interfaces.UpdateLibraryResponse;
import libraryService.services.LibraryService;

@Endpoint
public class LibraryEndpoint
{
	public static final String NAMESPACE = "http://www.library_service.com/library";

	private LibraryService libraryService;
	
	public LibraryEndpoint(LibraryService libraryService)
	{
		this.libraryService = libraryService;
	}
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "getAllLibrariesRequest")
	@ResponsePayload
	public GetAllLibrariesResponse getAllLibraries(@RequestPayload GetAllLibrariesRequest request)
	{
		GetAllLibrariesResponse response = new GetAllLibrariesResponse();
		
		List<Library> libraries = libraryService.getAllLibraries();
		List<LibraryInfo> libraryInfo = response.getLibraryInfo();
		
		for(int i = 0; i < libraries.size(); ++i)
		{
			libraryInfo.add(libraries.get(i).getLibraryInfo());
		}
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "getLibraryByIdRequest")
	@ResponsePayload
	public GetLibraryByIdResponse getLibraryById(@RequestPayload GetLibraryByIdRequest request)
	{
		GetLibraryByIdResponse response = new GetLibraryByIdResponse();
		
		LibraryInfo libraryInfo = libraryService.getLibraryById(request.getId()).getLibraryInfo();
		
		response.setLibraryInfo(libraryInfo);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "addLibraryRequest")
	@ResponsePayload
	public AddLibraryResponse addLibrary(@RequestPayload AddLibraryRequest request)
	{
		AddLibraryResponse response = new AddLibraryResponse();
		
		response.setLibraryInfo(libraryService.addLibrary(request).getLibraryInfo());
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "updateLibraryRequest")
	@ResponsePayload
	public UpdateLibraryResponse updateLibrary(@RequestPayload UpdateLibraryRequest request)
	{
		UpdateLibraryResponse response = new UpdateLibraryResponse();
		
		response.setLibraryInfo(libraryService.updateLibrary(request).getLibraryInfo());
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "updateLibraryBooksRequest")
	@ResponsePayload
	public UpdateLibraryBooksResponse updateLibraryBooks(@RequestPayload UpdateLibraryBooksRequest request)
	{
		UpdateLibraryBooksResponse response = new UpdateLibraryBooksResponse();
		
		response.setLibraryInfo(libraryService.updateLibraryBooks(request).getLibraryInfo());
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "deleteLibraryRequest")
	@ResponsePayload
	public DeleteLibraryResponse deleteLibrary(@RequestPayload DeleteLibraryRequest request)
	{
		DeleteLibraryResponse response = new DeleteLibraryResponse();
		
		libraryService.deleteLibrary(request.getId());
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "removeBookFromLibraryRequest")
	@ResponsePayload
	public RemoveBookFromLibraryResponse removeBookFromLibrary(@RequestPayload RemoveBookFromLibraryRequest request)
	{
		RemoveBookFromLibraryResponse response = new RemoveBookFromLibraryResponse();
		
		libraryService.removeBookFromLibrary(request.getLibraryId(), request.getBookId().getId());
		
		return response;
	}
	
}
