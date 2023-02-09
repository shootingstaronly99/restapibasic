package com.epam.service;


import com.epam.esm.common.ResponseModel;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.exception.IncorrectParameterException;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    void shouldFindAllGift() throws GiftCertificateException {
        List<GiftCertificate> giftCertificates = giftCertificateService.getAll().getData();
        assertEquals(5, giftCertificates.size());
    }

    @Test
    void shouldNotFindAllGift() throws GiftCertificateException {
        List<GiftCertificate> giftCertificates = giftCertificateService.getAll().getData();
        boolean gift = 10 == giftCertificates.size();
        assertFalse(gift);
    }

    @Test
    void shouldCreateGift() throws GiftCertificateException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createDate = LocalDateTime.parse("2022-10-17 11:15:10", formatter);
        LocalDateTime lastUpdateDate = LocalDateTime.parse("2022-10-05 11:15:10", formatter);
        Long id = 1L;
        Tag tag = new Tag(id, "tagName1");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        GiftCertificate giftCertificateToCreate = new GiftCertificate(5L, "egiftCertificate5", "description5", 5.55, 2, createDate, lastUpdateDate, tags);
        giftCertificateService.create(giftCertificateToCreate);

        GiftCertificate giftCertificateToFindOpt = giftCertificateService.getById(5).getData();

        assertTrue(giftCertificateToFindOpt.getId() != null);
    }

    @Test
    void shouldNotCreateGift() throws GiftCertificateException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createDate = LocalDateTime.parse("2022-10-17 11:15:10", formatter);
        LocalDateTime lastUpdateDate = LocalDateTime.parse("2022-10-05 11:15:10", formatter);
        Long id = 1L;
        Tag tag = new Tag(id, "tagName1");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        GiftCertificate giftCertificateToCreate = new GiftCertificate(5L, "egiftCertificate5", "description5", 5.55, 2, createDate, lastUpdateDate, tags);
        giftCertificateService.create(giftCertificateToCreate);

        GiftCertificate giftCertificateToFindOpt = giftCertificateService.getById(6).getData();

        assertTrue(giftCertificateToFindOpt.getId() != null);
    }


    @Test
    void shouldDelete() throws NoSuchFieldException {
        giftCertificateService.delete(3);
        List<GiftCertificate> afterDelete = giftCertificatesRepo.findAll();
        assertEquals(4, afterDelete.size());
    }

    @Test
    void shouldNotDelete() throws NoSuchFieldException {
        List<GiftCertificate> afterDelete = giftCertificatesRepo.findAll();
        boolean d = afterDelete.size() == 4;
        assertFalse(d);
    }

    @Test
    void shouldUpdate() throws IncorrectParameterException, GiftCertificateException {
        int targetGift = 3;
        ResponseModel<GiftCertificate> certificate = giftCertificateService.getById(targetGift);
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate = certificate.getData();
        giftCertificate.setName("Update name gift " + targetGift);
        giftCertificate.setDescription("Update desc gift" + targetGift);
        giftCertificateService.update(3, giftCertificate);
        Optional<GiftCertificate> updatedCertificate = giftCertificatesRepo.findById(targetGift);
        GiftCertificate updatedGiftCertificate = new GiftCertificate();
        if (updatedCertificate.isPresent()) {
            updatedGiftCertificate = updatedCertificate.get();
        }
        String name = giftCertificate.getName();
        String updatedName = updatedGiftCertificate.getName();
        assertEquals(name, updatedName);
    }

}
