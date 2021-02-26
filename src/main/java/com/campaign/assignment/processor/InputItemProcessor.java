package com.campaign.assignment.processor;

import com.campaign.assignment.model.Input;
import com.campaign.assignment.model.TargetCampaign;
import org.springframework.batch.item.ItemProcessor;

public class InputItemProcessor  implements ItemProcessor<Input, TargetCampaign> {

    @Override
    public TargetCampaign process(Input input) throws Exception {
        /*
            Replace input Id with the camp name "result of the solution algorithm"
        */
        TargetCampaign targetCamp = new TargetCampaign();
        targetCamp.setCampaignName(input.getId());
        return targetCamp;
    }
}
