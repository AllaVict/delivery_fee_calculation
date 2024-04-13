package com.deliveryfeecalculation.converter.impl;

import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.deliveryfeecalculation.factory.ExtraFeeFactory.createExtraFeeDtoWithData;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ExtraFeeDtoToExtraFeeConverter")
class ExtraFeeDtoToExtraFeeConverterTest {

    private ExtraFeeDtoToExtraFeeConverter converter = new ExtraFeeDtoToExtraFeeConverter();

    private ExtraFeeDTO extraFeeDTO;

    @BeforeEach
    public void setUp() {
        extraFeeDTO = createExtraFeeDtoWithData();
    }

    @Test
    void shouldGetTargetClass() {
        assertEquals(ExtraFee.class, converter.getTargetClass());
    }

    @Test
    void shouldGetSourceClass() {
        assertEquals(ExtraFeeDTO.class, converter.getSourceClass());
    }

    @Test
    void testConvert_shouldConvertExtraFeeDToExtraFee() {
        ExtraFee result = converter.convert(extraFeeDTO);

        assertNotNull(result);
        assertEquals(extraFeeDTO.getName(), result.getName());
        assertEquals(extraFeeDTO.getStatus(), result.getStatus());
    }
}