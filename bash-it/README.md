# bash-it lambda

## what

The AWS runtime doesn't support bash. However, bash is one of my favorite languages,
and is portable acrosss \*nix systems. Surely there is a way to break out of the
container AWS Lambda confines you to?

Nominally, uses a python runtime. But that's just an overhead wrapper before we
drop down into the good stuff.

<img src="dev-resources/img/h4x.png" alt="hi" width="900"/>

## binaries available

The AWS Lambda LXC runtime container is pretty limited. The path the code runs
is `/var/task`, there is no `~` or home user directory, and permissions around
the file system are severely restricted. From initial investigation, it semms
manipulation of the file system can only occur in `/tmp`. However, binaries can
be left in `/var/task` and that folder added to the `$PATH`, or `/tmp/bin` also
added to the `$PATH`.

Support exists for cli / binaries in python (`awscli`), nodejs (`jsonlint`), or
pre-compiled binaries (can either be included in the deployment package at build-
time (`deploy/publish-lambda:add_bin` fn) or at runtime (`src/util:_jq`).

## build
```bash
deploy/build-project
```

## config
<img src="dev-resources/img/bash_it_config.png" alt="hi" width="900"/>

### triggers
- manually

### execution role
- lambda_with_s3

### runtime
- 128mb
- 1 minute timeout

## scratch

```python
import os
import subprocess

path = '/tmp/bin'

cmdline = [os.path.join(path, 'my-script')]
subprocess.run(cmdline,
               shell=True,
               check=True,
               stderr=subprocess.STDOUT)
```

## links
- <https://alestic.com/2014/11/aws-lambda-environment/>
- File system: <http://s3.alestic.com/archive/aws-lambda-ls-laiR-20141117.txt>
