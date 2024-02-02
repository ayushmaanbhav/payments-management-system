package com.ayushmaanbhav.eventProcessor.testsetup.mock;

import com.ayushmaanbhav.commonsspring.api.event.EventApi;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;

@Service
public class MockSetup {

    @MockBean
    EventApi eventApi;

}
