<?php
class Database{
 
    // specify your own database credentials
    private $host = "10.99.103.2";
    private $db_name = "packages";
    private $username = "user";
    private $password = "user";
    public $conn;
 
    // get the database connection
    public function getConnection(){
        $this->conn = null;
        try{
            $this->conn = new PDO("mysql:host=" . $this->host . ";dbname=" . $this->db_name, $this->username, $this->password);
            $this->conn->exec("set names utf8");
        }catch(PDOException $exception){
            echo "Connection error: " . $exception->getMessage();
			die();
        }
 
        return $this->conn;
    }
}
?>