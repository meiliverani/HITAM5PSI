# HITAM5PSI
Java Based Android Application - UTS Sistem Rekayasa 5PSI - Kelompok Hitam
![alt text](https://5psihitamsr.000webhostapp.com/logo.png)
1. Licen 1831076
2. Meiliverani Erline 1831126
3. Melvy Devalia 1831158
4. Jodi Saputra Dermawan Saragih 1831119

# DATABASE ANALYSIS
This application is using 1 database named "db_android" with 3 tables within which are "tb_siswa", "tb_paket", and "tb_sekolah".
Each table has 1 primary key which is ID. While "tb_siswa" has 2 foreign key which come from each 2 other tables.
The diagram of the db's schema is drawn as below:
![alt text](https://5psihitamsr.000webhostapp.com/db-schema.png)


# FUNCTION ANALYSIS
This application basically has all CRUD functions in it.
Specifically named as:
1. addStudent(); // to CREATE a new record to tb_siswa
2. showEmployee(); // to RETRIEVE and READ all records in tb_siswa and show it in a ListView
3. showEmployee(String json) // to RETRIEVE and READ a record of tb_siswa as requester by passed json to this function which represent 1 student id
4. updateEmployee(); // to UPDATE a record of tb_siswa
5. deleteEmployee(); // to DELETE a record of tb_siswa

# HOSTING SITE
https://5psihitamsr.000webhostapp.com/

# METHOD
PHP is implemented in this application using GET and POST method to integrate between Java and MySQL
All used PHP files is specifically mention below:
1. https://5psihitamsr.000webhostapp.com/koneksi.php // to connect with MySQL database
2. https://5psihitamsr.000webhostapp.com/tampilSemuaSiswa.php // to show all records in tb_siswa (name and id only)
3. https://5psihitamsr.000webhostapp.com/tampilSiswa.php // with "id" required parameter, to show a specific data of a student by id
4. https://5psihitamsr.000webhostapp.com/tambahSiswa.php // to add record to tb_siswa
5. https://5psihitamsr.000webhostapp.com/getPackages.php // to retrieve all records in tb_paket
6. https://5psihitamsr.000webhostapp.com/getSchools.php // to retrieve all records in tb_sekolah
7. https://5psihitamsr.000webhostapp.com/updateSiswa.php // to update record of tb_siswa
8. https://5psihitamsr.000webhostapp.com/hapusSiswa.php // to delete record of tb_siswa

