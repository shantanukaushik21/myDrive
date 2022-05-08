package com.example.myDrive.Controller;

import com.example.myDrive.Model.*;
import com.example.myDrive.MyDriveApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MyDriveController {
	@GetMapping("/hi")
	public String sayHi() {
		return "Hello from our server";
	}

	@GetMapping("/getUsers")
	public List<User> getUsers() throws Exception {
		MyDriveApplication app=new MyDriveApplication();
		return app.getUsers();
	}

	@RequestMapping(value = "/insertUser",method= RequestMethod.POST)
	public String insertUser(@RequestBody User user) throws Exception {
		MyDriveApplication app=new MyDriveApplication();
		User u=app.adduser(user);
		if(u==null){
			return "This email id is Already Registered";
		}
		System.out.println(user.toString());
		return user.getEmail()+" is added as a user, Now he can Upload flies to his drive";
	}

	@RequestMapping(value = "/uploadFile",method= RequestMethod.POST)
	public FileUploadResponse uploadFile(@RequestBody FileUploadRequest req) throws Exception {
		MyDriveApplication app=new MyDriveApplication();
		FileUploadResponse response=app.fileUploadService(req);

		return response;
	}

	@RequestMapping(value = "/getFilesByUserID",method= RequestMethod.POST)
	public GetFilesByUserIdResponse getFilesByUserID(@RequestBody GetFilesByUserIdRequest req) throws Exception {
		MyDriveApplication app=new MyDriveApplication();
		GetFilesByUserIdResponse response=app.getFilesByUserID(req.getUser_id(),req.getEmail(),req.getPassword());

		return response;
	}


}
