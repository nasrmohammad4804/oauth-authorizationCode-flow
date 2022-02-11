<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Get Employees</title>
    <style>
        th,td{
            border: 1px solid black;
        }
    </style>
</head>
<body>
<h3 style="color: red;">show all employee</h3>
    <table>
        <tr>
            <th>employee id</th>
            <th>employee name</th>
        </tr>
<c:forEach var="employee" items="${employees}">
    <tr>
        <td>${employee.empId}</td>
        <td>${employee.name}</td>
    </tr>
</c:forEach>
    </table>
</body>
</html>