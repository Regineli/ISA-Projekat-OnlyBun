package rs.ac.uns.ftn.informatika.jpa.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import rs.ac.uns.ftn.informatika.jpa.model.Location;


/*
 * @Entity anotacija naznacava da je klasa perzistentni entitet. Klasa ima konstruktor bez parametara.
 * Dodatno se moze iskoristiti anotacija @Table("naziv_tabele_u_bazi") kojom se
 * specificira tacan naziv tabele u bazi, sema kojoj pripada, itd. Ako se izostavi ova anotacija, dovoljno je
 * imati anotaciju @Entity i u bazi ce se kreirati tabela sa nazivom klase.
 */

/*
 * Efikasnost asocijacija:
 * 
 * One-To-One:
 * - Unidirekcione/bidirekcione @OneToOne veze sa @MapsId su efikasne
 * - Bidirekcione @OneToOne bez @MapsId su manje efikasne
 * 
 * One-To-Many:
 * - Bidirekcione @OneToMany i unidirekcione @ManyToOne su efikasne
 * - Unidirekcione @OneToMany sa Set kolekcijom su manje efikasne
 * - Unidirekcione @OneToMany sa List kolekcijom su prilično neefikasne
 * 
 * Many-To-Many:
 * - Unidirekcione/bidirekcione @ManyToMany sa Set kolekcijom su efikasne
 * - Unidirekcione/bidirekcione @ManyToMany sa List kolekcijom su prilično neefikasne
 */
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "bunnypost")
public class BunnyPost {

	/*
	 * Svaki entitet ima svoj kljuc (surogat kljuc), dok se strategija generisanja
	 * kljuceva moze eksplicitno podesiti: - AUTO - generisanje kljuceva se oslanja
	 * na perzistencionog provajdera da izabere nacin generisanja (ako je u pitanju
	 * Hibernate, selektuje tip na osnovu dijalekta baze, za najpopularnije baze
	 * izabrace IDENTITY) - IDENTITY - inkrementalno generisanje kljuceva pri svakom
	 * novom insertu u bazu - SEQUENCE - koriste se specijalni objekti baze da se
	 * generisu id-evi - TABLE - postoji posebna tabela koja vodi racuna o
	 * kljucevima svake tabele Vise informacija na:
	 * https://en.wikibooks.org/wiki/Java_Persistence/Identity_and_Sequencing
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/*
	 * Kolona moze imati ime koje se razlikuje od naziva atributa.
	 */
	@Column(name = "details", unique = false, nullable = false)
	private String details;

	/*
	 * Anotacija @Column oznacava da ce neki atribut biti kolona u tabeli
	 */

	@Column(name = "photo", nullable = false)
	private String photo;
	
	
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 'user_id' is the foreign key
    private User user;
	
	@Column(name="time", nullable = false, unique = false)
	private LocalDateTime time;	
	
	
	@OneToMany(mappedBy = "bunnyPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Comment> comments = new HashSet<>();
	
	@ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "location_id") 
    private Location location;

