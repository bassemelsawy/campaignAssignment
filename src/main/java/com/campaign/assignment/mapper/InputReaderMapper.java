package com.campaign.assignment.mapper;

import com.campaign.assignment.model.Input;
import org.springframework.batch.item.file.LineMapper;
import java.util.*;

public class InputReaderMapper  implements LineMapper<Input> {
    @Override
    public Input mapLine(String line, int lineNumber) throws Exception {

       String[] splittedLine = line.split("\\s+");
       List<String> segments = new ArrayList<>();

       for(int i = 1 ; i < splittedLine.length ; i++ ){
           segments.add(splittedLine[i]);
       }

        Input input = new Input();
        input.setId(splittedLine[0]);
        input.setSegments(segments);
        return input;
    }
}
