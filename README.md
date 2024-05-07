# Library SOAP web service
  Library web service https://github.com/eigirdas03/libraryService2 changed to be accessible with SOAP


## How to run
1. `git clone https://github.com/eigirdas03/libraryService3`
2. `cd libraryService3`
3. `docker-compose up --build` or `docker-compose up --build -d` (detached mode)
4. WSDL can be found in files and http://localhost:80/library_service/library_service.wsdl
   

## Resources

### Library
* id (auto generated)
* name
* address
* opened (year)
* books[]

### Book
* id (auto generated)
* author (name + surname)
* title
* published (year)
* plants[]

### Plant
* id (auto generated)
* name
* type


## Available commands

* get all books
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:book="http://www.library_service.com/book">
   <soapenv:Header/>
   <soapenv:Body>
      <book:getAllBooksRequest/>
   </soapenv:Body>
</soapenv:Envelope>
```

* get specific book
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:book="http://www.library_service.com/book">
   <soapenv:Header/>
   <soapenv:Body>
      <book:getBookByIdRequest>
         <book:id>1</book:id>
      </book:getBookByIdRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

* create a book and plants if specified

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:book="http://www.library_service.com/book" xmlns:plan="http://www.library_service.com/plant">
   <soapenv:Header/>
   <soapenv:Body>
      <book:addBookRequest>
         <book:author>author name and surname</book:author>
         <book:title>book title</book:title>
         <book:published>1999</book:published>
         <!--Zero or more repetitions:-->
         <book:plantInfo>
            <plan:name>plant name</plan:name>
            <plan:type>plant type</plan:type>
         </book:plantInfo>
      </book:addBookRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

* update specific book (if plant id is specified and it exists, the plant will be updated, otherwise a new plant will be created with a random id)
  
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:book="http://www.library_service.com/book" xmlns:plan="http://www.library_service.com/plant">
   <soapenv:Header/>
   <soapenv:Body>
      <book:updateBookRequest>
         <book:bookInfo>
            <book:id>1</book:id>
            <book:author>new author</book:author>
            <book:title>new title</book:title>
            <book:published>2000</book:published>
            <!--Zero or more repetitions:-->
            <book:plantInfo>
               <plan:id>1</plan:id>
               <plan:name>updated plant(if exists) name</plan:name>
               <plan:type>updated plant(if exists) type</plan:type>
            </book:plantInfo>
            
            <book:plantInfo>
               <plan:name>new plant with some name</plan:name>
               <plan:type>new plant with some type</plan:type>
            </book:plantInfo>

         </book:bookInfo>
      </book:updateBookRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

* delete specific book and plants linked to it
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:book="http://www.library_service.com/book">
   <soapenv:Header/>
   <soapenv:Body>
      <book:deleteBookRequest>
         <book:id>1</book:id>
      </book:deleteBookRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

* get all libraries
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:lib="http://www.library_service.com/library">
   <soapenv:Header/>
   <soapenv:Body>
      <lib:getAllLibrariesRequest/>
   </soapenv:Body>
</soapenv:Envelope>
```

* get specific library
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:lib="http://www.library_service.com/library">
   <soapenv:Header/>
   <soapenv:Body>
      <lib:getLibraryByIdRequest>
         <lib:id>1</lib:id>
      </lib:getLibraryByIdRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

* create a library and link existing books to it
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:lib="http://www.library_service.com/library" xmlns:book="http://www.library_service.com/book">
   <soapenv:Header/>
   <soapenv:Body>
      <lib:addLibraryRequest>
         <lib:name> library name</lib:name>
         <lib:address>library address</lib:address>
         <lib:opened>2001</lib:opened>
         <!--Zero or more repetitions:-->
         <lib:bookId>
            <book:id>1</book:id>
         </lib:bookId>
      </lib:addLibraryRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

* update specific library and which books are linked to it
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:lib="http://www.library_service.com/library" xmlns:book="http://www.library_service.com/book">
   <soapenv:Header/>
   <soapenv:Body>
      <lib:updateLibraryRequest>
         <lib:id>1</lib:id>
         <lib:name>new library name</lib:name>
         <lib:address>new library address</lib:address>
         <lib:opened>2002</lib:opened>
         <!--Zero or more repetitions:-->
         <lib:bookId>
            <book:id>1</book:id>
         </lib:bookId>
      </lib:updateLibraryRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

* update which books are linked to library
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:lib="http://www.library_service.com/library" xmlns:book="http://www.library_service.com/book">
   <soapenv:Header/>
   <soapenv:Body>
      <lib:updateLibraryBooksRequest>
         <lib:id>1</lib:id>
         <!--Zero or more repetitions:-->
         <lib:bookId>
            <book:id>2</book:id>
         </lib:bookId>
      </lib:updateLibraryBooksRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

* unlink book from library
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:lib="http://www.library_service.com/library" xmlns:book="http://www.library_service.com/book">
   <soapenv:Header/>
   <soapenv:Body>
      <lib:removeBookFromLibraryRequest>
         <lib:libraryId>1</lib:libraryId>
         <lib:bookId>
            <book:id>2</book:id>
         </lib:bookId>
      </lib:removeBookFromLibraryRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

* delete specific library and unlink books that were linked to it
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:lib="http://www.library_service.com/library">
   <soapenv:Header/>
   <soapenv:Body>
      <lib:deleteLibraryRequest>
         <lib:id>1</lib:id>
      </lib:deleteLibraryRequest>
   </soapenv:Body>
</soapenv:Envelope>
```