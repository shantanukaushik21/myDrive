package com.example.myDrive;

import com.example.myDrive.DBManager.DBOperations;
import com.example.myDrive.Model.File;
import com.example.myDrive.Model.FileUploadRequest;
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


//		test.getFileByUser(1);
		FileUploadRequest req=new FileUploadRequest();
		req.setEmail("kkkk@gmail.com");
		req.setPassword("pass1");
		req.setFileName("k.jpg");
		req.setFileLocation("D:/source/photo.jpg");
		fileUploadService(req);

//		test.add_user_file_mapping(3,4);
//		test.getFileByUser(4);
	}
	public static ArrayList<User> getUsers() throws Exception {
		DBOperations test=new DBOperations();
		return test.getUsers();
	}
	public static ArrayList<File> getFiles() throws Exception {
		DBOperations test=new DBOperations();
		return test.getFiles();
	}

	public User adduser(User user) throws Exception {
//		System.out.println(user.getUser_id());
		DBOperations test=new DBOperations();
		User res=null;
		try{
			res=test.addUser(user.getName(),user.getEmail(),user.getPassword());
		}catch (Exception e){ return res;}

		return res;
	}

//	public static void uploadObject() throws IOException {
//			// The ID of your GCP project
//			 String projectId = "marine-physics-349505";
//
//			// The ID of your GCS bucket
//			 String bucketName = "mydrive_99100";
////			String bucketName = "testcreate99100";
//
//			// The ID of your GCS object
//			 String objectName = "testUser/photo2.jpg";
//
//			// The path to your file to upload
//			 String filePath = "D:/source/photo.jpg";
//			long bytes = Files.size(Path.of(filePath));
//			if(bytes/1024 > (long)10000){
//				throw new EOFException("Currently not allowed to upload file more than 10000 KB");
//			}
//
//			Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
////			Bucket bucket = storage.create(BucketInfo.of(bucketName));
////			System.out.printf("Bucket %s created.%n", bucket.getName());
//
//			BlobId blobId = BlobId.of(bucketName, objectName);
//			BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
//			storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));
//
//			System.out.println(
//					"File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
//	}

	public static String uploadObject(String projectId,String bucketName,String objectName,String filePath) throws IOException {
		long bytes = Files.size(Path.of(filePath));
		if(bytes/1024 > (long)10000){
			throw new EOFException("Currently not allowed to upload file more than 10000 KB");
		}

		Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
		BlobId blobId = BlobId.of(bucketName, objectName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

		String message="File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName;
		System.out.println(message);
		return message;

	}

	public static int codeGenerator(String str){
		return str.hashCode();
	}

	public static User authenticate(String email, String password) throws Exception {
		DBOperations test=new DBOperations();
		ArrayList<User> Allusers;
		Allusers=test.getUsers();
		System.out.println(Allusers.size());
//		System.out.println("inside auth");
		User res=null;
		for(User u: Allusers){
			System.out.println(u.toString());
			if(u.getEmail().compareTo(email)==0&&u.getPassword().compareTo(password)==0){
				res=u;
				System.out.println("User is Authorised");
				break;
			}

		}
		return res;
	}

	public static void fileUploadService(FileUploadRequest uploadDetails) throws Exception {
		User user=null;
		ArrayList<File> userFiles=null;
		DBOperations db=new DBOperations();
		user=authenticate(uploadDetails.getEmail(),uploadDetails.getPassword());
		String msg="";
		if(user==null){
			//not allowed to upload a file
			msg="user not Authorised";
			System.out.println("null");
		}else{
			userFiles=db.getFileByUser(user.getUser_id());
			boolean flag=true;
			if(userFiles!=null){
				for(File f: userFiles){
					if(f.getFile_name()==uploadDetails.getFileName()){
						msg= msg+"   "+ "user has already uploaded a file with same name try changing the name";
						System.out.println("user has already uploaded a file with same name try changing the name");
						flag=false;
//					return msg;
					}
				}
			}
			if(flag){
				msg+=uploadObject("marine-physics-349505","mydrive_99100","DriveUser/"+uploadDetails.getFileName(),uploadDetails.getFileLocation());
//				File file=new File(uploadDetails.getFileName(),"DriveUser/"+uploadDetails.getFileName());
				File file=db.addFile(uploadDetails.getFileName(),"DriveUser/"+uploadDetails.getFileName());
				msg+=db.add_user_file_mapping(user.getUser_id(),file.getFile_id());
			}

		}
		System.out.println(msg);

	}
}

