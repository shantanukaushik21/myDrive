# myDrive
JAVA project to upload data to Google cloud storage.

Clone and run the project in the local host. 

Things covered:

Register users to the Drive
User Authentication to upload or manipulate files.
Proper IAM policies are used in the Service Accounts.
Authorised users can upload files.
The Authorised user sees all the files he uploaded.
user and file details are also stored in google bigquery(GCP).
Future Scope:

JWT authentication can be included to achieve statelessness.
Database can be Migrated to Cloud SQL for better computation. I selected Google BigQuery due to time constraints.
files can be hosted online and connected to load balancer to scale in future.
NOTE: I have not shared the Service Account Key on github due to security reasons. Calling Google API without the key will give errors. As the files are to be uploaded to the cloud, I have restricted the file size to 10KB to minimize the cost.
