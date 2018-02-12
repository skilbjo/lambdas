# bash-it lambda

## what
Hack the github visualization chart.

<img src="dev-resources/img/h4x.png" alt="hi" width="900"/>

## binaries available

Git is also an option. Note git is version 2.4.3; and the tar (meant for linux!)
was downloaded here: <https://github.com/lambci/lambci/tree/master/vendor>

## build
```bash
deploy/build-project
```

## config
<img src="dev-resources/img/bash_it_config.png" alt="hi" width="900"/>

### triggers
- Cloudwatch (1x/day)

### execution role
- lambda_exec

### runtime
- 128mb
- 30 second timeout
