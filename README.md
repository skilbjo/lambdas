# lambdas

[![markets-etl_aws](https://healthchecks.io/badge/80da65e9-ff8f-45f1-b75e-109790/yfJXsnyi/markets-etl_aws.svg)](https://healthchecks.io/badge/80da65e9-ff8f-45f1-b75e-109790/yfJXsnyi/markets-etl_aws.svg)

## lambdas

### markets-etl
Get the latest markets data, write results to S3.

### aws-anarchy
Perform management operations on AWS, such as shuffle data around S3, and keeping
AWS Athena partitions up to date.

### bash-it
The AWS runtime doesn't support bash. However, bash is one of my favorite languages,
and is portable acrosss \*nix systems. Surely there is a way to break out of the
container AWS Lambda confines you to?

Nominally, uses a python runtime. But that's just an overhead wrapper before we
drop down into the good stuff.
