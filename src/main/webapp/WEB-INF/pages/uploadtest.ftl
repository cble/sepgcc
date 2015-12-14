<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>jQuery File Upload Example</title>
    <script src="js/jquery.1.9.1.min.js"></script>

    <script src="js/vendor/jquery.ui.widget.js"></script>
    <script src="js/jquery.iframe-transport.js"></script>
    <script src="js/jquery.fileupload.js"></script>

    <!-- bootstrap just to have good looking page -->
    <script src="js/bootstrap.min.js"></script>
    <link href="css/bootstrap.min.css" type="text/css" rel="stylesheet" />

    <!-- we code these -->
    <script src="js/myuploadfunction.js"></script>
</head>

<body>
<h1>Spring MVC - jQuery File Upload</h1>
<div style="width:500px;padding:20px">

    <label class="btn btn-primary" for="fileupload">
        <input id="fileupload" type="file" name="files[]" data-url="uploadFile" style="display:none;">
        Button Text Here
    </label>

    <div id="progress" class="progress">
        <div class="progress-bar" style="width: 0%;"></div>
    </div>

    <table id="uploaded-files" class="table">
        <tr>
            <th>File Name</th>
            <th>File Size</th>
            <th>File Type</th>
            <th>Download</th>
        </tr>
    </table>

</div>
</body>
</html>
