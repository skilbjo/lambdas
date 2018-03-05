# dython


## what

## binaries available

## build
```bash
deploy/build-project
```

## config
### env vars
```bash
export github_token_encrypted=''
export hchk=''
```

### triggers
- Cloudwatch (1x/day)

### execution role
- lambda_exec

### runtime
- 128mb
- 30 second timeout

### Links
- <https://hackernoon.com/how-did-i-hack-aws-lambda-to-run-docker-containers-7184dc47c09b>
- <https://github.com/vladgolubev/docker-in-aws-lambda>
- <https://github.com/indigo-dc/udocker>
