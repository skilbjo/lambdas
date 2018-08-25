# ping-it lambda

See <https://github.com/skilbjo/lambdas/tree/master/bash-it>

## what
Hack the github visualization chart. Set this up with cloudwatch and AWS Lambda,
Github, and then ...

<img src="dev-resources/img/forget_about_it.png" alt="hi" width="800"/>

## binaries available

Git is also an option. Note git is version 2.4.3; and the tar (meant for linux!)
was downloaded here: <https://github.com/lambci/lambci/tree/master/vendor>

## build
```bash
deploy/build-project
```

## config
### env vars
```bash
export github_token_encrypted=''
export hchk_ping_it=''
export slack_api_key=''
```

### triggers
- Cloudwatch (1x/day)

### execution role
- lambda_exec

### runtime
- 128mb
- 30 second timeout
