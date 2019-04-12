package eu.n4v.prolicht;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import eu.n4v.prolicht.model.*;

/**
 * DB initialisation
 */
@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initAccount(AccountRepository repository) {
        return args -> {
        };
    }

    @Bean
    CommandLineRunner initApplicant(ApplicantRepository repository) {
        return args -> {
        };
    }

    @Bean
    CommandLineRunner initEvent(EventRepository repository) {
        return args -> {
        };
    }

    @Bean
    CommandLineRunner initEventCategory(EventCategoryRepository repository) {
        return args -> {
        };
    }

    @Bean
    CommandLineRunner initHobby(HobbyRepository repository) {
        return args -> {
        };
    }

    @Bean
    CommandLineRunner initHobbyCategory(HobbyCategoryRepository repository) {
        return args -> {
        };
    }

    @Bean
    CommandLineRunner initKnowledge(KnowledgeRepository repository) {
        return args -> {
        };
    }

    @Bean
    CommandLineRunner initKnowledgeCategory(KnowledgeCategoryRepository repository) {
        return args -> {
        };
    }

    @Bean
    CommandLineRunner initPhoto(PhotoRepository repository) {
        return args -> {
        };
    }


}
