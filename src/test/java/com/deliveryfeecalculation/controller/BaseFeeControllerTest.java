package com.deliveryfeecalculation.controller;

import com.deliveryfeecalculation.domain.dto.BaseFeeDTO;
import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.model.BaseFee;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import com.deliveryfeecalculation.exception.ResourceNotFoundException;
import com.deliveryfeecalculation.service.BaseFeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.deliveryfeecalculation.factory.BaseFeeFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BaseFeeController")
class BaseFeeControllerTest {

    @Mock
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
    @DisplayName("When Create a BaseFee")
    class CreateBaseFeeTests {
        @Test
        void testCreateBaseFee_ShouldReturnBaseFee() {
            when(baseFeeService.createBaseFee(baseFeeDTO)).thenReturn(baseFeeDTO);

            ResponseEntity<?> responseEntity = baseFeeController.createBaseFee(baseFeeDTO);

            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
            assertEquals(baseFeeDTO, responseEntity.getBody());
        }

        @Test
        void testCreateBaseFee_ShouldReturn400Status_WhenInvalidRequest() {
            baseFee = new BaseFee();
            baseFeeDTO = new BaseFeeDTO();
            ResponseEntity<?> responseEntity = baseFeeController.createBaseFee(baseFeeDTO);

            assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        }

    }

    @Nested
    @DisplayName("When archive a BaseFee")
    class ArchiveBaseFeeTests {
        @Test
        void testArchiveBaseFee_ShouldReturnBaseFeeWithArchiveStatus() {
            when(baseFeeService.archiveBaseFee(BASE_FEE_ID)).thenReturn(archiveBaseFeeDTO);

            ResponseEntity<?> responseEntity = baseFeeController.archiveBaseFee(BASE_FEE_ID);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(archiveBaseFeeDTO, responseEntity.getBody());
        }

        @Test
        void testArchiveBaseFee_ShouldnReturn400Status_WhenInvalidRequest() {

            ResponseEntity<?> responseEntity = baseFeeController.archiveBaseFee(null);

            assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        }

    }

    @Nested
    @DisplayName("When Find BaseFee By Id")
    class FindBaseFeeByIdTests {
        @Test
        void testFindBaseFeeById_ShouldReturnBaseFee() {
            when(baseFeeService.findBaseFeeById(BASE_FEE_ID)).thenReturn(baseFeeDTO);

            ResponseEntity<?> responseEntity = baseFeeController.findBaseFeeById(BASE_FEE_ID);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(baseFeeDTO, responseEntity.getBody());

        }

        @Test
        void testFindBaseFeeById_ShouldReturn404Status_WhenBaseFeeDoesNotExist() {
            when(baseFeeService.findBaseFeeById(BASE_FEE_ID))
                    .thenThrow(new ResourceNotFoundException("BaseFee not found for id: " + BASE_FEE_ID));

            ResponseEntity<?> responseEntity = baseFeeController.findBaseFeeById(BASE_FEE_ID);

            assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        }

    }

    @Nested
    @DisplayName("When Find All BaseFees")
    class FindAllBaseFeesTests {

        @Test
        void testFindAllBaseFees_ShouldReturnAllBaseFees() {
            when(baseFeeService.findAllBaseFees()).thenReturn(baseFeeDTOList);

            ResponseEntity<?> responseEntity = baseFeeController.findAllBaseFees();

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(baseFeeDTOList, responseEntity.getBody());

        }

        @Test
        void testFindAllBaseFees_ShouldReturn200Status_WhenReturnEmptyList() {
            baseFeeDTOList = new ArrayList<>();
            when(baseFeeService.findAllBaseFees()).thenReturn(baseFeeDTOList);

            ResponseEntity<?> responseEntity = baseFeeController.findAllBaseFees();

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(baseFeeDTOList, responseEntity.getBody());
        }

    }

    @Nested
    @DisplayName("When Delete a BaseFee")
    class DeleteBaseFeeTests {
        @Test
        void testDeleteBaseFee_ShouldReturnBaseFee() {
            doNothing().when(baseFeeService).deleteBaseFeeById(any(Long.class));

            ResponseEntity<?> responseEntity = baseFeeController.deleteBaseFeeById(BASE_FEE_ID);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals("The BaseFee has deleted successfully", responseEntity.getBody());
        }

        @Test
        void testDeleteBaseFee_ShouldnReturn404Status_WhenBaseFeeDoesNotExist() {
            doThrow(new ResourceNotFoundException("BaseFee not found for id: " + BASE_FEE_ID))
                    .when(baseFeeService).deleteBaseFeeById(any(Long.class));

            ResponseEntity<?> responseEntity = baseFeeController.deleteBaseFeeById(BASE_FEE_ID);

            assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        }

    }

}