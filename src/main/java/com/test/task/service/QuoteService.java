package com.test.task.service;

import com.test.task.model.EnergyLevel;
import com.test.task.model.Quote;

import java.util.List;

public interface QuoteService {
    List<Quote> getAllQuotes();

    Quote saveQuoteAndELvl(Quote quote);

    EnergyLevel getElvlByIsin(String isin);

    List<EnergyLevel> getAllElvls();
}
