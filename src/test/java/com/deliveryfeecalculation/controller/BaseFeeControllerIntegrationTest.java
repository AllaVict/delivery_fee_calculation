package com.deliveryfeecalculation.controller;

import com.deliveryfeecalculation.domain.dto.BaseFeeDTO;
import com.deliveryfeecalculation.domain.model.BaseFee;
import com.deliveryfeecalculation.exception.ResourceNotFoundException;
import com.deliveryfeecalculation.service.BaseFeeService;
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

import static com.deliveryfeecalculation.constants.Constants.Endpoints.BASE_FEE_URL;
import static com.deliveryfeecalculation.factory.BaseFeeFactory.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BaseFeeController.class)
class BaseFeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BaseFeeService baseFeeService;

    @InjectMocks
    private BaseFeeController baseFeeController;

    private static final long BASE_FEE_ID = 101L;

    private List<BaseFee> baseFeeList;
    private List<BaseFeeDTO> baseFeeDTOList;
    private BaseFee baseFee;
    private BaseFeeDTO archiveBaseFeeDTO;
    private BaseFeeDTO baseFeeDTO;

    @BeforeEach
    public void setUp() {
        baseFeeController = new BaseFeeController(baseFeeService);
        baseFeeList = createBaseFeeList();
        baseFee = createBaseFee();
        archiveBaseFeeDTO = createBaseFeeDto();
        baseFeeDTO = createBaseFeeDto();
        baseFeeDTOList = createBaseFeeDtoList();
    }

    @Nested
    @DisplayName("When Find BaseFee By Id")
    class FindBaseByIdTests {
        @Test
        void testFindBaseFeeById_ShouldReturnBaseFee() throws Exception {
            given(baseFeeService.findBaseFeeById(BASE_FEE_ID)).willReturn(baseFeeDTO);

            mockMvc.perform(get(BASE_FEE_URL + "/" + BASE_FEE_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(baseFeeDTO)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void testFindBaseFeeById_ShouldNoBaseFeeFound() throws Exception {
            baseFeeDTO = null;
            given(baseFeeService.findBaseFeeById(BASE_FEE_ID)).willThrow(
                    new ResourceNotFoundException("BaseFee not found for id: " + BASE_FEE_ID));

            mockMvc.perform(get(BASE_FEE_URL + "/" + BASE_FEE_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(baseFeeDTO)))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }

    }

    @Nested
    @DisplayName("When Find All BaseFees")
    class FindAllBaseFeesTests {

        @Test
        void testFindAllBaseFees_shouldReturnAllBaseFees() throws Exception {
            given(baseFeeService.findAllBaseFees()).willReturn(baseFeeDTOList);

            mockMvc.perform(get(BASE_FEE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(baseFeeDTOList)))
                    .andDo(print())
                    .andExpect(status().isOk());

        }

        @Test
        void testFindAllBaseFees_shouldThrowException() throws Exception {
            baseFeeDTOList = new ArrayList<>();
            given(baseFeeService.findAllBaseFees()).willReturn(baseFeeDTOList);

            mockMvc.perform(get(BASE_FEE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(baseFeeDTOList)))
                    .andDo(print())
                    .andExpect(status().isOk());

        }
    }

    @Nested
    @DisplayName("When Create a BaseFee")
    class CreateBaseFeeTests {
        @Test
        void testCreateBaseFee_ShouldReturnBaseFee() throws Exception {
            BaseFeeDTO savedBaseFee = createBaseFeeDto();
            given(baseFeeService.createBaseFee(baseFeeDTO)).willReturn(savedBaseFee);

            mockMvc.perform(post(BASE_FEE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(baseFeeDTO)))
                    .andDo(print())
                    .andExpect(status().isCreated());

        }

        @Test
        void testCreateBaseFee_InvalidData() throws Exception {
            baseFeeDTO = null;
            mockMvc.perform(post(BASE_FEE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(baseFeeDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());

        }

    }

    @Nested
    @DisplayName("When archive a BaseFee")
    class ArchiveBaseFeeTests {
        @Test
        void testArchiveBaseFee_shouldReturnExtraFeeWithArchiveStatus() throws Exception {
            given(baseFeeService.archiveBaseFee(BASE_FEE_ID)).willReturn(baseFeeDTO);

            mockMvc.perform(put(BASE_FEE_URL + "/" + BASE_FEE_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(baseFee)))
                    .andDo(print())
                    .andExpect(status().isOk());

        }

        @Test
        void testArchiveBaseFee_NonExistentId() throws Exception {
            Long nonExistentId = 999L;
            given(baseFeeService.archiveBaseFee(nonExistentId)).willThrow(
                    new ResourceNotFoundException("BaseFee not found for id: " + nonExistentId));

            mockMvc.perform(put(BASE_FEE_URL + "/" + nonExistentId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());

        }

        @Test
        void testArchiveBaseFee_InvalidData() throws Exception {
            given(baseFeeService.findBaseFeeById(null)).willThrow(
                    new ResourceNotFoundException("BaseFee not found for id: " + null));

            mockMvc.perform(put(BASE_FEE_URL + "/")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());

        }

        @Test
        void testArchiveBaseFee_MissingIdInUrl() throws Exception {
            mockMvc.perform(put(BASE_FEE_URL + "/")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());

        }
    }

    @Nested
    @DisplayName("When Delete a BaseFee")
    class DeleteBaseFeeTests {
        @Test
        void testDeleteBaseFee_shouldReturnExtraFee() throws Exception {
            doNothing().when(baseFeeService).deleteBaseFeeById(any(Long.class));

            mockMvc.perform(delete(BASE_FEE_URL + "/" + BASE_FEE_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(baseFee)))
                    .andExpect(content().string(containsString("The BaseFee has deleted successfully")))
                    .andDo(print())
                    .andExpect(status().isOk());

        }

        @Test
        void testDeleteBaseFee_statusCode404WhenInvalidRequested() throws Exception {
            mockMvc.perform(delete("/invalid")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(baseFee)))
                    .andDo(print())
                    .andExpect(status().isNotFound());

        }

    }

}