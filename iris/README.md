## iris 📊

### what
Using the docker-in-lambda work (the ultimate in functions-as-a-service, serverless computing, and virtualization and containerization), get a container running and sending an email on how my portfolio is doing for the day. Dependency on markets-etl and treats AWS Athena as its database.

Lots of interesting work done here, not just on the infrastructure side, but also writing a bash wrapper to treat the aws cli like a psql client: <https://github.com/skilbjo/iris/blob/master/src/athena>

See <https://github.com/skilbjo/iris> for more

<img src="dev-resources/img/iris.png" alt="hi" width="900"/>

### Lambda Execution environment

##### CPU info

```
$ cat /proc/cpuinfo
processor   : 0
vendor_id   : GenuineIntel
cpu family  : 6
model       : 62
model name  : Intel(R) Xeon(R) CPU E5-2680 v2 @ 2.80GHz
stepping    : 4
microcode   : 0x416
cpu MHz     : 2800.110
cache size  : 25600 KB
physical id : 0
siblings    : 2
core id     : 0
cpu cores   : 1
apicid      : 0
initial apicid  : 0
fpu     : yes
fpu_exception   : yes
cpuid level : 13
wp      : yes
flags       : fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush mmx fxsr sse sse2 ht syscall nx rdtscp lm constant_tsc rep_good nopl xtopology eagerfpu pni pclmulqdq ssse3 cx16 pcid sse4_1 sse4_2 x2apic popcnt tsc_deadline_timer aes xsave avx f16c rdrand hypervisor lahf_lm xsaveopt fsgsbase smep erms
bogomips    : 5600.22
clflush size    : 64
cache_alignment : 64
address sizes   : 46 bits physical, 48 bits virtual
power management:

processor   : 1
vendor_id   : GenuineIntel
```


##### Binaries

```
/bin:
total 5060
525366 dr-xr-xr-x  2 root root   4096 Nov  3 10:28 .
524919 drwxr-xr-x 21 root root   4096 Nov 12 18:07 ..
525384 -rwxr-xr-x  1 root root  28448 Mar 17  2014 arch
525393 lrwxrwxrwx  1 root root      4 Nov  3 10:25 awk -> gawk
525440 -rwxr-xr-x  1 root root  28128 Mar 17  2014 basename
525379 -rwxr-xr-x  1 root root 898032 Sep 25 17:25 bash
525417 -rwxr-xr-x  1 root root  48128 Mar 17  2014 cat
525388 -rwxr-xr-x  1 root root  57216 Mar 17  2014 chgrp
525394 -rwxr-xr-x  1 root root  52992 Mar 17  2014 chmod
525410 -rwxr-xr-x  1 root root  58432 Mar 17  2014 chown
525423 -rwxr-xr-x  1 root root 128224 Mar 17  2014 cp
525418 -rwxr-xr-x  1 root root 129832 Nov 19  2012 cpio
525370 -rwxr-xr-x  1 root root  45152 Mar 17  2014 cut
525436 -rwxr-xr-x  1 root root  59584 Mar 17  2014 date
525374 -rwxr-xr-x  1 root root  57000 Mar 17  2014 dd
525421 -rwxr-xr-x  1 root root  94992 Mar 17  2014 df
525400 -rwxr-xr-x  1 root root  41968 Jul  9 10:46 dmesg
525414 -rwxr-xr-x  1 root root  27904 Mar 17  2014 echo
525381 -rwxr-xr-x  1 root root  28128 Mar 17  2014 env
525438 -rwxr-xr-x  1 root root 234512 Jan 16  2011 find
525387 -rwxr-xr-x  1 root root 373632 Oct 14  2012 gawk
525431 -rwxr-xr-x  1 root root 106264 Sep  4  2013 grep
525369 lrwxrwxrwx  1 root root      3 Nov  3 10:25 gtar -> tar
525382 -rwxr-xr-x  1 root root   2253 Mar 20  2014 gunzip
525398 -rwxr-xr-x  1 root root  93352 Mar 20  2014 gzip
525413 -rwxr-xr-x  1 root root  13712 Jul  7  2012 hostname
525409 -rwxr-xr-x  1 root root  25536 Mar 17  2014 link
525412 -rwxr-xr-x  1 root root  53352 Mar 17  2014 ln
525375 -rwxr-xr-x  1 root root 113440 Mar 17  2014 ls
525420 -rwxr-xr-x  1 root root  48832 Mar 17  2014 mkdir
525395 -rwxr-xr-x  1 root root  32256 Mar 17  2014 mknod
525426 -rwxr-xr-x  1 root root  36512 Mar 17  2014 mktemp
525402 -rwxr-xr-x  1 root root 119336 Mar 17  2014 mv
525401 -rwxr-xr-x  1 root root 123360 Jul  7  2012 netstat
525432 -rwxr-xr-x  1 root root  82096 Feb 18  2013 ps
525433 -rwxr-xr-x  1 root root  28768 Mar 17  2014 pwd
525439 -rwxr-xr-x  1 root root  57216 Mar 17  2014 rm
525405 -rwxr-xr-x  1 root root  40480 Mar 17  2014 rmdir
525389 -rwxr-xr-x  1 root root  66168 Jul  7  2012 sed
525392 lrwxrwxrwx  1 root root      4 Nov  3 10:24 sh -> bash
525442 -rwxr-xr-x  1 root root  28032 Mar 17  2014 sleep
525425 -rwxr-xr-x  1 root root 109216 Mar 17  2014 sort
525377 -rwxr-xr-x  1 root root 332120 Nov 26  2013 tar
525385 -rwxr-xr-x  1 root root  56000 Mar 17  2014 touch
525415 -rwxr-xr-x  1 root root  24480 Mar 17  2014 true
525411 -rwxr-xr-x  1 root root  28448 Mar 17  2014 uname
525386 -rwxr-xr-x  1 root root   1941 Mar 20  2014 zcat
```

See full list [here](dev-resources/info/lambda_cpu_info.txt)

### build
```bash
mkvirtualenv pylambda || workon pylambda
(pylambda) deploy/build-project && test/run-tests
```

### config
#### env vars
```bash
export aws_access_key_id_encrypted=''
export aws_secret_access_key_encrypted=''
export email=''
export email_pw_encrypted=''
export healthchecks_io_iris=''
```

#### triggers
Cloudwatch rules are in UTC (~ -7/-8hrs to PST)

##### Morningstar API is ready
3:22pm, M-F
- Cloudwatch rule -> schedule -> cron expression: `22 22 ? * MON-FRI *`

##### Tiingo API is ready
6:05pm, M-F
- Cloudwatch rule -> schedule -> cron expression: `05 1 ? * TUE-SAT *`

#### execution role
- lambda\_with\_s3

#### runtime
- 128mb
- (* 5 60) second timeout
- No VPC
