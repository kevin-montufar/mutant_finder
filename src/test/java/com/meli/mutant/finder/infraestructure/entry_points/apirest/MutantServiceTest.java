package com.meli.mutant.finder.infraestructure.entry_points.apirest;

import com.meli.mutant.finder.domain.model.person.Person;
import com.meli.mutant.finder.domain.model.person.PersonRepository;
import com.meli.mutant.finder.domain.usecase.MutantFinderUseCase;
import com.meli.mutant.finder.domain.usecase.model.Stat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MutantService.class)
@AutoConfigureMockMvc
@EnableWebMvc
class MutantServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    PersonRepository personRepository;

    @MockBean
    MutantFinderUseCase mutantFinderUseCase;

    private Stat mockStat;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockStat = Stat.builder().mutants(4).humans(2).ratio(2).build();
    }

    @Test
    void getStatsTest() throws Exception {
        final String expectedResponse = "{\"ratio\":2.0,\"count_mutant_dna\":4,\"count_human_dna\":2}";
        Mockito.when(personRepository.countPeopleByIsMutant(true)).thenReturn(Long.valueOf(4));
        Mockito.when(personRepository.countPeopleByIsMutant(false)).thenReturn(Long.valueOf(2));
        when(mutantFinderUseCase.getStats()).thenReturn(mockStat);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/stats")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(expectedResponse, response.getContentAsString());
    }

    @Test
    void isMutantBadRequestTest() throws Exception  {
        Person person = Person.builder().dna(Arrays.asList("AAAA", "AAAA", "AAAA", "AAAA")).build();
        when(mutantFinderUseCase.isMutant(person.getDna())).thenReturn(true);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(person.toString());

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

}