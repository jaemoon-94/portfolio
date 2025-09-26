<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>알림</title>
    <script>
        window.onload = function() {
            alert('${notice_msg}');
            location.href = '${notice_url}';
        }
    </script>
</head>
<body>
</body>
</html>