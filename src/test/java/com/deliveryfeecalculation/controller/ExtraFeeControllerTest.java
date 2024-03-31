package com.deliveryfeecalculation.controller;

import com.deliveryfeecalculation.converter.TypeConverter;
import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import com.deliveryfeecalculation.exception.ResourceNotFoundException;
import com.deliveryfeecalculation.repository.ExtraFeeRepository;
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
import java.util.Optional;

import static com.deliveryfeecalculation.factory.ExtraFeeFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ExtraFeeController")
class ExtraFeeControllerTest {

    @Mock
    private ExtraFeeRepository extraFeeRepository;
    @Mock
    private TypeConverter<ExtraFeeDTO, ExtraFee> extraFeeDTOExtraFeeTypeConverter;
    @Mock
    private TypeConverter<ExtraFee,ExtraFeeDTO> extraFeeExtraFeeDTOTypeConverter;
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
        extraFeeController= new ExtraFeeController(extraFeeRepository,
                extraFeeDTOExtraFeeTypeConverter,extraFeeExtraFeeDTOTypeConverter);
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
            when(extraFeeRepository.findById(EXTRA_FEE_ID)).thenReturn(Optional.ofNullable(extraFee));
            when(extraFeeExtraFeeDTOTypeConverter.convert(extraFee)).thenReturn(extraFeeDTO);
            ResponseEntity<?> responseEntity = extraFeeController.findExtraFeeById(EXTRA_FEE_ID);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(extraFeeDTO, responseEntity.getBody());
        }

        @Test
        void testFindExtraFeeById_shouldThrowException() {

            assertThrows(ResourceNotFoundException.class,
                    () ->extraFeeController.findExtraFeeById(EXTRA_FEE_ID));
        }
    }

    @Nested
    @DisplayName("When Find All ExtraFees")
    class FindAllAdvertsTests {

        @Test
        void testFindAllExtraFees_shouldReturnAllExtraFees() {
            when(extraFeeRepository.findAll()).thenReturn(extraFeeList);
            when(extraFeeExtraFeeDTOTypeConverter.convert(extraFeeList)).thenReturn(extraFeeDTOList);

            ResponseEntity<?> responseEntity = extraFeeController.getAllExtraFees();

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals(extraFeeDTOList, responseEntity.getBody());
        }

        @Test
        void testFindAllExtraFees_shouldThrowException() {
            extraFeeRepository.deleteAll();
            extraFeeList =new ArrayList<>();
            when(extraFeeRepository.findAll()).thenReturn(extraFeeList);

            ResponseEntity<?> responseEntity = extraFeeController.getAllExtraFees();

            assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        }

    }


    @Nested
    @DisplayName("When Create a ExtraFee")
    class CreateExtraFeeTests {
        @Test
        void testCreateExtraFee_ShouldReturnExtraFee() {
            when(extraFeeDTOExtraFeeTypeConverter.convert(extraFeeDTO)).thenReturn(extraFee);
            when(extraFeeRepository.save(extraFee)).thenReturn(extraFee);

            ResponseEntity<?> responseEntity = extraFeeController.createExtraFee(extraFeeDTO);

            assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
            assertEquals(extraFee, responseEntity.getBody());
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
            when(extraFeeRepository.findById(EXTRA_FEE_ID)).thenReturn(Optional.ofNullable(extraFee));
            archiveExtraFeeDTO.setStatus(Status.ARCHIVE);
            when(extraFeeRepository.save(extraFee)).thenReturn(extraFee);
            when(extraFeeExtraFeeDTOTypeConverter.convert(extraFee)).thenReturn(archiveExtraFeeDTO);

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
            doNothing().when(extraFeeRepository).deleteById(any(Long.class));

            ResponseEntity<?> responseEntity = extraFeeController.deleteExtraFee(EXTRA_FEE_ID);

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            assertEquals("The ExtraFee has deleted successfully", responseEntity.getBody());
        }

    }

}