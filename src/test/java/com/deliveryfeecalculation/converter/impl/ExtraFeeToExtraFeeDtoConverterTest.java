package com.deliveryfeecalculation.converter.impl;

import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.deliveryfeecalculation.factory.ExtraFeeFactory.createExtraFeeWithData;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ExtraFeeToExtraFeeDtoConverter")
class ExtraFeeToExtraFeeDtoConverterTest {

    private ExtraFeeToExtraFeeDtoConverter converter = new ExtraFeeToExtraFeeDtoConverter();

    private ExtraFee extraFee;

    @BeforeEach
    public void setUp() {
        extraFee = createExtraFeeWithData();
    }

    @Test
    void shouldGetTargetClass() {
        assertEquals(ExtraFeeDTO.class, converter.getTargetClass());
    }

    @Test
    void shouldGetSourceClass() {
        assertEquals(ExtraFee.class, converter.getSourceClass());
    }


    @Test
    void testConvert_shouldConvertExtraFeeToExtraFeeDto() {
        ExtraFeeDTO result = converter.convert(extraFee);

        assertNotNull(result);
        assertEquals(extraFee.getName(), result.getName());
        assertEquals(extraFee.getStatus(), result.getStatus());
    }

}