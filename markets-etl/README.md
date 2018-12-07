## m a r k e t s - e t l  l a m b d a

[![markets-etl_aws](https://healthchecks.io/badge/80da65e9-ff8f-45f1-b75e-109790/yfJXsnyi/markets-etl_aws.svg)](https://healthchecks.io/badge/80da65e9-ff8f-45f1-b75e-109790/yfJXsnyi/markets-etl_aws.svg)

### what
markets-etl, but in lambda form

### build
```bash
deploy/build-project && lein uberjar
```

### run backfill
```bash
deploy/run-backfill
```

or

```bash
deploy/build-project && lein uberjar

lein run -m jobs.aws-lambda --date "2018-01-02"
```

#### Manually Insert Data

Sometimes you'll need to manually insert data when no data present for the
beginning of the year, yet you still want to show YTD results. A reason why you
would want to do this, for example, is an IPO that happened in the middle of
the year. Manually inserting the IPO share price on the first day of the year
is a good strategy for this.

To do this, manually download the csv for the first day of the year's
partition, and manually edit the csv to include this data (or just copy paste
the respective row from the IPO date's row), and re-upload the file to the
first day of the year's partition.

### config
#### env vars
```bash
export healthchecks_io_api_key=''
export quandl_api_key=''
export tiingo_api_key=''
export alpha_vantage_api_key=''
```

Note: if you get this error:

```bash
com.amazonaws.services.kms.model.InvalidCiphertextException: null (Service:
AWSKMS; Status Code: 400; Error Code: InvalidCiphertextException; Request ID:
4fa8c287-5a02-45a5-9d83-eeb199d74fa4), compiling:(api.clj:33:31) Exception in
thread "main" com.amazonaws.services.kms.model.InvalidCiphertextException: null
(Service: AWSKMS; Status Code: 400; Error Code: InvalidCiphertextException;
Request ID: 4fa8c287-5a02-45a5-9d83-eeb199d74fa4), compiling:(api.clj:33:31)
```

Make sure the environment variables above have been set at compile time:

```bash
export AWS_ACCESS_KEY_ID=''
export AWS_SECRET_ACCESS_KEY=''
```

#### execution role
- lambda\_with\_s3

#### triggers
Cloudwatch rules are in UTC (~ -7/-8hrs to PST)

##### Morningstar API is ready
3:20pm, M-F
- Cloudwatch rules/schedule, cron expression -> `20 22 ? * MON-FRI *`

##### Tiingo API is ready
6:00pm, M-F
- Cloudwatch rule -> schedule -> cron expression: `0 1 ? * TUE-SAT *`

##### event
payload:

```json
{
  "version": "0",
  "id": "89d1a02d-5ec7-412e-82f5-13505f849b41",
  "detail-type": "Scheduled Event",
  "source": "aws.events",
  "account": "123456789012",
  "time": "2016-12-30T18:44:49Z",
  "region": "us-east-1",
  "resources": [
    "arn:aws:events:us-east-1:123456789012:rule/SampleRule"
  ],
  "detail": {}
}
```

##### setup

<img src="dev-resources/img/cron.png" alt="hi" width="900"/>

<img src="dev-resources/img/lambda.png" alt="hi" width="900"/>
