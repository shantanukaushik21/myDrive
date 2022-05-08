package com.example.myDrive;

import com.example.myDrive.DBManager.DBOperations;
import com.example.myDrive.Model.User;

import java.io.EOFException;
import java.io.IOException;

import com.google.cloud.storage.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.nio.file.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@SpringBootApplication
public class MyDriveApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(MyDriveApplication.class, args);
		DBOperations test=new DBOperations();
		test.addUser("abhishek","a@gmail.com","12345");
//		test.addUser();
//		test.createConnection("marine-physics-349505");

//		uploadObject();
		System.out.println("end");
	}
	public ArrayList<User> getUsers() throws Exception {
		DBOperations test=new DBOperations();
		return test.getUsers();
	}

	public User adduser(User user) throws Exception {
		System.out.println(user.getUser_id());
		DBOperations test=new DBOperations();
		User res=test.addUser(user.getName(),user.getEmail(),user.getPassword());
		return res;
	}

	public static void uploadObject() throws IOException {
		{
			// The ID of your GCP project
			 String projectId = "marine-physics-349505";

			// The ID of your GCS bucket
			 String bucketName = "mydrive_99100";
//			String bucketName = "testcreate99100";

			// The ID of your GCS object
			 String objectName = "testUser/photo1.jpg";

			// The path to your file to upload
			 String filePath = "D:/source/photo.jpg";
			long bytes = Files.size(Path.of(filePath));
			if(bytes/1024 > (long)10000){
				throw new EOFException("Currently not allowed to upload file more than 10000 KB");
			}

			Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
//			Bucket bucket = storage.create(BucketInfo.of(bucketName));
//			System.out.printf("Bucket %s created.%n", bucket.getName());

			BlobId blobId = BlobId.of(bucketName, objectName);
			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
			storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

			System.out.println(
					"File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
		}
	}

}
