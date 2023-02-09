package com.epam.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.repository.GiftCertificatesRepo;
import com.epam.esm.repository.impl.GiftCertificatesRepoImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GiftCertificateRepoTest {

    private GiftCertificatesRepo giftCertificatesRepo;

    @BeforeEach
    public void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
        giftCertificatesRepo = new GiftCertificatesRepoImpl(dataSource);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("/drop.sql"));
    }


    @Test
    void shouldGetGiftCertificateById() throws GiftCertificateException {
        Optional<GiftCertificate> giftCertificateOpt = giftCertificatesRepo.findById(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createDate = LocalDateTime.parse("2022-11-12 11:15:10", formatter);
        LocalDateTime lastUpdateDate = LocalDateTime.parse("2022-11-09 11:15:10", formatter);
        Long id = 7L;
        Tag tag = new Tag(id, "tagName7");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        GiftCertificate giftCertificateToCompare = new GiftCertificate(2L, "bgiftCertificate2", "description2", 2.22, 2, createDate, lastUpdateDate, tags);
        GiftCertificate giftCertificate = new GiftCertificate();

        if (giftCertificateOpt.isPresent()) {
            giftCertificate = giftCertificateOpt.get();
        }

        boolean check = giftCertificate.equals(giftCertificateToCompare);
        assertFalse(check);
    }

    @Test
    void shouldGetAllGiftCertificates() {
        List<GiftCertificate> giftCertificates = giftCertificatesRepo.findAll();
        assertEquals(5, giftCertificates.size());
    }

    @Test
    void shouldUpdateGiftCertificate() throws GiftCertificateException {
        int targetGift = 3;
        //Get certificate to update
        Optional<GiftCertificate> certificate = giftCertificatesRepo.findById(targetGift);
        GiftCertificate giftCertificate = new GiftCertificate();
        if (certificate.isPresent()) {
            giftCertificate = certificate.get();
        }
        //Update it with new values
        giftCertificate.setName("Update name gift " + targetGift);
        giftCertificate.setDescription("Update desc gift" + targetGift);
        giftCertificatesRepo.update(giftCertificate);

        //Get updated GiftCertificate
        Optional<GiftCertificate> updatedCertificate = giftCertificatesRepo.findById(targetGift);
        GiftCertificate updatedGiftCertificate = new GiftCertificate();
        if (updatedCertificate.isPresent()) {
            updatedGiftCertificate = updatedCertificate.get();
        }

        String name = giftCertificate.getName();
        String updatedName = updatedGiftCertificate.getName();
        assertEquals(name, updatedName);
    }

    @Test
    void shouldDeleteCertificate() throws NoSuchFieldException {
        giftCertificatesRepo.delete(2);
        List<GiftCertificate> afterDelete = giftCertificatesRepo.findAll();
        assertEquals(4, afterDelete.size());
    }

    @Test
    void shouldCreateGiftCertificate()throws GiftCertificateException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createDate = LocalDateTime.parse("2022-10-17 11:15:10", formatter);
        LocalDateTime lastUpdateDate = LocalDateTime.parse("2022-10-05 11:15:10", formatter);
        Long id = 1L;
        Tag tag = new Tag(id, "tagName1");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        GiftCertificate giftCertificateToCreate = new GiftCertificate(5L, "egiftCertificate5", "description5", 5.55, 2, createDate, lastUpdateDate, tags);
        giftCertificatesRepo.create(giftCertificateToCreate);
        Optional<GiftCertificate> giftCertificateToFindOpt = giftCertificatesRepo.findById(5);
        GiftCertificate giftCertificateToFind = new GiftCertificate();
        if (giftCertificateToFindOpt.isPresent()) {
            giftCertificateToFind = giftCertificateToFindOpt.get();
        }
        boolean check = giftCertificateToCreate.equals(giftCertificateToFind);
        assertFalse(check);
    }

    @Test
    void shouldFindCertificatesByNameDESC() {
        Map<String, String> query = new HashMap<>();
        query.put("sortByName", "DESC");
        List<GiftCertificate> list = giftCertificatesRepo.getWithFilters(query);
        assertEquals("egiftCertificate5", list.get(0).getName());
    }

    @Test
    void shouldFindCertificatesByNameASC() {
        Map<String, String> query = new HashMap<>();
        query.put("sortByName", "ASC");
        List<GiftCertificate> list = giftCertificatesRepo.getWithFilters(query);
        assertEquals("agiftCertificate1", list.get(0).getName());
    }

    @Test
    void shouldFindCertificatesByDateASC() {
        Map<String, String> query = new HashMap<>();
        query.put("sortByDate", "ASC");
        List<GiftCertificate> list = giftCertificatesRepo.getWithFilters(query);
        assertEquals("dgiftCertificate4", list.get(0).getName());
    }

    @Test
    void shouldFindCertificatesByDateDESC() {
        Map<String, String> query = new HashMap<>();
        query.put("sortByDate", "DESC");
        List<GiftCertificate> list = giftCertificatesRepo.getWithFilters(query);
        assertEquals("egiftCertificate5", list.get(0).getName());
    }

    @Test
    void shouldFindCertificatesByPartName() {
        Map<String, String> query = new HashMap<>();
        query.put("partName", "agiftCertificate1");
        List<GiftCertificate> list = giftCertificatesRepo.getWithFilters(query);
        assertEquals(1, list.size());
    }

    @Test
    void shouldFindCertificatesByDescName() {
        Map<String, String> query = new HashMap<>();
        query.put("partDescription", "description2");
        List<GiftCertificate> list = giftCertificatesRepo.getWithFilters(query);
        assertEquals(1, list.size());
    }
}