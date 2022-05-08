package com.example.myDrive.DBManager;

import com.example.myDrive.Model.File;
import com.example.myDrive.Model.User;
//import org.springframework.data.jpa.repository.JpaRepository;
import com.google.cloud.bigquery.*;
import com.google.cloud.bigquery.BigQueryError;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Repository
//@Component
public class DBOperations{
    private BigQuery bigquery;
    boolean isConnected=false;
//    public void createConnection() throws Exception {
//        bigquery = BigQueryOptions.newBuilder().setProjectId("marine-physics-349505")
//                .build().getService();
//        final String getUsers = "SELECT * FROM `marine-physics-349505.my_Drive.User_table` LIMIT 1000";
//        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(getUsers).build();
//
//        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).build());
//        queryJob = queryJob.waitFor();
//        // the waitFor method blocks until the job completes
//        // and returns `null` if the job doesn't exist anymore
//        if (queryJob == null) {
//            throw new Exception("job no longer exists");
//        }
//        // once the job is done, check if any error occured
//        if (queryJob.getStatus().getError() != null) {
//            throw new Exception(queryJob.getStatus().getError().toString());
//        }
//
//        // Step 4: Display results
//        // Print out a header line, and iterate through the
//        // query results to print each result in a new line
//        System.out.println("user table");
//        TableResult result = queryJob.getQueryResults();
//        for (FieldValueList row : result.iterateAll()) {
//            // We can use the `get` method along with the column
//            // name to get the corresponding row entry
//            String user_name = row.get("user_name").getStringValue();
//            int userid = Integer.parseInt(row.get("user_id").getValue().toString());
////            System.out.printf("%s\t%d\n", userid, user_name);
//            System.out.print(userid+"    "+user_name);
//        }
//    }

    public void createConnection() throws Exception {
        if(isConnected){
            return;
        }
        bigquery = BigQueryOptions.newBuilder().setProjectId("marine-physics-349505")
                .build().getService();
        isConnected=true;
    }
//    public ArrayList<User> getUsers() throws Exception {
//        createConnection("marine-physics-349505");
//        ArrayList<User> users = new ArrayList<>();
//
//        final String getUsers = "SELECT * FROM `marine-physics-349505.my_Drive.User_table` LIMIT 1000";
//        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(getUsers).build();
//
//        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).build());
//        queryJob = queryJob.waitFor();
//        // the waitFor method blocks until the job completes
//        // and returns `null` if the job doesn't exist anymore
//        if (queryJob == null) {
//            throw new Exception("job no longer exists");
//        }
//        // once the job is done, check if any error occured
//        if (queryJob.getStatus().getError() != null) {
//            throw new Exception(queryJob.getStatus().getError().toString());
//        }
//
//        // Step 4: Display results
//        // Print out a header line, and iterate through the
//        // query results to print each result in a new line
//        System.out.println("user table");
//        TableResult result = queryJob.getQueryResults();
//        for (FieldValueList row : result.iterateAll()) {
//            // We can use the `get` method along with the column
//            // name to get the corresponding row entry
//            String user_name = row.get("user_name").getStringValue();
//            int userid = Integer.parseInt(row.get("user_id").getValue().toString());
////            System.out.printf("%s\t%d\n", userid, user_name);
//            User user=new User(userid,user_name);
//            users.add(user);
//            System.out.print(userid+"    "+user_name);
//        }
//        return users;
//    }

    public TableResult runQuery(String query) throws Exception {
//        createConnection("marine-physics-349505");
        ArrayList<User> users = new ArrayList<>();

        final String getUsers = query;
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(getUsers).build();

        Job queryJob = bigquery.create(JobInfo.newBuilder(queryConfig).build());
        queryJob = queryJob.waitFor();
        // the waitFor method blocks until the job completes
        // and returns `null` if the job doesn't exist anymore
        if (queryJob == null) {
            throw new Exception("job no longer exists");
        }
        // once the job is done, check if any error occured
        if (queryJob.getStatus().getError() != null) {
            throw new Exception(queryJob.getStatus().getError().toString());
        }

        // Step 4: Display results
        // Print out a header line, and iterate through the
        // query results to print each result in a new line
//        System.out.println("user table");
        TableResult result = queryJob.getQueryResults();
        return result;
    }


