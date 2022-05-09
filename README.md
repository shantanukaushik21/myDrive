# myDrive
JAVA project to upload data to Google cloud storage.

Read the Demo.txt file to get details about Database, cloud storage buckets etc.

Clone and run the project in the local host. 

Things covered:

1. Register users to the Drive
2. User Authentication to upload or download files.
3. Proper IAM policies are used in the Service Accounts.
4. Authorised users can upload files.
5. The Authorised user sees all the files he uploaded.
6. user and file data are stored in google bigquery(GCP).

Future Scope:

1. JWT authentication can be included to achieve statelessness.
2. Database can be Migrated to Cloud SQL for better computation. I selected Google BigQuery due to time constraints.
3. files can be hosted online and connected to load balancer to scale in future.

NOTE: I have not shared the Service Account Key on github due to security reasons. Calling Google API without the key will give errors. As the files are to be uploaded to the cloud, I have restricted the file size to 10KB to minimize the cost.
