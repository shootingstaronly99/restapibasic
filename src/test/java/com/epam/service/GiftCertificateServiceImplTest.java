package com.epam.service;


import com.epam.esm.entity.GiftCertificates;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.NullPointerException;
import com.epam.esm.repository.impl.GiftCertificatesRepoImpl;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GiftCertificateServiceImplTest {

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    @Mock
    private GiftCertificatesRepoImpl giftCertificatesRepo;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
        giftCertificatesRepo = new GiftCertificatesRepoImpl(dataSource);
        giftCertificateService = new GiftCertificateServiceImpl(giftCertificatesRepo);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("/drop.sql"));
    }

    @Test
    void shouldFindAllGift() throws NullPointerException {
        List<GiftCertificates> giftCertificates = giftCertificateService.getAll();
        assertEquals(5, giftCertificates.size());
    }

    @Test
    void shouldCreateGift() throws NullPointerException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createDate = LocalDateTime.parse("2022-10-17 11:15:10", formatter);
        LocalDateTime lastUpdateDate = LocalDateTime.parse("2022-10-05 11:15:10", formatter);
        Long id = 1L;
        Tag tag = new Tag(id, "tagName1");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        GiftCertificates giftCertificateToCreate = new GiftCertificates(5L, "egiftCertificate5", "description5", 5.55, 2, createDate, lastUpdateDate, tags);
        giftCertificateService.create(giftCertificateToCreate);

        Optional<GiftCertificates> giftCertificateToFindOpt = giftCertificateService.findById(5);
        GiftCertificates giftCertificateToFind = new GiftCertificates();
        if (giftCertificateToFindOpt.isPresent()) {
            giftCertificateToFind = giftCertificateToFindOpt.get();
        }
        boolean check = giftCertificateToFind.equals(giftCertificateToFindOpt.get());
        assertTrue(check);
    }


    @Test
    void shouldDelete() {
        giftCertificateService.delete(3);
        List<GiftCertificates> afterDelete = giftCertificatesRepo.findAll();
        assertEquals(4, afterDelete.size());
    }

    @Test
    void shouldUpdate() throws IncorrectParameterException,NullPointerException {
        int targetGift = 3;
        Optional<GiftCertificates> certificate = giftCertificateService.findById(targetGift);
        GiftCertificates giftCertificate = new GiftCertificates();
        if (certificate.isPresent()) {
            giftCertificate = certificate.get();
        }
        giftCertificate.setName("Update name gift " + targetGift);
        giftCertificate.setDescription("Update desc gift" + targetGift);
        giftCertificateService.update(3, giftCertificate);
        Optional<GiftCertificates> updatedCertificate = giftCertificatesRepo.findById(targetGift);
        GiftCertificates updatedGiftCertificate = new GiftCertificates();
        if (updatedCertificate.isPresent()) {
            updatedGiftCertificate = updatedCertificate.get();
        }
        String name = giftCertificate.getName();
        String updatedName = updatedGiftCertificate.getName();
        assertEquals(name, updatedName);
    }
}
