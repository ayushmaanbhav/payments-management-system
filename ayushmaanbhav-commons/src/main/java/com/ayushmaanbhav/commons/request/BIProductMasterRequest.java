package com.ayushmaanbhav.commons.request;

import lombok.Data;

@Data
public class BIProductMasterRequest {

    private ProductData data;

    @Data
    public static class ProductData {

        private Product product;

        @Data
        public static class Product{

            private String brand;
            private String name;
            private Attributes attributes;
            private float mrp;
            private SubCategory leaf_category;
            private String image_url;
            private int product_id;

            @Data
            public static class Attributes {
                private String food_type;
            }

            @Data
            public static class SubCategory {
                private String name;
            }

        }

    }

}
