with access_logs as (
  select
    cast(date_parse(s3.datetime,'%d/%b/%Y:%H:%i:%S +%f') as date) date,
    s3.bucket,
    s3.requestor_id,
    s3.http_status,
    s3.error_code,
    s3.user_agent,
    s3.key,
    s3.request_uri_key,
    (case
      when s3.operation in ('REST.GET.OBJECT',
                            'REST.GET.OBJECT',
                            'REST.GET.BUCKET')    then 'read'
      when s3.operation in ('REST.HEAD.BUCKET',
                            'REST.HEAD.OBJECT')   then 'head'
      when s3.operation in ('REST.DELETE.OBJECT',
                            'REST.POST.MULTI_OBJECT_DELETE') then 'delete'
      when s3.operation in ('REST.GET.ACL',
                            'REST.GET.BUCKETPOLICY',
                            'REST.GET.ENCRYPTION',
                            'REST.GET.ANALYTICS') then 'admin'
      when s3.operation in ('REST.GET.METRICS')   then 'metrics'
      when s3.operation in ('REST.GET.ACCELERATE',
                            'REST.GET.BUCKETVERSIONS',
                            'REST.GET.CORS',
                            'REST.GET.INVENTORY',
                            'REST.GET.LIFECYCLE',
                            'REST.GET.LOCATION',
                            'REST.GET.LOGGING_STATUS',
                            'REST.GET.NOTIFICATION',
                            'REST.GET.REPLICATION',
                            'REST.GET.REQUEST_PAYMENT',
                            'REST.GET.TAGGING',
                            'REST.GET.VERSIONING',
                            'REST.GET.WEBSITE')   then 'unknown'
      when s3.operation in ('REST.COPY.OBJECT',
                            'REST.PUT.OBJECT',
                            'REST.POST.UPLOAD')   then 'write'
      else s3.operation
    end) as request_type
  from logs.s3
  where
    s3.s3uploaddate between date '2018-08-20' and date '2018-08-20'
    and length(s3.datetime) > 1
)
select
  date,
  request_type,
  count(*) requests,
  bucket,
  requestor_id,
  key,
  request_uri_key,
  http_status,
  error_code,
  user_agent
from access_logs
group by
  1, 2, 4, 5, 6, 7, 8, 9, 10
order by 3 desc
