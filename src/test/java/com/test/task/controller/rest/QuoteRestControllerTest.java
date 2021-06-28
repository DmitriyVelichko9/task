package com.test.task.controller.rest;

import com.test.task.model.EnergyLevel;
import com.test.task.model.Quote;
import com.test.task.service.QuoteService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.test.task.TestUtils.convertObjectToJsonBytes;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = QuoteRestController.class)
class QuoteRestControllerTest {

    @MockBean
    private QuoteService quoteService;

    @Autowired
    private MockMvc mockMvc;


    public final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);

    private final Quote quote1 = new Quote(1L, "RU000A0JX0J2", 100.2, 101.9);
    private final Quote quote2 = new Quote(2L, "RU000A0JX0J2", 100.5, 101.9);
    private final Quote invalidQuoteIsin = new Quote(2L, "RU000A0", 100.5, 101.9);
    private final Quote invalidQuoteBidAndAsk = new Quote(2L, "RU000A0JX0J2", 102.5, 101.9);

    private final EnergyLevel eLvl1 = new EnergyLevel(1L, "RU000A0JX0J2", 100.2);
    private final EnergyLevel eLvl2 = new EnergyLevel(2L, "RU000A0JX0J3", 100.5);

    private final String isin = "RU000A0JX0J2";

    @Test
    void shouldGetAllQuotes() throws Exception {
        when(quoteService.getAllQuotes()).thenReturn(Arrays.asList(quote1, quote2));

        mockMvc.perform(get("/api/quote/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].isin", Matchers.is("RU000A0JX0J2")))
                .andExpect(jsonPath("$[0].bid", Matchers.is(100.2)))
                .andExpect(jsonPath("$[0].ask", Matchers.is(101.9)))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$[1].isin", Matchers.is("RU000A0JX0J2")))
                .andExpect(jsonPath("$[1].bid", Matchers.is(100.5)))
                .andExpect(jsonPath("$[1].ask", Matchers.is(101.9)));

        verify(quoteService, times(1)).getAllQuotes();
    }

    @Test
    void shouldReturnValidateExceptionWhenInvalidQuoteIsin() throws Exception {
        mockMvc.perform(post("/api/quote/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(invalidQuoteIsin)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnValidateExceptionWhenInvalidQuoteBidAndAsk() throws Exception {
        mockMvc.perform(post("/api/quote/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonBytes(invalidQuoteBidAndAsk)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetAllEnergyLevels() throws Exception {
        when(quoteService.getAllElvls()).thenReturn(Arrays.asList(eLvl1, eLvl2));

        mockMvc.perform(get("/api/quote/elvls"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].isin", Matchers.is("RU000A0JX0J2")))
                .andExpect(jsonPath("$[0].value", Matchers.is(100.2)))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(jsonPath("$[1].isin", Matchers.is("RU000A0JX0J3")))
                .andExpect(jsonPath("$[1].value", Matchers.is(100.5)));

        verify(quoteService, times(1)).getAllElvls();
    }

    @Test
    void shouldGetEnergyLevelByIsin() throws Exception {
        when(quoteService.getElvlByIsin(isin)).thenReturn(eLvl1);

        mockMvc.perform(get("/api/quote/elvls/{isin}", isin))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.isin", Matchers.is("RU000A0JX0J2")))
                .andExpect(jsonPath("$.value", Matchers.is(100.2)));

        verify(quoteService, times(1)).getElvlByIsin(isin);
    }
}