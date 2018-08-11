## athena-updater 🖥️
: keep aws athena up to date

### what
AWS Athena is Hadoop + Presto under the hood. Presto, using the Hive metastore,
needs to be issued commands to repair the partition scheme.

We set up an event listener on the S3 buckets, to post a message to an SNS
topic, which this lambda is a consumer of. It will then issue an `msck repair
table [schema].[table]` command, and update the partitions.

### build
```bash
mkvirtualenv --python=$(which python3) pylambda || workon pylambda
(pylambda) deploy/build-project && test/run-tests
```

### config
```
@Johns-MacBook-Pro:src $ cat file_to_athena_table_map.csv
file,schema,table
equities,dw,equities
currency,dw,currency
real_estate,dw,real_estate
interest_rates,dw,interest_rates
economics,dw,economics
```

#### env vars
```bash
export aws_access_key_id='[robot]'
export aws_secret_access_key='[robot]'
```

#### triggers

#### handler
```python
entrypoint.lambda_handler
```

#### execution role

#### runtime
- 128mb
- (* 5 60) second timeout
- No VPC
