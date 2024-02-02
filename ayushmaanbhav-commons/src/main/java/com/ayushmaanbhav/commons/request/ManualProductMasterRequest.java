package com.ayushmaanbhav.commons.request;

import lombok.Data;

@Data
public class ManualProductMasterRequest {

    private int product_code;
    private String brand;
    private String display_name;
    private String food_type;
    private float avg_mrp;
    private String level2_search_keys;
    private String image_url;
    private String formulation_type;

}
