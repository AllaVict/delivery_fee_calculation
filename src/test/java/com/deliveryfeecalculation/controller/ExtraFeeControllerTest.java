package com.deliveryfeecalculation.controller;

import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import com.deliveryfeecalculation.exception.ResourceNotFoundException;
import com.deliveryfeecalculation.service.ExtraFeeService;
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

import static com.deliveryfeecalculation.factory.ExtraFeeFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ExtraFeeController")
class ExtraFeeControllerTest {

    @Mock
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
        extraFeeController= new ExtraFeeController(extraFeeService);
        extraFeeList = createExtraFeeList();
        extraFee = createExtraFeeWithData();
        archiveExtraFeeDTO =createExtraFeeDtoWithData();
        extraFeeDTO= createExtraFeeDtoWithData();
        extraFeeDTOList= createExtraFeeDTOList();
    }
    @Nested
    @DisplayName("When Find ExtraFee By Id")
    class FindAdvertByIdTests {
        @Test
        void testFindExtraFeeById_ShouldReturnExtraFee() {
            when(extraFeeService.findExtraFeeById(EXTRA_FEE_ID)).thenReturn(extraFeeDTO);

            ResponseEntity<?> responseEntity = extraFeeController.findExtraFeeById(EXTRA_FEE_ID);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(extraFeeDTO, responseEntity.getBody());
        }

        @Test
        void testFindExtraFeeById_shouldThrowException() {
            when(extraFeeService.findExtraFeeById(EXTRA_FEE_ID))
                    .thenThrow(new ResourceNotFoundException("ExtraFee not found for id: " + EXTRA_FEE_ID));

            assertThrows(ResourceNotFoundException.class,
                    () ->extraFeeController.findExtraFeeById(EXTRA_FEE_ID));
        }
    }

    @Nested
    @DisplayName("When Find All ExtraFees")
    class FindAllAdvertsTests {

        @Test
        void testFindAllExtraFees_shouldReturnAllExtraFees() {
            when(extraFeeService.findAllExtraFees()).thenReturn(extraFeeDTOList);

            ResponseEntity<?> responseEntity = extraFeeController.getAllExtraFees();

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(extraFeeDTOList, responseEntity.getBody());
        }

        @Test
        void testFindAllExtraFees_shouldThrowException() {
            extraFeeDTOList =new ArrayList<>();
            when(extraFeeService.findAllExtraFees()).thenReturn(extraFeeDTOList);

            ResponseEntity<?> responseEntity = extraFeeController.getAllExtraFees();

            assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        }

    }


    @Nested
    @DisplayName("When Create a ExtraFee")
    class CreateExtraFeeTests {
        @Test
        void testCreateExtraFee_ShouldReturnExtraFee() {
            when(extraFeeService.createExtraFee(extraFeeDTO)).thenReturn(extraFeeDTO);

            ResponseEntity<?> responseEntity = extraFeeController.createExtraFee(extraFeeDTO);

            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
            assertEquals(extraFeeDTO, responseEntity.getBody());
        }

        @Test
        void testCreateExtraFee_InvalidData() {
            extraFee = new ExtraFee();
            extraFeeDTO= new ExtraFeeDTO();
            ResponseEntity<?> responseEntity = extraFeeController.createExtraFee(extraFeeDTO);

            assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        }

    }
    @Nested
    @DisplayName("When archive a ExtraFee")
    class  EditAdvertTests {
        @Test
        void testArchiveExtraFee_shouldReturnExtraFeeWithArchiveStatus() {
            when(extraFeeService.archiveExtraFee(EXTRA_FEE_ID)).thenReturn(archiveExtraFeeDTO);

            ResponseEntity<?> responseEntity = extraFeeController.archiveExtraFee(EXTRA_FEE_ID);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(archiveExtraFeeDTO, responseEntity.getBody());
        }

    }

    @Nested
    @DisplayName("When Delete a ExtraFee")
    class DeleteExtraFeeTests {
        @Test
        void testDeleteExtraFee_shouldReturnExtraFee() {
            doNothing().when(extraFeeService).deleteExtraFeeById(any(Long.class));

            ResponseEntity<?> responseEntity = extraFeeController.deleteExtraFeeById(EXTRA_FEE_ID);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals("The ExtraFee has deleted successfully", responseEntity.getBody());
        }

    }

}