    public User addUser(String name, String email, String password ) throws Exception {
        createConnection();
        ArrayList<User> users=new ArrayList<>();
        users=getUsers();
        int count= users.size();
        int user_id=count+1;
        System.out.println(user_id);

        //check if user has already registered
        for(User temp : users){
            System.out.println(temp.getEmail());
            if(temp.getEmail().compareTo(email)==0){
                throw new Exception("This user already has a account. Email should be unique");
            }
        }

        String datasetName="my_Drive";
        String tableName="User_table";

        User res=new User(user_id,name,email,password);

        BigQuery bq=BigQueryOptions.getDefaultInstance().getService();

        Map<String, Object> rowContent = new HashMap<>();


        rowContent.put("user_id", user_id);
        rowContent.put("user_name", name);
        rowContent.put("email", email);
        rowContent.put("password", password);

        TableId tableId = TableId.of(datasetName, tableName);
        InsertAllResponse response =
                bigquery.insertAll(
                        InsertAllRequest.newBuilder(tableId)
                                // More rows can be added in the same RPC by invoking .addRow() on the builder.
                                // You can also supply optional unique row keys to support de-duplication
                                // scenarios.
                                .addRow(rowContent)
                                .build());
        if (response.hasErrors()) {

            // If any of the insertions failed, this lets you inspect the errors
            for (Map.Entry<Long, List<BigQueryError>> entry : response.getInsertErrors().entrySet()) {
                System.out.println("Response error: \n" + entry.getValue());
            }
            throw new Exception("Insert Exception");
        }
        System.out.println("Rows successfully inserted into table");

        return res;
    }

    public File addFile(String name, String location) throws Exception {
//        createConnection();
        bigquery = BigQueryOptions.newBuilder().setProjectId("marine-physics-349505")
                .build().getService();
        ArrayList<File> Allfiles=new ArrayList<>();
        Allfiles=getFiles();
        int count= Allfiles.size();
        int file_id=count+1;
        System.out.println(file_id);

        String datasetName="my_Drive";
        String tableName="files_table";

        File res=new File(file_id,name,location);

        BigQuery bq=BigQueryOptions.getDefaultInstance().getService();

        Map<String, Object> rowContent = new HashMap<>();


        rowContent.put("file_ID", file_id);
        rowContent.put("file_name", name);
        rowContent.put("location", location);

        TableId tableId = TableId.of(datasetName, tableName);
        InsertAllResponse response =
                bigquery.insertAll(
                        InsertAllRequest.newBuilder(tableId)
                                // More rows can be added in the same RPC by invoking .addRow() on the builder.
                                // You can also supply optional unique row keys to support de-duplication
                                // scenarios.
                                .addRow(rowContent)
                                .build());
        if (response.hasErrors()) {

            // If any of the insertions failed, this lets you inspect the errors
            for (Map.Entry<Long, List<BigQueryError>> entry : response.getInsertErrors().entrySet()) {
                System.out.println("Response error: \n" + entry.getValue());
            }
            throw new Exception("Insert Exception");
        }
        System.out.println("Rows successfully inserted into table");

        return res;
    }

