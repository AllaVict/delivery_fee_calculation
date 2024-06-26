package com.deliveryfeecalculation.controller;

import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import com.deliveryfeecalculation.exception.ResourceNotFoundException;
import com.deliveryfeecalculation.service.ExtraFeeService;
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

import static com.deliveryfeecalculation.constants.Constants.Endpoints.EXTRA_FEE_URL;
import static com.deliveryfeecalculation.factory.ExtraFeeFactory.*;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ExtraFeeController.class)
class ExtraFeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExtraFeeService extraFeeService;

    @InjectMocks
    private ExtraFeeController extraFeeController;

    private static final long EXTRA_FEE_ID = 101L;

    private List<ExtraFee> extraFeeList;
    private List<ExtraFeeDTO> extraFeeDTOList;
    private ExtraFee extraFee;
    private ExtraFeeDTO archiveExtraFeeDTO;
    private ExtraFeeDTO extraFeeDTO;

    @BeforeEach
    public void setUp() {
        extraFeeList = createExtraFeeList();
        extraFee = createExtraFeeWithData();
        archiveExtraFeeDTO = createExtraFeeDtoWithData();
        extraFeeDTO = createExtraFeeDtoWithData();
        extraFeeDTOList = createExtraFeeDTOList();
    }

    @Nested
    @DisplayName("When Create a ExtraFee")
    class CreateExtraFeeTests {
        @Test
        void testCreateExtraFee_ShouldReturnExtraFee() throws Exception {
            ExtraFeeDTO savedExtraFee = createExtraFeeDtoWithData();
            given(extraFeeService.createExtraFee(extraFeeDTO)).willReturn(savedExtraFee);

            mockMvc.perform(post(EXTRA_FEE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraFeeDTO)))
                    .andDo(print())
                    .andExpect(status().isCreated());

        }

        @Test
        void testCreateExtraFee_ShouldReturn400Status_WhenExtraFeeDtoIsNull() throws Exception {
            extraFeeDTO = null;
            mockMvc.perform(post(EXTRA_FEE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraFeeDTO)))
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(result -> result.getResponse().getContentAsString().equals("Please fill all fields"));

        }

        @Test
        void testCreateExtraFee_ShouldReturn404Status_WhenInvalidRequested() throws Exception {

            mockMvc.perform(post(EXTRA_FEE_URL + "/invalid")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraFeeDTO)))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());
        }

    }

    @Nested
    @DisplayName("When archive a ExtraFee")
    class ArchiveExtraFeeTests {
        @Test
        void testArchiveExtraFee_ShouldReturnExtraFeeWithArchiveStatus() throws Exception {
            given(extraFeeService.archiveExtraFee(EXTRA_FEE_ID)).willReturn(extraFeeDTO);

            mockMvc.perform(put(EXTRA_FEE_URL + "/" + EXTRA_FEE_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraFee)))
                    .andDo(print())
                    .andExpect(status().isOk());

        }

        @Test
        void testArchiveExtraFee_ShouldReturn404Status_WhenNonExistentId() throws Exception {
            Long nonExistentId = 999L;
            given(extraFeeService.archiveExtraFee(nonExistentId)).willThrow(
                    new ResourceNotFoundException("ExtraFee not found for id: " + nonExistentId));

            mockMvc.perform(put(EXTRA_FEE_URL + "/" + nonExistentId)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());

        }

        @Test
        void testArchiveExtraFee_ShouldReturn404Status_WhenInvalidData() throws Exception {
            given(extraFeeService.findExtraFeeById(null)).willThrow(
                    new ResourceNotFoundException("ExtraFee not found for id: " + null));

            mockMvc.perform(put(EXTRA_FEE_URL + "/")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());

        }

        @Test
        void testArchiveExtraFee_ShouldReturn405Status_WhenMissingIdInUrl() throws Exception {
            mockMvc.perform(put(EXTRA_FEE_URL + "/")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());

        }
    }

    @Nested
    @DisplayName("When Find ExtraFee By Id")
    class FindExtraFeeByIdTests {
        @Test
        void testFindExtraFeeById_ShouldReturnExtraFee() throws Exception {
            given(extraFeeService.findExtraFeeById(EXTRA_FEE_ID)).willReturn(extraFeeDTO);

            mockMvc.perform(get(EXTRA_FEE_URL + "/" + EXTRA_FEE_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraFeeDTO)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        void testFindExtraFeeById_ShouldReturn404Status_WhenNoExtraFeeFound() throws Exception {
            extraFeeDTO = null;
            given(extraFeeService.findExtraFeeById(EXTRA_FEE_ID)).willThrow(
                    new ResourceNotFoundException("ExtraFee not found for id: " + EXTRA_FEE_ID));

            mockMvc.perform(get(EXTRA_FEE_URL + "/" + EXTRA_FEE_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraFeeDTO)))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }

        @Test
        void testFindExtraFeeById_ShouldReturn404Status_WhenInvalidRequested() throws Exception {
            extraFeeDTO = null;
            given(extraFeeService.findExtraFeeById(EXTRA_FEE_ID)).willThrow(
                    new ResourceNotFoundException("ExtraFee not found for id: " + EXTRA_FEE_ID));

            mockMvc.perform(get(EXTRA_FEE_URL + "/" + EXTRA_FEE_ID + "/invalid")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraFeeDTO)))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }

    }

    @Nested
    @DisplayName("When Find All ExtraFees")
    class FindAllExtraFeesTests {

        @Test
        void testFindAllExtraFees_ShouldReturnAllExtraFees() throws Exception {
            given(extraFeeService.findAllExtraFees()).willReturn(extraFeeDTOList);

            mockMvc.perform(get(EXTRA_FEE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraFeeDTOList)))
                    .andDo(print())
                    .andExpect(status().isOk());

        }

        @Test
        void testFindAllExtraFees_ShouldReturn200Status_WhenReturnEmptyList() throws Exception {
            extraFeeDTOList = new ArrayList<>();
            given(extraFeeService.findAllExtraFees()).willReturn(extraFeeDTOList);

            mockMvc.perform(get(EXTRA_FEE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraFeeDTOList)))
                    .andDo(print())
                    .andExpect(status().isOk());

        }

        @Test
        void testFindAllExtraFees_ShouldReturn404Status_WhenInvalidRequested() throws Exception {

            mockMvc.perform(get("/invalid")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("When Delete a ExtraFee")
    class DeleteExtraFeeTests {
        @Test
        void testDeleteExtraFee_ShouldReturnExtraFee() throws Exception {
            doNothing().when(extraFeeService).deleteExtraFeeById(any(Long.class));

            mockMvc.perform(delete(EXTRA_FEE_URL + "/" + EXTRA_FEE_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraFee)))
                    .andExpect(content().string(containsString("The ExtraFee has deleted successfully")))
                    .andDo(print())
                    .andExpect(status().isOk());

        }

        @Test
        void testDeleteExtraFee_ShouldnReturn404Status_WhenExtraFeeDoesNotExist() throws Exception {
            doThrow(new ResourceNotFoundException("ExtraFee not found for id: " + null))
                    .when(extraFeeService).deleteExtraFeeById(any(Long.class));

            mockMvc.perform(delete(EXTRA_FEE_URL + "/" + EXTRA_FEE_ID)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        }

        @Test
        void testDeleteExtraFee_ShouldnReturn404Status_WhenInvalidRequested() throws Exception {
            mockMvc.perform(delete("/invalid")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(extraFee)))
                    .andDo(print())
                    .andExpect(status().isNotFound());

        }

    }

}