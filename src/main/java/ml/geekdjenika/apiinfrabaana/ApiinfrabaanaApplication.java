package ml.geekdjenika.apiinfrabaana;

import lombok.ToString;
import ml.geekdjenika.apiinfrabaana.Controller.AuthController;
import ml.geekdjenika.apiinfrabaana.Model.Category;
import ml.geekdjenika.apiinfrabaana.enums.ERole;
import ml.geekdjenika.apiinfrabaana.Model.Language;
import ml.geekdjenika.apiinfrabaana.Model.Role;
import ml.geekdjenika.apiinfrabaana.Repository.CategoryRepository;
import ml.geekdjenika.apiinfrabaana.Repository.LanguageRepository;
import ml.geekdjenika.apiinfrabaana.Repository.RoleRepository;
import ml.geekdjenika.apiinfrabaana.payload.request.SignupRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.HashSet;
import java.util.Set;

import static ml.geekdjenika.apiinfrabaana.enums.ERole.ADMIN;

@SpringBootApplication
@ToString
@CrossOrigin
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ApiinfrabaanaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiinfrabaanaApplication.class, args);
    }

    @Bean
    CommandLineRunner start(RoleRepository roleRepository, AuthController authController, CategoryRepository categoryRepository, LanguageRepository languageRepository) {

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

            if (categoryRepository.findAll().isEmpty()) {
                categoryRepository.save(new Category("Gros porteurs"));
                categoryRepository.save(new Category("Véhicules légers"));
                categoryRepository.save(new Category("Motos"));
                categoryRepository.save(new Category("Générales"));
            }

            if (languageRepository.findAll().isEmpty()) {
                languageRepository.save(new Language("bm"));
                languageRepository.save(new Language("fr"));
                languageRepository.save(new Language("en"));
            }

        };
    }

}
