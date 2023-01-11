package com.epam.service;

import com.epam.esm.entity.Tag;
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
import java.util.Optional;

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
        List<Tag> tags = tagServiceImpl.getAll();
        System.out.println(tags.size());
        assertEquals(5, tags.size());
    }

    @Test
    void shouldFindByIdTag() {
        Optional<Tag> tagOptional = tagServiceImpl.findById(2);
        Tag tag = new Tag();
        if (tagOptional.isPresent()) {
            tag = tagOptional.get();
        }
        assertEquals("tagName2", tag.getName());
    }


    @Test
    void shouldCreateTag() {
        Long id = 6L;
        Tag tagToCreate = new Tag(id, "Tag 6");
        System.out.println(tagToCreate);
        tagServiceImpl.create(tagToCreate);
        Optional<Tag> tagOptional = tagServiceImpl.findById(6);
        Tag tag = new Tag();
        if (tagOptional.isPresent()) {
            tag = tagOptional.get();
        }
        assertEquals(tag, tagToCreate);
    }

    @Test
    void shouldDeleteTag() {
        tagServiceImpl.delete(4);
        List<Tag> tags = tagServiceImpl.getAll();
        assertEquals(4, tags.size());
    }

    @Test
    void ShouldFindByName() throws NullPointerException {
        Optional<Tag> tagOptional = tagServiceImpl.findByName("tagName2");
        Tag tag = new Tag();
        if (tagOptional.isPresent()) {
            tag = tagOptional.get();
        }
        assertEquals("tagName2", tag.getName());
    }
}