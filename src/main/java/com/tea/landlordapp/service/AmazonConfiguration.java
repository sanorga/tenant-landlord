package com.tea.landlordapp.service;

import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.AWSKMSClient;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

@Configuration
public class AmazonConfiguration {
 
    /**
     * Creates an Amazon S3 Client.
     * @return AmazonS3Client
     */
	
//	@Value("#{environment['AWS_ACCESS_KEY_ID']}")
//	private String key;
//	@Value("#{environment['AWS_SECRET_KEY']}")
//	private String secret;
	
	
    @Bean
    public AmazonS3 amazonS3() throws IOException {
    	AmazonS3 s3;
//    	if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(secret)){
//    		BasicAWSCredentials creds = new BasicAWSCredentials(key, secret);
//    		s3 = new AmazonS3Client(creds);
//    	} else {
    		s3 = new AmazonS3Client();
//    	}
    		s3.setRegion(Region.getRegion(Regions.US_EAST_1));
//    	s3.setEndpoint("https://s3.amazonaws.com");
    	return s3;
    }
	
	@Bean
	public AWSKMSClient kmsClient(){
		AWSKMSClient kms = new AWSKMSClient();
		kms.setEndpoint("https://kms.us-east-1.amazonaws.com");
		return kms;
	}
}