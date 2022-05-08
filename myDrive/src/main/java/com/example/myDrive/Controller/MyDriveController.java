package com.example.myDrive.Controller;

import com.example.myDrive.Model.File;
import com.example.myDrive.Model.FileUploadRequest;
import com.example.myDrive.Model.GetFilesByUserIdRequest;
import com.example.myDrive.Model.User;
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
	
	@GetMapping("/search")
	public String search(@RequestParam String q) {
		return q+" Is a hero";
	}

	@GetMapping("/getUsers")
	public List<User> getUsers() throws Exception {
		MyDriveApplication app=new MyDriveApplication();
		return app.getUsers();

	}

//	@RequestMapping(value = "/insertUser", method = RequestMethod.POST)
//	@PostMapping("/insertUser")
	@RequestMapping(value = "/insertUser",method= RequestMethod.POST)
	public String insertUser(@RequestBody User user) throws Exception {
		MyDriveApplication app=new MyDriveApplication();
		User u=app.adduser(user);
		if(u==null){
			return "Already Registered Please try new credentials";
		}
		System.out.println(user.toString());
		return user.getName()+" "+user.getEmail()+" Is added to database";
	}

	@RequestMapping(value = "/uploadFile",method= RequestMethod.POST)
	public String uploadFile(@RequestBody FileUploadRequest req) throws Exception {
		MyDriveApplication app=new MyDriveApplication();
		String msg= app.fileUploadService(req);

		return msg;
	}



	@RequestMapping(value = "/getFilesByUserID",method= RequestMethod.POST)
	public ArrayList<File> getFilesByUserID(@RequestBody GetFilesByUserIdRequest req) throws Exception {
		MyDriveApplication app=new MyDriveApplication();
		ArrayList<File> res=app.getFilesByUserID(req.getUser_id(),req.getEmail(),req.getPassword());

		return res;
	}


}
