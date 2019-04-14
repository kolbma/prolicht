/*
   Copyright 2019 Markus Kolb

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

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
