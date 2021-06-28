package com.test.task.repo;

import com.test.task.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepo extends JpaRepository<Quote, Long> {
}
