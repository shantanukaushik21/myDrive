# myDrive
JAVA project to upload data to Google cloud storage.

Clone and run the project in the local host. 

Things covered:
1. Register users to the Drive
2. User Authentication to upload or manupulate files.
3. Proper IAM policies are used in the Service Accounts.
4. Authorised user can upload file.
5. Authorised user see all the files he uploaded.
6. user and file details are also stored in google bigquery(GCP).

Future Scope:
1) JWT authentication can be included to achive statelessness.
2) Database can be Migrated to Cloud SQL for better computation. I selected Google bigquery due to time constraint.
3) files can we hosted online and connected to loadbalancer to scale in future.


NOTE: I have not shared the Service Account Key on github due to security reasons. Calling Google API's without the key will give errors.
As, the files are to be uploaded to cloud as have ristricted the files size to 10KB to minimize the cost.
