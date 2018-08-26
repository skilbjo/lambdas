## s3 logger 🖥️
: shuffle around aws s3 logs for easy access on Athena

### what
TODO: write some stuff

### build
```bash
deploy/build-project && test/run-tests
```

#### env vars
```bash
export aws_access_key_id='[data-robot]'
export aws_secret_access_key='[data-robot]'
```

#### triggers
TODO: write some stuff

#### handler
```python
entrypoint.lambda_handler
```

#### execution role

#### runtime
- 128mb
- (* 5 60) second timeout
- No VPC
