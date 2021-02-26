package com.campaign.assignment.mapper;

import com.campaign.assignment.model.Input;
import org.springframework.batch.item.file.LineMapper;

import java.util.*;

public class InputReaderMapper  implements LineMapper<Input> {
    @Override
    public Input mapLine(String line, int lineNumber) throws Exception {
       String[] splited = line.split("\\s+");

        List<String> segments = new ArrayList<>();
       for(int i = 1 ; i < splited.length ; i++ ){
           segments.add(splited[i]);
       }

        Input input = new Input();
        input.setId(splited[0]);
        input.setSegments(segments);
        System.out.println(input);

        return input;
    }
}
