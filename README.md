# oauth-authorizationCode-flow
implement custom oauth-authorizationCode-flow in spring boot and this flow is very seure 
for when client id and secret send to authorization server then return authorization code
and we send authorization code and encoding of client id and secret in backend to authorization server
then return token as response and on server side send request to resource server with token 
if token is valid and require scope then access to data of resource server
this flow is very secure because for get resource need to token and this token dont transfer on client 
then anyone cant access to token but you think with client id and authorization code will get to token and then access to resource
but although client id transer on client layer and server layer and can access then but you dont access to secret
because this key store on server side and cant to access them then when you must send client id and authorization code
and secert then because dont have secret then dont cant to access token and then dont cant to data from resource server
