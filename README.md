# fileUpload
# README

A. Adding a different archiving method:
Would suggest creating a switch class to take desired compression method that is received from front end or other
service and take files provided from the api and compress them the way is defined and produce compressed files. Regarding my code,
would just need to create 7z archiving method and implement it in the factory class.

B. Significant request increase: 
Would need to create load balancer to use many instances of microservice if its possible use Openshift or other,
in the service like this one would continue on the path of async requests/responses, create a task manager,
thread pools and so on, introduce request limits per file size or frequency of requests from a certain IP.
But it also depends on the hardware not just software.

C. Allow 1GB max file size: 
Use streaming api to handle large request and upload sizes, i have not myself had to deal with large uploads, but
from my research this would be a good library:  Apache Commons File Upload Library. With current implementation i've
tested via postman with 990 mb text file it compressed it to 3.35 mb and it overall took 10.5 s. Since i dont have a
reference time i would like to know what would be the goal?
