package com.deliveryfeecalculation.converter.impl;

import com.deliveryfeecalculation.domain.dto.BaseFeeDTO;
import com.deliveryfeecalculation.domain.dto.ExtraFeeDTO;
import com.deliveryfeecalculation.domain.model.BaseFee;
import com.deliveryfeecalculation.domain.model.ExtraFee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.deliveryfeecalculation.factory.BaseFeeFactory.createBaseFeeDto;
import static com.deliveryfeecalculation.factory.ExtraFeeFactory.createExtraFeeDtoWithData;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BaseFeeDtoToBaseFeeConverterTest")
class BaseFeeDtoToBaseFeeConverterTest {

        private BaseFeeDtoToBaseFeeConverter converter = new BaseFeeDtoToBaseFeeConverter();

        private BaseFeeDTO baseFeeDTO;

        @BeforeEach
        public void setUp() {
            baseFeeDTO = createBaseFeeDto();
        }

        @Test
        void shouldGetTargetClass() {
            assertEquals(BaseFee.class, converter.getTargetClass());
        }

        @Test
        void shouldGetSourceClass() {
            assertEquals(BaseFeeDTO.class, converter.getSourceClass());
        }

        @Test
        void testConvert_shouldConvertBaseFeeDToBaseFee() {
            BaseFee result = converter.convert(baseFeeDTO);

            assertNotNull(result);
            assertEquals(baseFeeDTO.getCity(), result.getCity());
            assertEquals(baseFeeDTO.getStatus(), result.getStatus());
        }
    }