package com.nashtech.sharing.jpa;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.nashtech.sharing.jpa.service.AuthorService;
import com.nashtech.sharing.jpa.service.EnhancementService;
import jakarta.persistence.LockModeType;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class JpaShareApplication {


    @Bean
    CommandLineRunner runner (EnhancementService enhancementService,
                              AuthorService authorService) {
        return args -> {
//            Enhancement enhancement = enhancementService.findOneById(1L).get();
//            enhancement.setTitle("112233");
//            log.info("{}", enhancement);
//            enhancementService.save(enhancement);

//            projection(authorService);

//            lockSimulatePessimistic(enhancementService,
//                                    LockModeType.PESSIMISTIC_WRITE,
//                                    LockModeType.PESSIMISTIC_READ, 1L, 2L);

        };
    }

    public static void main (String[] args) {
        SpringApplication.run(JpaShareApplication.class, args);
    }


    static void projection (AuthorService authorService) {
        authorService.getFullAuthor().forEach(e -> {
            log.info("{} {}", e.getAuthorId(), e.getEmail());
        });
        authorService.getAuthorProjection().forEach(e -> {
            log.info("{} - {} - {} - {}", e.getAuthorId(), e.getName(), e.getEmail(), e.getAddress());
        });

    }


    @SneakyThrows
    static void lockSimulatePessimistic (EnhancementService dao, LockModeType lockModeType1, LockModeType lockModeType2, Long id1, Long id2) {

        log.info("lockSimulatePessimistic {} {}", lockModeType1, lockModeType2);
        var f1 = CompletableFuture
            .runAsync(
                () -> dao.findAndLock(id1, 0, 7000, "task 1", lockModeType1));
        var f2 = CompletableFuture
            .runAsync(
                () -> {
                    if ( lockModeType2 == null ) {
                        return;
                    }
                    dao.findAndLock(id2, 100, 0, "task 2", lockModeType2);
                }

            );


        CompletableFuture.allOf(f1, f2).get();
        assert f1.isDone();
        assert f2.isDone();

    }

    @SneakyThrows
    public static void sleep (int num) {
        TimeUnit.MILLISECONDS.sleep(num);
    }

}
