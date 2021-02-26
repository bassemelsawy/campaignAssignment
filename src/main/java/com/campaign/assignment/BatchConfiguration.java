package com.campaign.assignment;


import com.campaign.assignment.mapper.InputReaderMapper;
import com.campaign.assignment.model.Input;
import com.campaign.assignment.model.TargetCampaign;
import com.campaign.assignment.processor.InputItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    private Resource outputResource = new FileSystemResource("output/outputData.csv");

    @Bean
    public FlatFileItemReader<Input> reader()
    {
        FlatFileItemReader<Input> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("Input.txt"));
        reader.setLineMapper(new InputReaderMapper());
        return reader;
    }

    @Bean
    public InputItemProcessor processor(){
        return new InputItemProcessor();
    }

    @Bean
    public FlatFileItemWriter<TargetCampaign> writer()
    {
        FlatFileItemWriter<TargetCampaign> writer = new FlatFileItemWriter<>();

        writer.setResource(outputResource);

        writer.setLineAggregator(new DelimitedLineAggregator<>() {
            {
                setFieldExtractor(new BeanWrapperFieldExtractor<TargetCampaign>() {
                    {
                        setNames(new String[] {"campaignName"});
                    }
                });
            }
        });
        return writer;
    }



    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<Input, TargetCampaign> chunk(3)
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
