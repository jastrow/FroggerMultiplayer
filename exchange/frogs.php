<?PHP

/*
   DROP TABLE gamedata;
   CREATE TABLE gamedata (
      name TEXT,
      id INT,
      typ TEXT,
      facing TEXT,
      xPosition INT,
      yPosition INT,
      time INT,
      length INT,
      leftToRight ENUM("t","f"),
      treeRide INT,

      playerip TEXT,
      datum DATETIME);

*/

// DB Connection
$mysqli = new mysqli('localhost', 'xxxxxxx', 'xxxxxxxxx', 'xxxxxxxx');
if ($mysqli->connect_error) {
    die('Connect Error (' . $mysqli->connect_errno . ') '. $mysqli->connect_error);
}

// IP-Address
$ip = $_SERVER['REMOTE_ADDR'];


if(isset($_POST['name'])) {

   $check = checkData($_POST);
   if(!$check) { die("wrong data"); }

   $keys = $vals = array();
   foreach($check as $key => $dat) {
      $keys[] = $key;
      $vals[] = $dat;
   }

   $keys[] = 'ip'; $vals[] = "'".$ip."'";
   $keys[] = 'datum'; $vals[] = "'".date("Y-m-d H:i:s")."'";

   $fields = implode(",",$keys);
   $daten = implode(",",$vals);
   $sql = "INSERT INTO gamedata (".$fields.") VALUES (".$daten.")";
   $res = $mysqli->query($sql);

   if($mysqli->error) { die( $mysqli->error.' '.$sql ); }

// Ausgabe der letzten Frogs nicht sich selbst
} else {
   $res = $mysqli->query("SELECT * FROM gamedata WHERE playerip != '$ip' ORDER BY datum DESC LIMIT 1");
   if($mysqli->error) { die( $mysqli->error ); }

   $row = $res->fetch_array(MYSQL_ASSOC);
   echo '{"ist":'.json_encode($row).'}';
}


function checkData($data) {
   $fields = array("name","id","typ","facing","xPosition","yPosition","time","length","leftToRight","treeRide");

   foreach($data as $key => $dat) {
      $data[$key] = trim($dat);
      $i = array_search($key,$fields);
      if($i !== false) {
         unset($fields[$i]);
      }
   }
   if(count($fields) != 0) { die("wrong dataset"); }

   if(!trim($data['name'])) { return false; }
   if(!trim($data['id']) OR !ctype_digit($data['id'])) { return false; }
   if(!trim($data['xPosition']) OR !ctype_digit($data['xPosition'])) { return false; }
   if(!trim($data['yPosition']) OR !ctype_digit($data['yPosition'])) { return false; }

   foreach($data as $key => $dat) {
      $data[$key] = "'".addslashes($dat)."'";
   }

   return $data;

}

?>