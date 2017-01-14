<?php

$mysqli = new mysqli('localhost', 'XXXXX', 'XXXXX', 'XXXXXX');
$myArray = array();

if ($mysqli->connect_error) {
    die('Connect Error (' . $mysqli->connect_errno . ') '. $mysqli->connect_error);
}


if(isset($_POST['name'])) {

   if(!ctype_digit($_POST['zeit'])) {
      die("Wrong timeformat");
   }
   if(!trim($_POST['name'])) {
      die("No Playername");
   }

   $check = $mysqli->query("INSERT INTO highscore (name,zeit,created) VALUES (
      '".addslashes(str_replace("|","",$_POST['name']))."',
      '".$_POST['zeit']."',
      '".time()."'
   )");


} else if(isset($_GET["reset"]) OR isset($_GET["clear"])) {

   $mysqli->query("DELETE FROM highscore WHERE 1");
   $mysqli->query("INSERT INTO highscore (name,zeit,created) VALUES ('Max Muster','10000','1484321881')");
   $mysqli->query("INSERT INTO highscore (name,zeit,created) VALUES ('Maya Hummelich','20000','1484321881')");
   $mysqli->query("INSERT INTO highscore (name,zeit,created) VALUES ('Fiona','30000','1484321881')");

   echo "Frogger Highscore DB reseted";


} else {

   $limit = " LIMIT 3";
   if(isset($_GET['showall'])) {
      $limit = "";
      echo "alle einträge: <br>";
   }


   if($result = $mysqli->query("SELECT * FROM highscore ORDER BY zeit ASC".$limit)) {
      //print_r($result);
      $out = "";
       while($row = $result->fetch_array(MYSQL_ASSOC)) {
               $myArray[] = $row;
               $out .= implode("|",$row)."\r\n";
       }
       //echo '{"ist":'.json_encode($myArray).'}';
       echo $out;
   }


}


$mysqli->close();
?>