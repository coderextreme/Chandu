<?php
require_once 'HTTP/Request2.php';

function isValidJson($strJson) { 
    json_decode($strJson); 
    return (json_last_error() === JSON_ERROR_NONE); 
}

$json_params = file_get_contents("php://input");

if (strlen($json_params) > 0 && isValidJSON($json_params))
  $decoded_params = json_decode($json_params, true);
else
  $decoded_params = json_decode('{"params":{"search":""}}', true);

$request = new HTTP_Request2('https://www.googleapis.com/youtube/v3/search?part=snippet&order=viewCount&q='."crowdfunding+crowdsourcing+video+".$decoded_params{"params"}{"search"}.'&type=video&videoDefinition=high&key=AIzaSyC_ptDFs3eiuO9KlXbd-UxjLxEDvIfxZ3o', HTTP_Request2::METHOD_GET);
try {
    $response = $request->send();
    if (200 == $response->getStatus()) {
        $json = $response->getBody();
	$newitems = array();
	foreach (json_decode($json, true){"items"} as $item) {
		$newitem = array();
		$newitem{"ID"} = $item{"id"}{"videoId"};
		$newitem{"Link"} = 'https://www.youtube.com/embed/'.$item{"id"}{"videoId"};
		$newitem{"Date/Time"} = $item{"snippet"}{"publishedAt"};
		$newitem{"Title"} = $item{"snippet"}{"title"};
		$newitem{"Description"} = $item{"snippet"}{"description"};
		$newitem{"Image"} = $item{"snippet"}{"thumbnails"}{"default"}{"url"};
		array_push($newitems, $newitem);
	}
	echo json_encode($newitems);
    } else {
        echo 'Unexpected HTTP status: ' . $response->getStatus() . ' ' .
             $response->getReasonPhrase();
    }
} catch (HTTP_Request2_Exception $e) {
    echo 'Error: ' . $e->getMessage();
}
?>
