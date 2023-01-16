package ml.geekdjenika.apiinfrabaana;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Controller.AuthController;
import ml.geekdjenika.apiinfrabaana.Model.Categorie;
import ml.geekdjenika.apiinfrabaana.Model.ERole;
import ml.geekdjenika.apiinfrabaana.Model.Role;
import ml.geekdjenika.apiinfrabaana.Repository.CategorieRepository;
import ml.geekdjenika.apiinfrabaana.Repository.RoleRepository;
import ml.geekdjenika.apiinfrabaana.payload.request.SignupRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

import static ml.geekdjenika.apiinfrabaana.Model.ERole.ADMIN;

@SpringBootApplication
@ToString
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ApiinfrabaanaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiinfrabaanaApplication.class, args);
    }

    @Bean
    CommandLineRunner start(RoleRepository roleRepository, AuthController authController, CategorieRepository categorieRepository) {

        return args -> {

            if(roleRepository.findByName(ADMIN).isEmpty()) roleRepository.save(new Role(ADMIN));
            if(roleRepository.findByName(ERole.USER).isEmpty()) roleRepository.save(new Role(ERole.USER));

            Set<String> roles = new HashSet<>();
            roles.add("admin");
            SignupRequest defaultuser = new SignupRequest();
            defaultuser.setUsername("geekdjenika");
            defaultuser.setEmail("djenikaa@gmail.com");
            defaultuser.setPassword("inconnu");
            defaultuser.setRole(roles);
            authController.registerUser(defaultuser);

            if (categorieRepository.findAll().isEmpty()) {
                categorieRepository.save(new Categorie("Gros porteurs"));
                categorieRepository.save(new Categorie("Véhicules légers"));
                categorieRepository.save(new Categorie("Motos"));
                categorieRepository.save(new Categorie("Générales"));
            }

        };
    }

}
