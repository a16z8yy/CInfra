
CREATE DATABASE cloud DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

create table user_mst(
    user_id INT PRIMARY KEY auto_increment,
	user_name CHAR (20),
	password CHAR (20),
	delete_flag CHAR (1),
	create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8

create table server_mst(
	server_id INT PRIMARY KEY auto_increment,
	server_name CHAR (20),
	server_status CHAR (1),
	server_size CHAR (20),
	delete_flag CHAR (1),
	create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8

create table instance_mst(
	instance_id INT PRIMARY KEY auto_increment,
	instance_name CHAR (20),
	ip_address CHAR (20),
	instance_status CHAR (1),
	vcpus CHAR (20),
	ram CHAR (20),
	size CHAR (20),
	network_bridge CHAR (20),
	os_type CHAR (20),
	delete_flag CHAR (1),
	create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8