package com.deliveryfeecalculation.converter.impl;

import com.deliveryfeecalculation.domain.dto.BaseFeeDTO;
import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.model.BaseFee;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.deliveryfeecalculation.factory.BaseFeeFactory.createBaseFee;
import static com.deliveryfeecalculation.factory.ExtraFeeFactory.createExtraFeeWithData;
import static org.junit.jupiter.api.Assertions.*;

    @DisplayName("BaseFeeToBaseFeeDtoConverterTest")
    class BaseFeeToBaseFeeDtoConverterTest {

        private BaseFeeToBaseFeeDtoConverter converter = new BaseFeeToBaseFeeDtoConverter();

        private BaseFee baseFee;

        @BeforeEach
        public void setUp() {
            baseFee = createBaseFee();
        }

        @Test
        void shouldGetTargetClass() {
            assertEquals(BaseFeeDTO.class, converter.getTargetClass());
        }

        @Test
        void shouldGetSourceClass() {
            assertEquals(BaseFee.class, converter.getSourceClass());
        }


        @Test
        void testConvert_shouldConvertBaseFeeToBaseFeeDto() {
            BaseFeeDTO result = converter.convert(baseFee);

            assertNotNull(result);
            assertEquals(baseFee.getCity(), result.getCity());
            assertEquals(baseFee.getStatus(), result.getStatus());
        }

    }