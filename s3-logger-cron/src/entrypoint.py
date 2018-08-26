from __future__ import print_function   # for code compatible w/ python 2 or 3

def to_json(d):
  import json
  return json.dumps(d)

def main(event):
  import subprocess

  event_str = to_json(event)

  subprocess.call(['/var/task/run-it'])

  return print('done!')

def lambda_handler(event,context):
  return main(event)
