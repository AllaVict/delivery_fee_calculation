package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.converter.TypeConverter;
import com.deliveryfeecalculation.domain.dto.BaseFeeDTO;
import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.model.BaseFee;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import com.deliveryfeecalculation.exception.FeeCreationException;
import com.deliveryfeecalculation.exception.ResourceNotFoundException;
import com.deliveryfeecalculation.repository.BaseFeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.deliveryfeecalculation.domain.enums.Status.CURRENT;
import static com.deliveryfeecalculation.factory.BaseFeeFactory.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BaseFeeServiceImpl")
class BaseFeeServiceImplTest {


    @Mock
    private BaseFeeRepository baseFeeRepository;
    @Mock
    private TypeConverter<BaseFeeDTO, BaseFee> baseFeeDTOBaseFeeTypeConverter;
    @Mock
    private TypeConverter<BaseFee, BaseFeeDTO> baseFeeBaseFeeDTOTypeConverter;

    @InjectMocks
    private BaseFeeServiceImpl baseFeeService;

    private static final long BASE_FEE_ID = 101L;

    private List<BaseFee> baseFeeList;
    private List<BaseFeeDTO> baseFeeDTOList;
    private BaseFee baseFee;
    private BaseFeeDTO archiveBaseFeeDTO;
    private BaseFeeDTO baseFeeDTO;

    @BeforeEach
    public void setUp() {
        baseFeeService = new BaseFeeServiceImpl(baseFeeRepository,
                baseFeeDTOBaseFeeTypeConverter, baseFeeBaseFeeDTOTypeConverter);
        baseFeeList = createBaseFeeList();
        baseFee = createBaseFee();
        archiveBaseFeeDTO = createBaseFeeDto();
        baseFeeDTO = createBaseFeeDto();
        baseFeeDTOList = createBaseFeeDtoList();
    }


    @Nested
    @DisplayName("When Find BaseFee By Id")
    class FindBaseFeeByIdTests {
        @Test
        void testFindBaseFeeById_ShouldReturnBaseFee() {
            when(baseFeeRepository.findById(BASE_FEE_ID)).thenReturn(Optional.ofNullable(baseFee));
            when(baseFeeBaseFeeDTOTypeConverter.convert(baseFee)).thenReturn(baseFeeDTO);

            BaseFeeDTO result = baseFeeService.findBaseFeeById(BASE_FEE_ID);

            assertEquals(baseFeeDTO, result);
            verify(baseFeeRepository).findById(any(Long.class));
            assertNotNull(result);
            assertEquals(result, baseFeeDTO);
            Assertions.assertThat(result.getCity()).isEqualTo(baseFeeDTO.getCity());

        }

        @Test
        void testFindBaseFeeById_shouldThrowException() {

            assertThrows(ResourceNotFoundException.class,
                    () -> baseFeeService.findBaseFeeById(BASE_FEE_ID));
        }

    }

    @Nested
    @DisplayName("When Find All BaseFees")
    class FindAllBaseFeesTests {

        @Test
        void testFindAllBaseFees_shouldReturnAllBaseFees() {
            when(baseFeeRepository.findAll()).thenReturn(baseFeeList);
            when(baseFeeBaseFeeDTOTypeConverter.convert(baseFeeList)).thenReturn(baseFeeDTOList);

            List<BaseFeeDTO> foundExtraFeeDtoList = baseFeeService.findAllBaseFees();

            assertEquals(baseFeeDTOList.size(), foundExtraFeeDtoList.size());

        }

        @Test
        void testFindAllBaseFees_shouldThrowException() {
            baseFeeRepository.deleteAll();
            baseFeeDTOList = new ArrayList<>();
            baseFeeList = new ArrayList<>();
            when(baseFeeRepository.findAll()).thenReturn(baseFeeList);

            List<BaseFeeDTO> result = baseFeeService.findAllBaseFees();

            verify(baseFeeRepository).findAll();
            assertNotNull(result);
            assertEquals(result, baseFeeDTOList);
            Assertions.assertThat(result.size()).isEqualTo(baseFeeDTOList.size());

        }

    }

    @Nested
    @DisplayName("When Create a BaseFee")
    class CreateBaseFeeTests {
        @Test
        void testCreateBaseFee_ShouldReturnBaseFee() {
            when(baseFeeDTOBaseFeeTypeConverter.convert(baseFeeDTO)).thenReturn(baseFee);
            when(baseFeeRepository.saveAndFlush(baseFee)).thenReturn(baseFee);
            when(baseFeeBaseFeeDTOTypeConverter.convert(baseFee)).thenReturn(baseFeeDTO);

            BaseFeeDTO result = baseFeeService.createBaseFee(baseFeeDTO);

            assertEquals(baseFeeDTO, result);
            assertThat(result.getCity()).isEqualTo(baseFeeDTO.getCity());
            assertThat(result.getStatus()).isEqualTo(CURRENT);
            verify(baseFeeRepository).saveAndFlush(any(BaseFee.class));
            assertNotNull(result);
        }

        @Test
        void testCreateBaseFee_shouldThrowException() {
            baseFeeDTO = null;
            assertThrows(FeeCreationException.class,
                    () -> baseFeeService.createBaseFee(baseFeeDTO));
        }
    }


    @Nested
    @DisplayName("When archive a BaseFee")
    class ArchiveBaseFeeTests {
        @Test
        void testArchiveBaseFee_shouldReturnBaseFeeWithArchiveStatus() {
            when(baseFeeRepository.findById(BASE_FEE_ID)).thenReturn(Optional.ofNullable(baseFee));
            archiveBaseFeeDTO.setStatus(Status.ARCHIVE);
            when(baseFeeRepository.saveAndFlush(baseFee)).thenReturn(baseFee);
            when(baseFeeBaseFeeDTOTypeConverter.convert(baseFee)).thenReturn(archiveBaseFeeDTO);

            BaseFeeDTO result = baseFeeService.archiveBaseFee(BASE_FEE_ID);

            assertEquals(archiveBaseFeeDTO, result);
            assertThat(result.getCity()).isEqualTo(baseFeeDTO.getCity());
            assertNotNull(result);
            verify(baseFeeRepository).saveAndFlush(any(BaseFee.class));

        }

        @Test
        void testArchiveBaseFee_shouldThrowException() {
            assertThrows(ResourceNotFoundException.class,
                    () -> baseFeeService.archiveBaseFee(BASE_FEE_ID));

        }

    }

    @Nested
    @DisplayName("When Delete a BaseFee")
    class DeleteExtraFeeTests {
        @Test
        void testDeleteBaseFee_shouldReturnExtraFee() {
            when(baseFeeRepository.findById(BASE_FEE_ID)).thenReturn(Optional.of(baseFee));
            baseFeeService.deleteBaseFeeById(BASE_FEE_ID);
            verify(baseFeeRepository, times(1)).delete(baseFee);

        }

        @Test
        void testDeleteBaseFee_shouldThrowException() {

            assertThrows(ResourceNotFoundException.class,
                    () -> baseFeeService.deleteBaseFeeById(BASE_FEE_ID));
            verify(baseFeeRepository, times(0)).delete(baseFee);

        }

    }

}