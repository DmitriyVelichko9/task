package com.test.task.controller.rest;

import com.test.task.model.EnergyLevel;
import com.test.task.model.Quote;
import com.test.task.service.QuoteService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/quote")
public class QuoteRestController {

    private QuoteService quoteService;

    public QuoteRestController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping
    public List<Quote> getAllQuotes() {
        return quoteService.getAllQuotes();
    }

    @PostMapping
    public Quote addNewQuote(@RequestBody @Valid Quote quote) {
        quoteService.saveQuoteAndELvl(quote);
        return quote;
    }

    @GetMapping("/elvls")
    public List<EnergyLevel> getAllElvls() {
        return quoteService.getAllElvls();
    }

    @GetMapping("/elvls/{isin}")
    public EnergyLevel getElvlByIsin(@PathVariable String isin) {
        return quoteService.getElvlByIsin(isin);
    }
}
