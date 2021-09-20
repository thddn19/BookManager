package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.Author;
import com.fastcampus.jpa.bookmanager.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public void putAuthor() {
        Author author = new Author();
        author.setName("songwoo");

        authorRepository.save(author);

        throw new RuntimeException("오류 발생 ! transaction은 어떻게 될까 ?"); // 예외로 인한 rollback 발생
    }
}
