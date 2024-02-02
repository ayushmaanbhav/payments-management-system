package com.ayushmaanbhav.commons.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserData {

    private String mobile;
    private List<String> ayushmaanbhavFirebaseToken = new ArrayList<>();
    private List<String> ayushmaanbhavPartnerFirebaseToken = new ArrayList<>();

    public void addayushmaanbhavFirebaseToken(String ayushmaanbhavFirebaseToken) {
        this.ayushmaanbhavFirebaseToken.add(ayushmaanbhavFirebaseToken);
    }

    public void addayushmaanbhavPartnerFirebaseToken(String ayushmaanbhavPartnerFirebaseToken) {
        this.ayushmaanbhavPartnerFirebaseToken.add(ayushmaanbhavPartnerFirebaseToken);
    }
}
