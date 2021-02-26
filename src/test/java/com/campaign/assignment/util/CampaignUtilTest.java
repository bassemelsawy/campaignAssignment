package com.campaign.assignment.util;

import com.campaign.assignment.model.Input;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

class CampaignUtilTest {

    CampaignUtil campaignUtil;

    @BeforeEach
    void setUp() throws IOException {
        campaignUtil = new CampaignUtil();
    }

    @Test
    @DisplayName("should get the right campaign name when call process with a line from input.txt")
    void process() {
        Input input = new Input();
        input.setId("1");
        input.setSegments(List.of("20231","19691", "13659", "7507", "19024", "4328"));
        assertThat(campaignUtil.process(input)).isEqualTo("serials");
    }
}