select
  date_parse(s3.datetime,'%d/%b/%Y:%H:%i:%S +%f') datetime,
  *
from logs.s3
where
  s3uploaddate = date '2018-08-20'
limit 1000
