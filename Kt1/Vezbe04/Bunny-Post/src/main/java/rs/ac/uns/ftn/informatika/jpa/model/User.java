package rs.ac.uns.ftn.informatika.jpa.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import java.sql.Timestamp;
import java.util.Date;

import javax.validation.constraints.Email;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.management.relation.RoleList;
import javax.persistence.*;

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
@Entity
@Table(name = "app_user")
public class User implements UserDetails {

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
	@Column(name = "email", unique = true, nullable = false)
	@Email(message = "Invalid email format")
	private String email;
	
	@Column(name = "username", unique = true, nullable = false)
	@NotNull(message = "Username cannot be null")
	@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
	private String username;
	
	@Column(name = "password", nullable = false)
	@NotNull(message = "Password cannot be null")
	@Size(min = 8, message = "Password must be at least 8 characters")
	private String password;

	/*
	 * Anotacija @Column oznacava da ce neki atribut biti kolona u tabeli
	 */
	@Column(name = "firstName", nullable = false)
	@NotNull(message = "First name cannot be null")
	private String firstName;

	@Column(name = "lastName", nullable = false)
	@NotNull(message = "Last name cannot be null")
	private String lastName;
	
	@Column(name = "address", nullable = true)
	private String address;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status = UserStatus.PENDING_REGISTRATION_CONFIRMATION;
	
	@Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		Timestamp now = new Timestamp(new Date().getTime());
        this.setLastPasswordResetDate(now);
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
	public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    public List<Role> getRole() {
       return roles;
    }


	/*
	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}*/

	//@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private Set<BunnyPost> bunnyPosts = new HashSet<>();
	

    //@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //private Set<Comment> comments = new HashSet<>();
	
	/*
	 * Primer bidirekcione veze 1:n. User sadrzi kolekciju ispita, ispit pripada
	 * jednom User. Jedna strana veze je anotirana sa @OneToMany, dok je druga
	 * anotirana sa @ManyToOne. Dodatno je iskoriscen atribut mappedBy da se naznaci
	 * ko je vlasnik veze (User). U bazi ce se u tabeli Exam kreirati dodatna
	 * kolona koja ce sadrzati id objekata tipa User kao strani kljuc. Ako se
	 * izostavi mappedBy kreirace se medjutabela koja ce sadrzati 2 kolone - id
	 * Usera i id ispita
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
	 * Usera cuvaju, menjaju ili brisu i ispiti. To znaci da ne moraju unapred da
	 * se cuvaju ispiti pa onda povezuju sa Userom. orphanRemoval podesen na true
	 * ce obezbediti da se ispiti izbrisu iz baze kada se izbrisu iz kolekcije
	 * ispita u objektu User.
	 */
	//@OneToMany(mappedBy = "User", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private Set<Exam> exams = new HashSet<Exam>();
    public User() {
		
	}

	public User(Integer id, String email, String username, String password, String firstName, String lastName, String address, UserStatus status, List<Role> roles) {		
	}
	
	public User(Integer id, String email, String username, String password, String firstName, String lastName, String address, UserStatus status) {
	    super();
	    this.id = id;
	    this.email = email;
	    this.username = username;
	    this.password = password;
	    this.firstName = firstName;
	    this.lastName = lastName;
	    this.address = address;
	    this.status = status;
	    this.roles=roles;
	    this.status = status;  
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
		
	/*
	public Set<BunnyPost> getBunnyPosts() {
        return bunnyPosts;
    }

    public void setBunnyPosts(Set<BunnyPost> bunnyPosts) {
        this.bunnyPosts = bunnyPosts;
    }
    */
	
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
		exam.setUser(this);
	}

	public void removeExam(Exam exam) {
		exams.remove(exam);
		exam.setUser(null);
	}*/


	/*
	 * Pri implementaciji equals and hashCode metoda treba obratiti paznju da se
	 * one razlikuju kada se koristi ORM (Hibernate) i kada se klase posmatraju kao
	 * obicne POJO klase. Hibernate zahteva da entitet mora biti jednak samom sebi kroz sva
	 * stanja tog objekta (tranzijentni (novi objekat), perzistentan (persistent), otkacen (detached) i obrisan (removed)).
	 * To znaci da bi dobar pristup bio da se za generisanje equals i hashCode metoda koristi podatak
	 * koji je jedinstven a poznat unapred (tzv. business key) npr. index Usera, isbn knjige, itd.
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
		User s = (User) o;
		if (s.email == null || email == null) {
			return false;
		}
		return Objects.equals(email, s.email);
	}
	
	public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

	@Override
	public int hashCode() {
		return Objects.hashCode(email);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", lastName=" + lastName + ", password=" + password + ", status=" + status + "]";
	}
	
	
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }
	
	@JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
	
	@Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

}
