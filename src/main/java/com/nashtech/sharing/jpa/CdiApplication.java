package com.nashtech.sharing.jpa;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.hibernate.Session;
import org.hibernate.engine.spi.SessionImplementor;
import org.springframework.data.util.Pair;

import com.nashtech.sharing.jpa.conf.CDIBoot;
import com.nashtech.sharing.jpa.dao.AuthorDao;
import com.nashtech.sharing.jpa.dao.EnhancementDao;
import com.nashtech.sharing.jpa.entity.Author;
import com.nashtech.sharing.jpa.entity.Enhancement;
import com.nashtech.sharing.jpa.service.AuthorService;
import jakarta.persistence.LockModeType;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class CdiApplication {

    public static void main (String[] args) {
        CDIBoot.run(container -> {
//            var enhancementDao = container.select(EnhancementDao.class).get();
//            Enhancement enhancement = new Enhancement();
//            enhancement.setTitle("Title2");
//            enhancement.setDuplicate(true);
//            enhancement.setPriority("Any1");
//            enhancement.setDescription("descrtion  2");
//            enhancementDao.save(enhancement);
//            enhancementDao.findOneById(1L);

//            var authorDao = container.select(AuthorDao.class).get();
            var authorService = container.select(AuthorService.class).get();
            var a = authorService.findOneById(1L).get();
            log.info("{}", a);
            a.setName("A1");
            authorService.save(a);


            // Transaction
//            twoTransaction(enhancementDao);
            // Locking
//            locking(enhancementDao, 1L, 1L);
            // dirty
//            dirty(enhancementDao);
//            no change no update
            // relationship


            // one2many, many2one. many2many, one2one
//            o2O(authorDao);

        });

    }

    static void o2O (AuthorDao authorDao) {
        //            AuthorProjection author = new AuthorProjection();
//            author.setName("AuthorProjection 2");
//            author.setEmail("abc123@gmail.com");
//            AuthorDetail authorDetail = new AuthorDetail();
//            authorDetail.setAddress("112233");
//            author.setAuthorDetail(authorDetail);
        Author author = authorDao.findOneById(1L).get();
        author.getAuthorDetail().setAddress("addrees3");
        authorDao.save(author);

    }

    static void dirty (EnhancementDao dao) {

        dao.doInJpa(entityManager -> {
            var entity = entityManager.find(Enhancement.class, 1L);
            entity.setTitle("new 1a12");
            var session = entityManager.unwrap(Session.class);
            // Flush the session to synchronize changes with the database
//            session.flush();

            // Check if the entity is dirty
            if ( session.isDirty() ) {
                // Get the EntityEntry associated with the entity
                var sessionImpl = (SessionImplementor) session;
                var entityEntry = sessionImpl.getPersistenceContext().getEntry(entity);
                Arrays.stream(Enhancement.class.getDeclaredFields()).map(
                        f -> {
                            try {
                                f.setAccessible(true);
                                return Pair.of(f.getName(), f.get(entity));
                            }
                            catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    )
                    .forEach(name -> log.info("{}={}", name.getSecond(), entityEntry.getLoadedValue(name.getFirst())));

            }
            else {
                // The entity has not been modified
                System.out.println("The entity has not been modified.");
            }
            return null;
        });

    }


    // Transaction and roll back
    // Validate

    static void twoTransaction (EnhancementDao dao) {
        dao.doInJpa(entityManager -> {
            Enhancement enhancement = entityManager.find(Enhancement.class, 1L);
            enhancement.setTitle("first transaction - 2 " + LocalDateTime.now());
            entityManager.merge(enhancement);
            dao.doInJpa(entityManager1 -> {
                Enhancement enhancement2 = entityManager.find(Enhancement.class, 2L);
                enhancement2.setTitle("second transaction ".repeat(1) + LocalDateTime.now());
                enhancement2.setPriority(" ");
                entityManager.merge(enhancement2);
                return enhancement2;
            });
            return enhancement;
        });
    }

    static void locking (EnhancementDao ticketDao, Long id1, Long id2) {

//        LockModeType.OPTIMISTIC

//        lockSimulatePessimistic(ticketDao, LockModeType.NONE, LockModeType.NONE);
//        LockModeType.READ ~ LockModeType.OPTIMISTIC, null, one transaction
//        lockSimulatePessimistic(ticketDao, LockModeType.OPTIMISTIC, null, id1, id2);
//        writeable

//        LockModeType.READ ~ LockModeType.OPTIMISTIC, LockModeType.OPTIMISTIC
//        lockSimulatePessimistic(ticketDao, LockModeType.OPTIMISTIC, LockModeType.OPTIMISTIC, id1, id2);
//        task 1 fail because the version was changed

//        LockModeType.WRITE ~ LockModeType.OPTIMISTIC_FORCE_IMCREMENT, LockModeType.OPTIMISTIC
//        lockSimulatePessimistic(ticketDao, LockModeType.OPTIMISTIC_FORCE_INCREMENT, null, id1, id2);
//        task 1 fail because the version was changed

//        LockModeType.PESSIMISTIC

//             1. no locking, all task can write
//            lockSimulatePessimistic(ticketDao, LockModeType.NONE, LockModeType.NONE, id1, id2);

//             2. 1 lock for read, 2 no lock, => all can write 2, 1
//             why it just write 2,
//            lockSimulatePessimistic(ticketDao, LockModeType.PESSIMISTIC_READ, LockModeType.NONE, id1, id2);
//             because we use the lock mode LockModeType.PESSIMISTIC_READ
//            Enhancement enhancement = ticketDao.findOneById(2L).get();
//            writeWithRead(ticketDao, enhancement);

//            3
//            LockModeType.PESSIMISTIC_READ and LockModeType.PESSIMISTIC_READ
//            lockSimulatePessimistic(ticketDao, LockModeType.PESSIMISTIC_READ, LockModeType.PESSIMISTIC_READ, id1, id2);
//            exception thrown caused entity was lock and another can read not read and timeout
//            4
//            LockModeType.PESSIMISTIC_WRITE and LockModeType.PESSIMISTIC_READ
//            lockSimulatePessimistic(ticketDao, LockModeType.PESSIMISTIC_WRITE, LockModeType.PESSIMISTIC_READ, id1, id2);
//            exception thrown caused entity was locked by job1 for write  and another can read not read and timeout
//            5
//            LockModeType.PESSIMISTIC_WRITE and LockModeType.PESSIMISTIC_WRITE
//            lockSimulatePessimistic(ticketDao, LockModeType.PESSIMISTIC_WRITE, LockModeType.PESSIMISTIC_WRITE, id1, id2);
//            exception thrown caused entity was locked by job1 for write  and another can read not read and timeout

//            6
//            LockModeType.PESSIMISTIC_FORCE_INCREMENT and LockModeType.NONE
//            lockSimulatePessimistic(ticketDao, LockModeType.PESSIMISTIC_FORCE_INCREMENT, LockModeType.NONE, id1, id2);
//            in case the entity did not change, the version still INCREMENT
//            exception thrown caused entity was locked by job1 for write  and another can read not read and timeout

    }

    static void writeWithRead (EnhancementDao dao, Enhancement o) {
        log.info("title: {}", o.getTitle());
        Enhancement enhancement1 = dao.doInJpa(e -> {
            var t = e.find(Enhancement.class, o.getId(), LockModeType.PESSIMISTIC_READ);
            t.setTitle("ABC" + LocalDateTime.now());
            e.merge(t);
            log.info("title2: {}", t.getTitle());
            return t;
        });
        // The title is no change
        assert o.getTitle().equals(enhancement1.getTitle());
    }

    @SneakyThrows
    static void lockSimulatePessimistic (EnhancementDao dao, LockModeType lockModeType1, LockModeType lockModeType2, Long id1, Long id2) {

        log.info("test {} {}", lockModeType1, lockModeType2);
//        CompletableFuture.runAsync()
        var f1 = CompletableFuture
            .runAsync(
                () -> {
                    log.info("start_read 1 {}", lockModeType1);
                    dao.doInJpa(
                        entityManager -> {
//                            var cb = entityManager.getCriteriaBuilder();
//                            var cr = cb.createQuery(Enhancement.class);
//                            var from = cr.from(Enhancement.class);
//                            cr.select(from);
//                            var ls = entityManager.createQuery(cr).getResultList();
//                            ls.forEach(r -> entityManager.lock(r, lockModeType1));
                            var o = entityManager.find(Enhancement.class, id1, lockModeType1);
                            log.info("end_read 1 {}", lockModeType1);
                            o.setTitle(String.format("1## %s-%s", lockModeType1, LocalDateTime.now()));
                            sleep(2000);
                            entityManager.merge(o);
                            return null;

                        }

                    );
                    log.info("write success 1 {}", lockModeType1);
                });
        var f2 = CompletableFuture
            .runAsync(
                () -> {
                    if ( lockModeType2 == null ) {
                        return;
                    }
                    sleep(10);
                    log.info("start_read 2 {}", lockModeType2);
                    dao.doInJpa(
                        entityManager -> {
                            Map<String, Object> properties = Map.of();
//                            Map<String, Object> properties = Map.of("jakarta.persistence.query.timeout", 200);
                            // This hint defines the timeout in milliseconds to acquire a pessitimistic lock.
                            var o = entityManager
                                .find(Enhancement.class, id2, lockModeType2, properties);
                            log.info("end_read 2 {}", lockModeType2);
                            o.setTitle(String.format("2## %s-%s ", lockModeType2, LocalDateTime.now()));
                            entityManager.merge(o);
                            return o;

                        }
                    );
                    log.info("write success 2 {}", lockModeType2);
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
