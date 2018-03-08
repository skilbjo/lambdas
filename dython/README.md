# dython


## what

## binaries available
### Todo
curl https://raw.githubusercontent.com/grycap/scar/master/lambda/udocker >/tmp/bin/udocker


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

# patchelf
```bash
mkdir -p build/patchelf
(
cd build/patchelf
wget https://nixos.org/releases/patchelf/patchelf-0.9/patchelf-0.9.tar.bz2 #https://github.com/NixOS/patchelf/archive/0.9.zip
tar xvfj patchelf-0.9.tar.bz2
cd patchelf-0.9 && ./configure && make && sudo make install
)
```

- <https://github.com/dudash/aws-lambda-python27opencvdlib/blob/master/build.sh>
- <https://nixos.org/releases/patchelf/patchelf-0.9/>



### Links
- <https://hackernoon.com/how-did-i-hack-aws-lambda-to-run-docker-containers-7184dc47c09b>
- <https://github.com/vladgolubev/docker-in-aws-lambda>
- <https://github.com/indigo-dc/udocker>
- <https://github.com/indigo-dc/udocker/blob/master/doc/user_manual.md>
