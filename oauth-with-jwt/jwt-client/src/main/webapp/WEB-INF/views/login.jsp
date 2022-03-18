<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Please sign in</title>
    <style>
        body{
            background-color: #3e3e3e !important;

        }
        input{
            position: static;
        }
        #myParrent{

            display: block;
            border: 1.5px solid black;
            border-radius: 3px;
            background-color: aliceblue;
            max-width: 350px;


        }

    </style>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
    <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous"/>

    <link rel="stylesheet" type="text/css"
          href="https://bootswatch.com/4/cerulean/bootstrap.min.css" />
    <link rel="stylesheet" type="text/css"
          href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css" />
    <script src="https://use.fontawesome.com/e344ad35b2.js"></script>
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>

<div class="container" >
    <form class="form-signin" method="post" action="/login" id="myParrent">
        <h2 class="form-signin-heading">Please sign in</h2>

        <c:choose>

            <c:when test="${param.logout != null}">
                <div class="text-warning">
                    You have been logged out.
                </div>
            </c:when>



        </c:choose>
        <p>
            <label for="username" class="sr-only">Username</label>
            <input type="text" id="username" name="username" class="form-control" placeholder="Username" required
                   autofocus>
        </p>
        <p>
            <label for="password" class="sr-only">Password</label>
            <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
        </p>

        <div style="margin-bottom: 6px" class="g-recaptcha"
             data-sitekey="6Lf8y50dAAAAAArhVxDi9OveuI8eRGm6QpbfkIFL">
        </div>

        <p   style="display: flex;flex-direction: row">
        <div   style="display: inline">Remember me</div>

        <div  style="display: inline-block;"><input style="width: 16px;height: 14px" type="checkbox" id="remember-me"  name="remember-me" class="form-control" >
        </div>
        </p>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div id="google-login" class="alert-danger" style="text-align: center;margin-top: 20px;opacity: 1;padding: 5px 0"><a style="text-decoration: none;color: black !important;"
            href="http://localhost:8081/oauth/authorize?response_type=code&client_id=client2">Login with oauth2</a></div>
    </form>
</div>

</body>
</html>
