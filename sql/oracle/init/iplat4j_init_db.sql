--create DS_IPLAT01 tablespace
-- this script is used for viewing the tablespace of database 
--    select tablespace_name, file_id, file_name, round(bytes/(1024*1024),0) total_space
--    from dba_data_files order by tablespace_name;


CREATE TABLESPACE DS_IPLAT4J01 DATAFILE
'/oracledb/oradata/bslife/DS_IPLAT4J01.dbf' SIZE 100M AUTOEXTEND ON NEXT 10M MAXSIZE UNLIMITED
LOGGING
ONLINE
PERMANENT
EXTENT MANAGEMENT LOCAL AUTOALLOCATE
BLOCKSIZE 8K
SEGMENT SPACE MANAGEMENT AUTO
FLASHBACK ON;

--create user iplat4j
CREATE USER iplat4j IDENTIFIED BY iplat4j DEFAULT TABLESPACE DS_IPLAT4J01;
GRANT CONNECT,RESOURCE,UNLIMITED TABLESPACE TO iplat4j identified by iplat4j;



commit;
  
  
 