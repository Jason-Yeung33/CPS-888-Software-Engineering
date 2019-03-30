# CPS-888-Software-Engineering - Instant Messenger Project

Quick note: Please ignore the "CPS-888-Software-Engineering-master" folder as it's an older duplicate folder. It was just accidentally uploaded when commited to the github. Use the "Instant messenger" main folder.


## Welcome to our instant messenger application "LetsTalk." Please read on to understand how to set up the database and run the application for your own use.

Steps to run this application as intended:

1) Since we implemented a local database for this project, you would have to install the XAMPP control panel first. This can be installed here: https://www.apachefriends.org/index.html

2) Once installed, open the control panel and turn on the apache and mysql ports by pressing the start buttons.

Troubleshooting tips: If the default ports are already in use, feel free to change them in the "Apache(httpd.conf)" and/or      "Apache(httpd-ssl.conf)" files found under apache's config.

3) Once all the ports above are opened, click on the mysql admin button to access your local phpmyadmin on your browser. If that doesn't work, manually input it into your browser (localhost/phpmyadmin/ or localhost:8080/phpmyadmin as an example if you used port 8080 instead of the default 80).

4) Once you're in the local phpmyadmin for your pc, create a new database, which can be done on the left. Name it "Users". After creating the database, you can import our tables into the database which can be found in the project client folder (users.sql). This should conclude the database setup.

5) Start/Open the instant messenger server by first running the server project or clicking on the Server jar found in the dist folder.

6) Now that the server is open and started, the instant messenger should function as intended. Open the instant messenger jar in the client dist folder or run the code in an ide like netbeans. You will be able to access all the functions once the database and server are running.

7) You will be greeted with a home screen that will allow you to choose whether you want to register for a new account or log in to the messenger. If you choose to register, you'll be sent to a registration page, which will prompt you to type a username and password. It will check the database if the username already exists and if it does, it will tell you to choose a new one until a the username is checked is not in the database. Once successful, it will send you to the client chat to log in.

8) If you choose log in from the home screen, you will be immediately sent to the client chat room where you can log in as a user or log in as a guest. You can decide to user either as both have the same functions for the chat.

9) Once logged in, you can send messages to anyone online in the chatroom. You can see who's online from the users online list to the right of the client. You can also send private messages to people as whispers by using the "username>your message" command. Only the person you send the message to will see it as "[WHISPER] Your message."

10) Once you're done with using the chat room, you can press the log out button or close the window to disconnect from the server. 


This concludes our explanation on how to run our software! We all hope you enjoy our LetsTalk application. The best place to talk with your friends.
