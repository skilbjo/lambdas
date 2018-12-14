## m a r k e t s - e t l  l a m b d a

[![markets-etl_aws](https://healthchecks.io/badge/80da65e9-ff8f-45f1-b75e-109790/yfJXsnyi/markets-etl_aws.svg)](https://healthchecks.io/badge/80da65e9-ff8f-45f1-b75e-109790/yfJXsnyi/markets-etl_aws.svg)

### what
markets-etl, but in lambda form

### build
```bash
run deploy/build-project
```

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

or

```bash
Caused by: com.amazonaws.services.kms.model.AWSKMSException: The ciphertext
refers to a customer master key that does not exist, does not exist in this
region, or you are not allowed to access. (Service: AWSKMS; Status Code: 400;
Error Code: AccessDeniedException; Request ID:
cc225d2e-87cb-4abb-b125-4ac9b02bc4b6)
```

Make sure the environment variables above have been set at compile time.

```bash
export aws_access_key_id="$(cat ~/.aws/credentials | grep -A 2 skilbjo-robot | grep aws_access_key_id | awk '{print $3}')"
export aws_secret_access_key="$(cat ~/.aws/credentials | grep -A 2 skilbjo-robot | grep aws_secret_access_key | awk '{print $3}')"
export AWS_ACCESS_KEY_ID=$aws_access_key_id
export AWS_SECRET_ACCESS_KEY=$aws_secret_access_key
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
