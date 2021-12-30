CREATE USER 'deployment'@'%' IDENTIFIED BY 'deployment';
GRANT ALL PRIVILEGES ON *.* TO 'deployment'@'%';
FLUSH PRIVILEGES;