package com.campaign.assignment.processor;

import com.campaign.assignment.model.Input;
import com.campaign.assignment.model.TargetCampaign;
import com.campaign.assignment.util.CampaignUtil;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class InputItemProcessor  implements ItemProcessor<Input, TargetCampaign> {

    @Autowired
    CampaignUtil campaignUtil;

    @Override
    public TargetCampaign process(Input input) {
        String campaignResult =  campaignUtil.process(input);
        TargetCampaign targetCamp = new TargetCampaign();
        targetCamp.setCampaignName(campaignResult);
        return targetCamp;
    }

}