	@Column(name = "likes_count", nullable = false)
    private int likesCount = 0;

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    @ManyToMany
    @JoinTable(
        name = "post_likes",
        joinColumns = @JoinColumn(name = "bunny_post_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> likedByUsers = new HashSet<>();

	
	
	/*
	 * Primer bidirekcione veze 1:n. BunnyPost sadrzi kolekciju ispita, ispit pripada
	 * jednom BunnyPost. Jedna strana veze je anotirana sa @OneToMany, dok je druga
	 * anotirana sa @ManyToOne. Dodatno je iskoriscen atribut mappedBy da se naznaci
	 * ko je vlasnik veze (BunnyPost). U bazi ce se u tabeli Exam kreirati dodatna
	 * kolona koja ce sadrzati id objekata tipa BunnyPost kao strani kljuc. Ako se
	 * izostavi mappedBy kreirace se medjutabela koja ce sadrzati 2 kolone - id
	 * BunnyPosta i id ispita
	 * 
	 * Atributom fetch moze se podesavati nacin dobavljanja povezanih entiteta.
	 * Opcije su EAGER i LAZY. FetchType odlucuje da li ce se ucitati i sve veze sa
	 * odgovarajucim objektom cim se inicijalno ucita sam objekat ili nece. Ako je
	 * FetchType EAGER ucitace se sve veze sa objektom odmah, a ako je FetchType
	 * LAZY ucitace se tek pri eksplicitnom trazenju povezanih objekata (pozivanjem
	 * npr. metode getExams). Vise informacija na:
	 * https://howtoprogramwithjava.com/hibernate-eager-vs-lazy-fetch-type/
	 * 
	 * Pored atributa fetch moze se iskoristiti i atribut cascade. CascadeType
	 * podesen na All dozvoljava da se prilikom svakog cuvanja, izmene ili brisanja
	 * BunnyPosta cuvaju, menjaju ili brisu i ispiti. To znaci da ne moraju unapred da
	 * se cuvaju ispiti pa onda povezuju sa BunnyPostom. orphanRemoval podesen na true
	 * ce obezbediti da se ispiti izbrisu iz baze kada se izbrisu iz kolekcije
	 * ispita u objektu BunnyPost.
	 */
	//@OneToMany(mappedBy = "BunnyPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private Set<Exam> exams = new HashSet<Exam>();

	public BunnyPost() {
		super();
	}

	public BunnyPost(String details, User user) {
		super();
		this.details = details;
		this.user=user;
	}
	


    public BunnyPost(Integer id, String details, String photo) {
        this.id = id;
        this.details = details;
        this.photo = photo;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getPhoto() {
		return photo;
	}
	
	public Set<Comment> getComments() {
		return this.comments;
	}
	
	public void setPhoto(String photo) {
	    this.photo = photo;
	}
	
	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	
	public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
	
	
	/*
	public Set<Exam> getExams() {
		return exams;
	}

	public void setExams(Set<Exam> exams) {
		this.exams = exams;
	}
	
	//kad god postoji bidirekciona veza, obe strane trebaju biti sinhronizovane
	//kroz addChild i removeChild metode u roditeljskom entitetu 
	public void addExam(Exam exam) {
		exams.add(exam);
		exam.setBunnyPost(this);
	}

	public void removeExam(Exam exam) {
		exams.remove(exam);
		exam.setBunnyPost(null);
	}*/


	/*
	 * Pri implementaciji equals and hashCode metoda treba obratiti paznju da se
	 * one razlikuju kada se koristi ORM (Hibernate) i kada se klase posmatraju kao
	 * obicne POJO klase. Hibernate zahteva da entitet mora biti jednak samom sebi kroz sva
	 * stanja tog objekta (tranzijentni (novi objekat), perzistentan (persistent), otkacen (detached) i obrisan (removed)).
	 * To znaci da bi dobar pristup bio da se za generisanje equals i hashCode metoda koristi podatak
	 * koji je jedinstven a poznat unapred (tzv. business key) npr. index BunnyPosta, isbn knjige, itd.
	 * U slucaju da takvog obelezja nema, obicno se implementacija svodi na proveri da li je id (koji je kljuc) isti.
	 * Posto u velikom broju slucajeva id baza generise, to znaci da u tranzijentnom stanju objekti nisu jednaki.
	 * Postoji nekoliko resenja za ovaj problem:
	 * 1. Naci neko jedinstveno obelezje
	 * 2. Koristiti prirodne kljuceve
	 * 3. Pre cuvanja na neki nacin saznati koja je sledeca vrednost koju ce baza generisati pa pozvati setId metodu da se kompletira objekat cak i pre cuvanja
	 * 4. Na drugi nacin implementirati equals i hashCode - primer u klasi Teacher
	 */
	@Override	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		BunnyPost s = (BunnyPost) o;
		if (s.details == null || details == null) {
			return false;
		}
		return Objects.equals(details, s.details);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public void incrementLikes() {
        this.likesCount++;
    }

    public void decrementLikes() {
        if (this.likesCount > 0) {
            this.likesCount--;
        }
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Set<User> getLikedByUsers() {
        return likedByUsers;
    }

    public void addLike(User user) {
        likedByUsers.add(user);
        incrementLikes();
    }

    public void removeLike(User user) {
        likedByUsers.remove(user);
        decrementLikes();
    }

    @Override
    public int hashCode() {
        return Objects.hash(details);
    }

    @Override
    public String toString() {
        return "BunnyPost [id=" + id + ", details=" + details + ", user=" + user.getFirstName() + ", photo=" + photo + "]";
    }
}
