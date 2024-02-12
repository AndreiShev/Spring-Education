package com.example.contacts.repository;

import com.example.contacts.model.Contact;
import com.example.contacts.exception.ContactNotFoundException;
import com.example.contacts.repository.mapper.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DatabaseContactRepository implements ContactRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<Contact> findAll() {
        log.debug("Calling DatabaseContactRepository -> findAll");

        String sql = "SELECT * FROM contact";

        return jdbcTemplate.query(sql, new TaskRowMapper());
    }

    @Override
    public Optional<Contact> findById(Long id) {
        log.debug("Calling DatabaseContactRepository -> findById with ID: {}", id);
        String sql = "SELECT * from contact WHERE id = ?";

        Contact contact = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[] {id}),
                        new RowMapperResultSetExtractor<>(new TaskRowMapper(), 1)
                )
        );


        return Optional.ofNullable(contact);
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Calling DatabaseContactRepository -> save with Task: {}", contact);
        contact.setId(System.currentTimeMillis());
        String sql = "INSERT INTO contact (first_name, last_name, email, phone, id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone(), contact.getId());

        return contact;
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Calling DatabaseContactRepository -> update with Task: {}", contact);

        Contact existedContact = findById(contact.getId()).orElse(null);
        if(existedContact != null) {
            String sql = "UPDATE contact SET first_name = ?, last_name = ?, email = ?, phone = ? WHERE id = ?";
            jdbcTemplate.update(sql, contact.getFirstName(), contact.getLastName(), contact.getEmail(), contact.getPhone(), contact.getId());
            return contact;
        }

        log.warn("Task with ID {} not found!", contact.getId());
        throw new ContactNotFoundException("Contact for update not found! ID: " + contact.getId());
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Calling DatabaseContactRepository -> delete with id: {}", id);

        String sql = "DELETE FROM contact WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

    @Override
    public void batchInsert(List<Contact> contactList) {
        log.debug("Calling DatabaseContactRepository -> batchInsert");

        String sql = "INSERT INTO contact (first_name, last_name, email, phone, id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Contact contact = contactList.get(i);
                ps.setString(1, contact.getFirstName());
                ps.setString(2, contact.getLastName());
                ps.setString(3, contact.getEmail());
                ps.setString(4, contact.getPhone());
                ps.setLong(5, contact.getId());
            }

            @Override
            public int getBatchSize() {
                return contactList.size();
            }
        });
    }
}
