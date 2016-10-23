<!DOCTYPE html>
<!-- saved from url=(0048)http://www.thomashardy.me.uk/demos/localstorage/ -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Badge System</title>

<meta name="viewport" content="width=device-width"> 
 
<link rel="shortcut icon" href="http://www.thomashardy.me.uk/demos/localstorage/images/favicon.ico">
<link type="text/css" rel="stylesheet" href="./res/style.css">
<link href="./res/css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="./res/JsBarcode.all.min.js"></script>
<script type="text/javascript" src="./res/jquery.min.js.download"></script>
</head>
<body>
<div id="wrap">
<header>
	<h1>Badge System</h1>
	<div class="clear"></div>
</header>
<section id="demo">
<div id="influads_block" class="influads_block"> </div>
		<p>Preview barecode</p>
        <svg id="barcode_display"></svg>
		<form id="localStorageTest" method="post" action="#">
			<label>First Name:</label>
			<input type="text" name="firstname" id="firstname" value="<?php echo $_GET['fn']; ?>" disabled="true">

            <label>Last Name:</label>
			<input type="text" name="lastname" id="lastname" value="<?php echo $_GET['ln']; ?>" disabled="true">

			<label>Barcode:</label>
			<input type="text" name="barcode" id="barcode" value="">
			 
			<input type="submit" class="demo-button" value="Submit">
			<input type="button" class="demo-button" id="cancel" value="Cancel">
        </form>
</section>
</div>




<script type="text/javascript">
$(document).ready(function () {
    function init() {
		if (localStorage["barcode-<?php echo $_GET['pid']; ?>"]) {
            $('#barcode').val(localStorage["barcode-<?php echo $_GET['pid']; ?>"]);
        }
        JsBarcode("#barcode_display").init();
        JsBarcode("#barcode_display", $('#barcode').val());
    }
	init();
});

$('#barcode').keyup(function () {
    JsBarcode("#barcode_display", $('#barcode').val());
});
	
$('#localStorageTest').submit(function() {
    localStorage["barcode-<?php echo $_GET['pid']; ?>"] = $('#barcode').val();
    top.postMessage("SUBMIT_CLICKED", "*");
});
$('#cancel').click(function() {
    top.postMessage("CANCEL_CLICKED", "*");
});

</script>
</body></html>