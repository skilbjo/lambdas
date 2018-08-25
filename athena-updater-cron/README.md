## athena-updater-cron 🖥️
: keep aws athena up to date

### what
AWS Athena is Hadoop + Presto under the hood. Presto, using the Hive metastore,
needs to be issued commands to repair the partition scheme.

We set up an event listener on the S3 buckets, to post a message to an SNS
topic, which this lambda is a consumer of. It will then issue an `msck repair
table [schema].[table]` command, and update the partitions.

### build
```bash
deploy/build-project && test/run-tests
```

#### env vars
```bash
export aws_access_key_id='[data-robot]'
export aws_secret_access_key='[data-robot]'
export healthchecks_io_athena_updater_cron=''
```

#### triggers
5pm, 5am, 11am every day
- Cloudwatch rules/schedule, cron expression -> `0 0,12,18 * * ? *`

#### handler
```python
entrypoint.lambda_handler
```

#### execution role
```bash
lambda_with_athena_and_s3
```

#### runtime
- 128mb
- (* 5 60) second timeout
- No VPC
