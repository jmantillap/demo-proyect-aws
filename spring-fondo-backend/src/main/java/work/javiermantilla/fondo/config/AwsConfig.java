package work.javiermantilla.fondo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Configuration
public class AwsConfig {

	@Bean
	DynamoDBMapper dynamoDBMapper() {

//		AmazonDynamoDB amazonDynamoDBConfig = AmazonDynamoDBClientBuilder.standard()
//				.withEndpointConfiguration(
//						new AwsClientBuilder.EndpointConfiguration("dynamodb.us-east-1.amazonaws.com", "us-east-1"))
//				.withCredentials(new AWSStaticCredentialsProvider(
//						new BasicAWSCredentials("", "")))
//				.build();
//
//		return new DynamoDBMapper(amazonDynamoDBConfig);
		 //"us-east-1"
		 DefaultAWSCredentialsProviderChain credentialsProviderChain = new DefaultAWSCredentialsProviderChain();
		AmazonDynamoDB amazonDynamoDBConfig= AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(
						new AwsClientBuilder.EndpointConfiguration("dynamodb.us-east-1.amazonaws.com",Regions.US_EAST_1.getName()))
				.withCredentials(credentialsProviderChain)
				.build();
						
		return new DynamoDBMapper(amazonDynamoDBConfig);

	}


}