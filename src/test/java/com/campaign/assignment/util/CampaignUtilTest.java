package com.campaign.assignment.util;

import com.campaign.assignment.model.Input;
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
    void shouldReturnCampaignName_WhenCallProcess() {
        Input input = new Input();
        input.setId("1");
        input.setSegments(List.of("22207", "28404", "28113", "24604", "15686", "17632", "7975"));
        assertThat(campaignUtil.process(input)).isEqualTo("selma");
    }


    @Test
    @DisplayName("test the distrbution ")
    void shouldSelectTheValidCampaignBasedOnDistribution_WhenCallProcess() {
        Input input1 = new Input();
        input1.setId("1");
        input1.setSegments(List.of("8023"));

        assertThat(campaignUtil.process(input1)).isEqualTo("seaming");

        Input input2 = new Input();
        input2.setId("2");
        input2.setSegments(List.of("8023"));

        assertThat(campaignUtil.process(input2)).isEqualTo("genre");
    }
}