package libraryService.exceptions;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT)
public class LibraryServiceException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public LibraryServiceException(String message)
	{
		super(message);
	}
}
