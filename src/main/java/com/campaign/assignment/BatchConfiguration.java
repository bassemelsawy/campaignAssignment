package com.campaign.assignment;


import com.campaign.assignment.model.Input;
import com.campaign.assignment.processor.InputItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<Input> reader() {

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames(new String[]{"id", "name"});

        DefaultLineMapper lineMapper = new DefaultLineMapper<Input>();
        lineMapper.setLineTokenizer(tokenizer);


        return new FlatFileItemReaderBuilder<Input>()
                .name("InputItemReader")
                .resource(new ClassPathResource("Input.csv"))
                .lineTokenizer(tokenizer)
                //.delimited()
                //.names(new String[]{"filed1", "filed1"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Input>() {{
                    setTargetType(Input.class);
                }})
                .build();
    }

    @Bean
    public InputItemProcessor processor(){
        return new InputItemProcessor();
    }

    @Bean
    public FlatFileItemWriter<Input> writer(){
        FlatFileItemWriter<Input> writer = new FlatFileItemWriter<Input>();
        writer.setResource(new ClassPathResource("fileOutput.csv"));
        writer.setLineAggregator(new DelimitedLineAggregator<Input>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<Input>() {{
                setNames(new String[] { "id", "name" });
            }});
        }});

        return writer;
    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<Input, Input> chunk(3)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job importUserJob() {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

}
