
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Tilmeld dig her:</h1>


<form action="signup" method="post">
    <label for="username">Indtast Email: </label>
    <br/>
    <input type="text" id="username" name="username"/>
    <br/>
    <label for="password">Indtast Kodeord: </label>
    <br/>
    <input type="password" id="password" name="password"/>
    <br/>
    <label for="name">Indtast Fulde navn: </label>
    <br/>
    <input type="text" id="name" name="name"/>
    <br/>

    <input type="submit"  value="Opret mig som bruger"/>
</form>


</body>
</html>
