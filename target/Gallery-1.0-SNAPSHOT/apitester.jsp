<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Api tester</title>

</head>
<body>
<h1>
    Api tester
</h1>

<div id="container"></div>
<div>
    <button onclick="sendGet()">GET</button>
    <button onclick="sendPost()">POST</button>
    <button onclick="sendPut()">PUT</button>
    <button onclick="sendDelete()">DELETE</button>
</div>

<script>
    function sendGet() {
        fetch("api/picture").then(response => response.text()).then(txt => container.innerText = txt)
    }

    function sendPost() {
        fetch("api/picture", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({x: 10})
        }).then(response => response.text()).then(txt => container.innerText = txt)
    }

    function sendPut() {
        fetch("api/picture", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({x: 10})
        }).then(response => response.text()).then(txt => container.innerText = txt)
    }

    function sendDelete() {
        fetch("api/picture", {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({x: 10})
        }).then(response => response.text()).then(txt => container.innerText = txt)
    }
</script>
</body>
</html>
