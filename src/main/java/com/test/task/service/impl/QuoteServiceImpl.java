package com.test.task.service.impl;

import com.test.task.model.EnergyLevel;
import com.test.task.model.Quote;
import com.test.task.repo.EnergyLevelRepo;
import com.test.task.repo.QuoteRepo;
import com.test.task.service.QuoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuoteServiceImpl implements QuoteService {

    private QuoteRepo quoteRepo;
    private EnergyLevelRepo energyLevelRepo;

    public QuoteServiceImpl(QuoteRepo quoteRepo, EnergyLevelRepo energyLevelRepo) {
        this.quoteRepo = quoteRepo;
        this.energyLevelRepo = energyLevelRepo;
    }

    @Override
    @Transactional
    public List<Quote> getAllQuotes() {
        return quoteRepo.findAll();
    }

    @Override
    @Transactional
    public Quote saveQuoteAndELvl(Quote quote) {
        energyLevelRepo.save(calculateELvl(quote));
        quoteRepo.save(quote);
        return quote;
    }

    @Override
    @Transactional
    public EnergyLevel getElvlByIsin(String isin) {
        return energyLevelRepo.findEnergyLevelByIsin(isin);
    }

    @Override
    @Transactional
    public List<EnergyLevel> getAllElvls() {
        return energyLevelRepo.findAll();
    }

    private EnergyLevel calculateELvl(Quote quote) {
        EnergyLevel energyLevel = energyLevelRepo.findEnergyLevelByIsin(quote.getIsin());
        if (energyLevel == null) {
            energyLevel = new EnergyLevel();
            energyLevel.setIsin(quote.getIsin());
            if (quote.getBid() == null) {
                energyLevel.setValue(quote.getAsk());
                return energyLevel;
            } else {
                energyLevel.setValue(quote.getBid());
                return energyLevel;
            }
        }
        if (quote.getBid() > energyLevel.getValue()) {
            energyLevel.setValue(quote.getBid());
        }
        if (quote.getAsk() < energyLevel.getValue()) {
            energyLevel.setValue(quote.getAsk());
        }
        return energyLevel;
    }
}

