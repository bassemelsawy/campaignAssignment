package com.campaign.assignment.processor;

import com.campaign.assignment.model.Input;
import org.springframework.batch.item.ItemProcessor;

public class InputItemProcessor  implements ItemProcessor<Input, Input> {

    @Override
    public Input process(Input input) throws Exception {
        System.out.println(input);
        return input;
    }
}
