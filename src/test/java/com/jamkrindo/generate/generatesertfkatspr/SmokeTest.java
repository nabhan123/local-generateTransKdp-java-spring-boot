package com.jamkrindo.generate.generatesertfkatspr;


import com.jamkrindo.generate.generatesertfkatspr.controller.PubConsPenKdp;
import com.jamkrindo.generate.generatesertfkatspr.controller.UnitTest;
import org.apache.coyote.BadRequestException;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SmokeTest {

    @Autowired
    private UnitTest unitTest;

    @InjectMocks
    PubConsPenKdp pubConsPenKdp;

//    @Test
//    @DisplayName("Negative Case : inputan null")
//    void nullInput(){
//        Assertions.assertThrows(NullPointerException.class, () -> pubConsPenKdp.fndDebPenKdpToReadySync(null));
//    }




    @Test
    @DisplayName("Trial unit test")
    void testCall() throws Exception{
//        assert (unitTest).isNotNull();
    }

}