    public String add_user_file_mapping(int userId, int fileId) throws Exception {
//        createConnection();
        bigquery = BigQueryOptions.newBuilder().setProjectId("marine-physics-349505")
                .build().getService();
//        ArrayList<File> Allfiles=new ArrayList<>();
//        Allfiles=getFiles();
//        int count= Allfiles.size();
//        int file_id=count+1;
//        System.out.println(file_id);

        String datasetName="my_Drive";
        String tableName="user_files_table";

        String msg;

//        File res=new File(file_id,name,location);

        BigQuery bq=BigQueryOptions.getDefaultInstance().getService();

        Map<String, Object> rowContent = new HashMap<>();


        rowContent.put("user_id", userId);
        rowContent.put("file_id", fileId);
//        rowContent.put("location", location);

        TableId tableId = TableId.of(datasetName, tableName);
        InsertAllResponse response =
                bigquery.insertAll(
                        InsertAllRequest.newBuilder(tableId)
                                // More rows can be added in the same RPC by invoking .addRow() on the builder.
                                // You can also supply optional unique row keys to support de-duplication
                                // scenarios.
                                .addRow(rowContent)
                                .build());
        if (response.hasErrors()) {

            // If any of the insertions failed, this lets you inspect the errors
            for (Map.Entry<Long, List<BigQueryError>> entry : response.getInsertErrors().entrySet()) {
                System.out.println("Response error: \n" + entry.getValue());
            }
            return "Insert failed at user_files_table";
//            throw new Exception("Insert Exception");

        }
        System.out.println("Rows successfully inserted into table");

        return "Rows successfully inserted in user_files_table";
    }

//    public int addUser(String name, String email, String password ) throws Exception {
//        ArrayList<User> users;
//        users=getUsers();
//        int count= users.size();
//        int user_id=count+1;
////        for(User u: users){
////            if(u.getUser_id()==user_id){
////                throw new Exception("User ID "+user_id+"already present");
////            }
////        }
//        System.out.println(user_id);
//
//
//        final String insert_user_query = "insert into `marine-physics-349505.my_Drive.User_table` values ("+user_id+",\""+name+"\",\""+email+"\",\""+password+"\")";
//        System.out.println(insert_user_query);
////        bigquery.query(QueryJobConfiguration.of(insert_user_query));
//        TableResult result =runQuery(insert_user_query);
//        System.out.println(insert_user_query);
//        return user_id;
//    }
    public ArrayList<User> getUsers() throws Exception {
        createConnection();
        final String getUsers = "SELECT * FROM `marine-physics-349505.my_Drive.User_table` LIMIT 1000";
        TableResult result =runQuery(getUsers);
        ArrayList<User> users = new ArrayList<>();
        for (FieldValueList row : result.iterateAll()) {
            // We can use the `get` method along with the column
            // name to get the corresponding row entry
            String user_name = row.get("user_name").getStringValue();
            int userid = Integer.parseInt(row.get("user_id").getValue().toString());
            String email = row.get("email").getStringValue();
            String password = row.get("password").getStringValue();
            User user=new User(userid,user_name,email,password);
            users.add(user);
            System.out.println(user.toString());
//            System.out.print(userid+"    "+user_name);
        }
        return users;
    }

    public ArrayList<File> getFiles() throws Exception {
        createConnection();
        String getFiles = "SELECT * FROM `marine-physics-349505.my_Drive.files_table` LIMIT 1000";
        TableResult result1 =runQuery(getFiles);
        ArrayList<File> files = new ArrayList<>();

        for (FieldValueList row : result1.iterateAll()) {
            // We can use the `get` method along with the column
            // name to get the corresponding row entry
//            System.out.print(row.toString());
            String file_name = row.get("file_name").getStringValue();
            int fileid = Integer.parseInt(row.get("file_ID").getValue().toString());
            String location = row.get("location").getStringValue();
            File file =new File(fileid,file_name,location);
            files.add(file);
            System.out.println(file.toString());
        }
        return files;
    }

    public File getFileDetails(int file_id) throws Exception {
        createConnection();
        final String query = "select * FROM `marine-physics-349505.my_Drive.files_table` where file_id="+file_id;
        TableResult result =runQuery(query);
        FieldValueList row= result.iterateAll().iterator().next();
        String file_name = row.get("file_name").getStringValue();
        int fileid = Integer.parseInt(row.get("file_ID").getValue().toString());
        String location = row.get("location").getStringValue();

        File file= new File(fileid,file_name,location);
        return file;

    }


    public ArrayList<File> getFileByUser(int Id) throws Exception {
        createConnection();
        ArrayList<User> users=new ArrayList<User>();
//        ArrayList<File> Allfiles=getFiles();
        ArrayList<File> files_res=new ArrayList<>();
        ArrayList<File> All_files=getFiles();
        final String getUsers = "SELECT user.user_id, user.user_name, file.file_ID from (select user_id, user_name from `marine-physics-349505.my_Drive.User_table`) user LEFT JOIN(select user_id,file_ID from `marine-physics-349505.my_Drive.user_files_table`) file ON user.user_id= file.user_id";
        TableResult result =runQuery(getUsers);

        ArrayList<Integer> f_id= new ArrayList<>();
        for (FieldValueList row : result.iterateAll()) {
            // We can use the `get` method along with the column
            // name to get the corresponding row entry

//            System.out.println(row.toString());
            String user_name;
            int user_id;
            int file_id;
            if(row.get("file_ID").getValue()!=null){
                user_name = row.get("user_name").getStringValue();
                user_id = Integer.parseInt(row.get("user_id").getValue().toString());
                file_id = Integer.parseInt(row.get("file_ID").getValue().toString());
                if(user_id==Id) {
                    f_id.add(file_id);
                }
            }

        }
        System.out.println(f_id.toString());
        for(int fileid : f_id){
            files_res.add(getFileDetails(fileid));
        }

        return files_res;
    }

}
