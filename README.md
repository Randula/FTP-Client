FTP-Client
==========

Sample FTP client project for transfer binary and text file.


FTP Server - apache-ftpserver-1.0.6


Configure FTP Server
--------------------
1) Extract the given apache-ftpserver-1.0.6.zip
2) Go to <Your-dir>/apache-ftpserver-1.0.6/res/conf
3) Add username and password as available configurations in users.properties (Currently I have configured the username 'local' )

Start FTP server
----------------
1) Go to apache-ftpserver-1.0.6/bin/
2) Run ./ftpd.sh res/conf/ftpd-typical.xml
