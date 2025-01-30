package rs.ac.uns.ftn.informatika.jpa.config;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import rs.ac.uns.ftn.informatika.jpa.RateLimiter;
import rs.ac.uns.ftn.informatika.jpa.model.User;
import rs.ac.uns.ftn.informatika.jpa.repository.UserRepository;
import rs.ac.uns.ftn.informatika.jpa.service.EmailService;

@Component
public class ActivityInterceptor implements HandlerInterceptor {
	
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private EmailService emailService;
	 
	 @Autowired
	 private RateLimiter rateLimiter;
	 

	 @Override
	 public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	     // Prvo uzimamo URI trenutnog zahteva
	     String requestURI = request.getRequestURI();

	     // Lista javnih endpointa
	     List<String> publicEndpoints = Arrays.asList(
	         "/auth/login",
	         "/auth/signup",
	         "/auth/testRegistration",
	         "/h2-console",
	         "/api/foo",
	         "/api/bunnyPosts/public",
	         "/api/users/public",
	         "/api/users/activate",
	         "/api/bunnyPosts/public/testClearCache"
	     );

	     // Ako je zahtev za javni endpoint, dozvoljavamo dalje procesiranje
	     if (publicEndpoints.stream().anyMatch(requestURI::startsWith)) {
	         return true; 
	     }
	     
	     System.out.print("pre svega ");

	     // Ako je korisnik autentifikovan, ažuriramo njegov poslednji login
	     Principal principal = request.getUserPrincipal();
	     if (principal != null) {
	         String email = principal.getName();
	         User user = userRepository.findByUsername(email);
	         System.out.print("prncipals ");
	         System.out.print(principal);
	         System.out.print("mejl");
	         System.out.print(email);
	         System.out.print(user);
	         if (user != null) {
	             user.setLastLogin(LocalDateTime.now());
	             userRepository.save(user);
	             System.out.print("Ovo je u istom redu. ");
	             
	             if (!rateLimiter.isTooManuRequests(email)) {
	                    response.setStatus(429); 
	                    response.getWriter().write("Wait...");
	                    return false;
	                }
	         }
	     }

	     // Metoda vraća true da bi omogućila dalji tok obrade zahteva
	     return true;
	 }


}
