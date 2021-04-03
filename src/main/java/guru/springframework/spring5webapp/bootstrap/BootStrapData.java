package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.domain.Author;
import guru.springframework.spring5webapp.domain.Book;
import guru.springframework.spring5webapp.domain.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;


    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository= publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author eric = new Author("Eric", "Evans");
        Book ddd = new Book("Dommain Driven Disign", "123456");
        eric.getBooks().add(ddd);  //LoD violation I know but that's how it's implemented in the video
        ddd.getAuthors().add(eric);

        /*Why Author is persisted first
        The Book-Author relationship has a join table. As Book is the owning side, when you save Book,
        it expects the author id to be present in the database in order to add it to the join table.

        In this case ManyToMany relation is bidirectional (both Book and Author has collection of other side's values)
        But it is Book class which specifies @JoinTable
         */
        authorRepository.save(eric);
        bookRepository.save(ddd);

        Author rod = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EE Development without EJB", "625435145");
        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);

        authorRepository.save(rod);
        bookRepository.save(noEJB);


        System.out.println("Started in Bootstrap");
        System.out.println("Number of Books " + bookRepository.count());

        Publisher pub = new Publisher("Oxford University Press", "Churchil Ave 5", "Oxford", "England", "12312");
        publisherRepository.save(pub);
        System.out.println("Number of publishers: "+publisherRepository.count());
    }
}
