package com.deliveryfeecalculation.controller;

import com.deliveryfeecalculation.domain.model.ExtraFee;
import com.deliveryfeecalculation.repository.ExtraFeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.deliveryfeecalculation.constants.Constants.Endpoints.EXTRA_FEE_URL;
import static com.deliveryfeecalculation.factory.ExtraFeeFactory.createExtraFeeList;
import static com.deliveryfeecalculation.factory.ExtraFeeFactory.createExtraFeeWithData;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExtraFeeController.class)
class ExtraFeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private ExtraFeeRepository extraFeeRepository;

    @InjectMocks
    private ExtraFeeController extraFeeController;

    private static final long EXTRA_FEE_ID = 101L;

    private List<ExtraFee> extraFeeList;
    @MockBean
    private ExtraFee extraFee;

    @BeforeEach
    public void setUp() {
        extraFeeList = createExtraFeeList();
        extraFee = createExtraFeeWithData();
    }

    @Nested
    @DisplayName("When Find ExtraFee By Id")
    class FindAdvertByIdTests {
             @Test
            void testFindExtraFeeById_ShouldReturnExtraFee() throws Exception {
                given(extraFeeRepository.findById(EXTRA_FEE_ID)).willReturn(Optional.ofNullable(extraFee));
                mockMvc.perform(get(EXTRA_FEE_URL+"/"+EXTRA_FEE_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(extraFee)))
                        .andDo(print())
                        .andExpect(status().isOk());
            }

            @Test
            void testFindAdvertById_ShouldNoAdvertFound() throws Exception {
                extraFee =null;
                given(extraFeeRepository.findById(EXTRA_FEE_ID)).willReturn(Optional.ofNullable(extraFee));

                mockMvc.perform(get(EXTRA_FEE_URL+"/"+EXTRA_FEE_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(extraFee)))
                        .andDo(print())
                        .andExpect(status().isNotFound());
            }

    }

    @Nested
    @DisplayName("When Find All ExtraFees")
    class FindAllAdvertsTests {

            @Test
            void testFindAllExtraFees_shouldReturnAllExtraFees() throws Exception{
                given(extraFeeRepository.findAll()).willReturn(extraFeeList);
                mockMvc.perform(get(EXTRA_FEE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(extraFeeList)))
                        .andDo(print())
                        .andExpect(status().isOk());
            }

            @Test
            void testFindAllAdverts_shouldThrowException() throws Exception{
                extraFeeList =new ArrayList<>();
                given(extraFeeRepository.findAll()).willReturn(extraFeeList);
                mockMvc.perform(get(EXTRA_FEE_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(extraFeeList)))
                        .andDo(print())
                        .andExpect(status().isNotFound());
            }
    }

    @Nested
    @DisplayName("When Create a ExtraFee")
    class CreateExtraFeeTests {
        @Test
        void testCreateExtraFee_ShouldReturnExtraFee() throws Exception {
            ExtraFee savedExtraFee = createExtraFeeWithData();
            given(extraFeeRepository.save(extraFee)).willReturn(savedExtraFee);
            mockMvc.perform(post(EXTRA_FEE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraFee)))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        @Test
        void testCreateExtraFee_InvalidData() throws Exception {
            extraFee = null;
            mockMvc.perform(post(EXTRA_FEE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraFee)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

    }

    @Nested
    @DisplayName("When archive a ExtraFee")
    class EditAdvertTests {
            @Test
            void testArchiveExtraFee_shouldReturnExtraFeeWithArchiveStatus() throws Exception {
                given(extraFeeRepository.findById(EXTRA_FEE_ID)).willReturn(Optional.ofNullable(extraFee));
                given(extraFeeRepository.save(extraFee)).willReturn(extraFee);
                        mockMvc.perform(put( EXTRA_FEE_URL +"/"+EXTRA_FEE_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(extraFee)))
                        .andDo(print())
                        .andExpect(status().isOk());
            }

            @Test
            void testArchiveExtraFee_InvalidData() throws Exception{
                extraFee =null;
                mockMvc.perform(put( EXTRA_FEE_URL +"/"+EXTRA_FEE_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(extraFee)))
                        .andDo(print())
                        .andExpect(status().isNotFound());
            }
        }

    @Nested
    @DisplayName("When Delete a ExtraFee")
    class DeleteExtraFeeTests {
        @Test
        void testDeleteExtraFee_shouldReturnExtraFee() throws Exception {
            doNothing().when(extraFeeRepository).deleteById(any(Long.class));

            mockMvc.perform(delete( EXTRA_FEE_URL +"/"+EXTRA_FEE_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraFee)))
                    .andExpect(content().string(containsString("The ExtraFee has deleted successfully")))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void testDeleteAdvert_statusCode404WhenInvalidRequested() throws Exception{
            mockMvc.perform(delete("/invalid")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraFee)))
                    .andDo(print())
                    .andExpect(status().isNotFound());

        }

    }
}