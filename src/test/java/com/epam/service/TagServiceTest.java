package com.epam.service;

import com.epam.esm.common.ResponseModel;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.GiftCertificateException;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.repository.impl.TagRepoImpl;
import com.epam.esm.service.impl.TagServiceImpl;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TagServiceTest {
    @InjectMocks
    private TagServiceImpl tagServiceImpl;
    @Mock
    private TagRepoImpl tagRepo;

    @BeforeEach
    public void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
        tagRepo = new TagRepoImpl(dataSource);
        tagServiceImpl = new TagServiceImpl(tagRepo);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("/drop.sql"));
    }

    @Test
    void shouldFindAllTag() {
        ResponseModel<List<Tag>> tags = tagServiceImpl.getAll();
        System.out.println(tags.getData().size());
        assertEquals(5, tags.getData().size());
    }

    @Test
    void shouldFindByIdTag() throws GiftCertificateException {
        ResponseModel<Tag> tagOptional = tagServiceImpl.getById(2);
        Tag tag = new Tag();
        if (tagOptional.getData().getId() != null) {
            tag = tagOptional.getData();
        }
        assertEquals("tagName2", tag.getName());
    }


    @Test
    void shouldCreateTag() throws GiftCertificateException, IncorrectParameterException {
        Long id = 6L;
        Tag tagToCreate = new Tag(id, "Tag 6");
        System.out.println(tagToCreate);
        tagServiceImpl.create(tagToCreate);
        ResponseModel<Tag> tagOptional = tagServiceImpl.getById(6);
        Tag tag = new Tag();
        if (tagOptional.getData().getId() != 0) {
            tag = tagOptional.getData();
        }
        assertEquals(tag, tagToCreate);
    }


    @Test
    void shouldFindByName() throws GiftCertificateException {
        ResponseModel<Tag> tagOptional = tagServiceImpl.findByName("tagName2");
        Tag tag = new Tag();
        if (tagOptional.getData().getId() != null) {
            tag = tagOptional.getData();
        }
        assertEquals("tagName2", tag.getName());
    }

    @Test
    void shouldDeleteTag() {
        tagRepo.delete(3);
        List<Tag> tag = tagRepo.findAll();
        assertEquals(4, tag.size());
    }


}