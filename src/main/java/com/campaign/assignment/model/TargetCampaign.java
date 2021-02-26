package com.campaign.assignment.model;

import lombok.Data;
import lombok.Setter;


public class TargetCampaign {
    private String campaignName;

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }
}
