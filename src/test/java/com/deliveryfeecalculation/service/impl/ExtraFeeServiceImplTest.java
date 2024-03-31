package com.deliveryfeecalculation.service.impl;

import com.deliveryfeecalculation.converter.TypeConverter;
import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.enums.Status;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import com.deliveryfeecalculation.exception.ResourceNotFoundException;
import com.deliveryfeecalculation.repository.ExtraFeeRepository;
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

import static com.deliveryfeecalculation.factory.ExtraFeeFactory.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ExtraFeeServiceImpl")
class ExtraFeeServiceImplTest {

    @Mock
    private ExtraFeeRepository extraFeeRepository;
    @Mock
    private TypeConverter<ExtraFeeDTO, ExtraFee> extraFeeDTOExtraFeeTypeConverter;
    @Mock
    private TypeConverter<ExtraFee, ExtraFeeDTO> extraFeeExtraFeeDTOTypeConverter;
    @InjectMocks
    private ExtraFeeServiceImpl extraFeeService;

    private static final long EXTRA_FEE_ID = 101L;

    private List<ExtraFee> extraFeeList;
    private List<ExtraFeeDTO> extraFeeDTOList;
    private ExtraFee extraFee;
    private ExtraFeeDTO archiveExtraFeeDTO;
    private ExtraFeeDTO extraFeeDTO;

    @BeforeEach
    public void setUp() {
        extraFeeService = new ExtraFeeServiceImpl(extraFeeRepository,
                extraFeeDTOExtraFeeTypeConverter, extraFeeExtraFeeDTOTypeConverter);
        extraFeeList = createExtraFeeList();
        extraFee = createExtraFeeWithData();
        archiveExtraFeeDTO = createExtraFeeDtoWithData();
        extraFeeDTO = createExtraFeeDtoWithData();
        extraFeeDTOList = createExtraFeeDTOList();
    }

    @Nested
    @DisplayName("When Find ExtraFee By Id")
    class FindExtraFeeByIdTests {
        @Test
        void testFindExtraFeeById_ShouldReturnExtraFee() {
            when(extraFeeRepository.findById(EXTRA_FEE_ID)).thenReturn(Optional.ofNullable(extraFee));
            when(extraFeeExtraFeeDTOTypeConverter.convert(extraFee)).thenReturn(extraFeeDTO);

            ExtraFeeDTO result = extraFeeService.findExtraFeeById(EXTRA_FEE_ID);

            assertEquals(extraFeeDTO, result);
            verify(extraFeeRepository).findById(any(Long.class));
            assertNotNull(result);
            assertEquals(result, extraFeeDTO);
            Assertions.assertThat(result.getName()).isEqualTo(extraFeeDTO.getName());

        }

        @Test
        void testFindExtraFeeById_shouldThrowException() {

            assertThrows(ResourceNotFoundException.class,
                    () -> extraFeeService.findExtraFeeById(EXTRA_FEE_ID));
        }

    }

    @Nested
    @DisplayName("When Find All ExtraFees")
    class FindAllExtraFeesTests {

        @Test
        void testFindAllExtraFees_shouldReturnAllExtraFees() {
            when(extraFeeRepository.findAll()).thenReturn(extraFeeList);
            when(extraFeeExtraFeeDTOTypeConverter.convert(extraFeeList)).thenReturn(extraFeeDTOList);

            List<ExtraFeeDTO> foundExtraFeeDtoList = extraFeeService.findAllExtraFees();

            assertEquals(extraFeeDTOList.size(), foundExtraFeeDtoList.size());

        }

        @Test
        void testFindAllExtraFees_shouldThrowException() {
            extraFeeRepository.deleteAll();
            extraFeeDTOList = new ArrayList<>();
            extraFeeList = new ArrayList<>();
            when(extraFeeRepository.findAll()).thenReturn(extraFeeList);

            List<ExtraFeeDTO> result = extraFeeService.findAllExtraFees();

            verify(extraFeeRepository).findAll();
            assertNotNull(result);
            assertEquals(result, extraFeeDTOList);
            Assertions.assertThat(result.size()).isEqualTo(extraFeeDTOList.size());

        }

    }

    @Nested
    @DisplayName("When Create a ExtraFee")
    class CreateExtraFeeTests {
        @Test
        void testCreateExtraFee_ShouldReturnExtraFee() {
            when(extraFeeDTOExtraFeeTypeConverter.convert(extraFeeDTO)).thenReturn(extraFee);
            when(extraFeeRepository.saveAndFlush(extraFee)).thenReturn(extraFee);
            when(extraFeeExtraFeeDTOTypeConverter.convert(extraFee)).thenReturn(extraFeeDTO);

            ExtraFeeDTO result = extraFeeService.createExtraFee(extraFeeDTO);

            assertEquals(extraFeeDTO, result);
            assertThat(result.getName()).isEqualTo(extraFeeDTO.getName());
            verify(extraFeeRepository).saveAndFlush(any(ExtraFee.class));
            assertNotNull(result);
        }

        @Test
        void testCreateExtraFee_shouldThrowException() {
            extraFeeDTO=null;
            assertThrows(IllegalArgumentException.class,
                    () -> extraFeeService.createExtraFee(extraFeeDTO));
        }
    }

    @Nested
    @DisplayName("When archive a ExtraFee")
    class ArchiveExtraFeeTests {
        @Test
        void testArchiveExtraFee_shouldReturnExtraFeeWithArchiveStatus() {
            when(extraFeeRepository.findById(EXTRA_FEE_ID)).thenReturn(Optional.ofNullable(extraFee));
            archiveExtraFeeDTO.setStatus(Status.ARCHIVE);
            when(extraFeeRepository.saveAndFlush(extraFee)).thenReturn(extraFee);
            when(extraFeeExtraFeeDTOTypeConverter.convert(extraFee)).thenReturn(archiveExtraFeeDTO);

            ExtraFeeDTO result = extraFeeService.archiveExtraFee(EXTRA_FEE_ID);

            assertEquals(archiveExtraFeeDTO, result);
            assertThat(result.getName()).isEqualTo(extraFeeDTO.getName());
            assertNotNull(result);
            verify(extraFeeRepository).saveAndFlush(any(ExtraFee.class));

        }

        @Test
        void testArchiveExtraFee_shouldThrowException() {
            assertThrows(ResourceNotFoundException.class,
                    () -> extraFeeService.archiveExtraFee(EXTRA_FEE_ID));

        }

    }

    @Nested
    @DisplayName("When Delete a ExtraFee")
    class DeleteExtraFeeTests {
        @Test
        void testDeleteExtraFee_shouldReturnExtraFee() {
            when(extraFeeRepository.findById(EXTRA_FEE_ID)).thenReturn(Optional.of(extraFee));
            extraFeeService.deleteExtraFeeById(EXTRA_FEE_ID);
            verify(extraFeeRepository, times(1)).delete(extraFee);

        }

        @Test
        void testDeleteExtraFee_shouldThrowException() {

            assertThrows(ResourceNotFoundException.class,
                    () -> extraFeeService.deleteExtraFeeById(EXTRA_FEE_ID));
            verify(extraFeeRepository, times(0)).delete(extraFee);

        }

    }

}