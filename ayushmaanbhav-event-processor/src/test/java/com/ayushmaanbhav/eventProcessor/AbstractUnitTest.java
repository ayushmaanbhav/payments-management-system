package com.ayushmaanbhav.eventProcessor;

import com.ayushmaanbhav.eventProcessor.spring.BaseApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BaseApplication.class)
public abstract class AbstractUnitTest {

}
