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

## RESULTS OF ADDING ASYNC endpoint
Personal testing notes.
1. Most results of tests revealed average of 10% improvement and far lower base and far lower highest amount of time. 
Even though on average the results only different from 10 to 30 sec. Testing too is apache benchmark.
2. Attached file is far smaller version for less dl size of project bundle.
3. Results are not conclusive and need more testing.

Results of 2 api comparison of benchmarks. Test file size is 23 Mbs

Regular rest api:
$ ab -n 100 -c 100 -p "C:\Users\pauli\Downloads\zuck.txt" -T 'multipart/form-data;boundary=1234567890' http://localhost:8080/upload/
This is ApacheBench, Version 2.3 <$Revision: 1901567 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Server Software:
Server Hostname:        localhost
Server Port:            8080

Document Path:          /upload/
Document Length:        193527 bytes

Concurrency Level:      100
Time taken for tests:   5.401 seconds
Complete requests:      100
Failed requests:        0
Total transferred:      19371300 bytes
Total body sent:        2376360700
HTML transferred:       19352700 bytes
Requests per second:    18.51 [#/sec] (mean)
Time per request:       5401.065 [ms] (mean)
Time per request:       54.011 [ms] (mean, across all concurrent requests)
Transfer rate:          3502.51 [Kbytes/sec] received
429667.99 kb/s sent
433170.50 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    1   0.7      0       4
Processing:  1192 2840 800.4   2931    4263
Waiting:      261 2652 838.4   2771    4084
Total:       1192 2840 800.4   2931    4263
WARNING: The median and mean for the initial connection time are not within a normal deviation
These results are probably not that reliable.

Percentage of the requests served within a certain time (ms)
50%   2931
66%   3314
75%   3476
80%   3557
90%   3826
95%   4005
98%   4171
99%   4263
100%   4263 (longest request)

$ ab -n 100 -c 100 -p "C:\Users\pauli\Downloads\zuck.txt" -T 'multipart/form-data;boundary=1234567890' http://localhost:8080/async/upload/
This is ApacheBench, Version 2.3 <$Revision: 1901567 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient).....done


Server Software:
Server Hostname:        localhost
Server Port:            8080

Document Path:          /async/upload/
Document Length:        193527 bytes

Async api:

Concurrency Level:      100
Time taken for tests:   4.727 seconds
Complete requests:      100
Failed requests:        0
Total transferred:      19371300 bytes
Total body sent:        2376361300
HTML transferred:       19352700 bytes
Requests per second:    21.15 [#/sec] (mean)
Time per request:       4727.205 [ms] (mean)
Time per request:       47.272 [ms] (mean, across all concurrent requests)
Transfer rate:          4001.79 [Kbytes/sec] received
490917.01 kb/s sent
494918.80 kb/s total

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    1   2.0      0      19
Processing:  1005 2631 826.6   2912    3815
Waiting:      261 2169 770.5   2275    3550
Total:       1006 2632 826.8   2912    3816

Percentage of the requests served within a certain time (ms)
50%   2912
66%   3260
75%   3335
80%   3415
90%   3521
95%   3600
98%   3701
99%   3816
100%   3816 (longest request)


