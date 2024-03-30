package com.deliveryfeecalculation.constants;

import org.springframework.stereotype.Component;

@Component
public final class Constants {

    private Constants() {
    }

    public static final class Endpoints {

        public static final String URL = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

        public static final String FEE_URL = "/fee";
        public static final String EXTRA_FEE_URL = "/extra_fee";

        private Endpoints() {
        }
    }

    public static final class Messages {

        public static final String VEHICLE_FORBIDDEN = "Usage of selected vehicle type is forbidden.";

        public static final String DELIVERY_FEE_CALCULATION = "Delivery fee calculation: ";

        private Messages() {
        }
    }

    public static final class StationNames {

        public static final String TALLINN = "Tallinn-Harku";

        public static final String TARTU = "Tartu-Tõravere";

        public static final String PARNU = "Pärnu";

        private StationNames() {
        }
    }


}