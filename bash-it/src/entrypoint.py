from __future__ import print_function   # for code compatible w/ python 2 or 3

def _init_bin(executable_name):
  import os
  import shutil

  LAMBDA_TASK_ROOT = os.environ.get('LAMBDA_TASK_ROOT', os.path.dirname(os.path.abspath(__file__)))
  BIN_DIR = '/tmp/bin'

  if not os.path.exists(BIN_DIR):
    os.makedirs(BIN_DIR)

  print('Copying binaries for '+executable_name+' in /tmp/bin')
  currfile = os.path.join(LAMBDA_TASK_ROOT, executable_name)
  newfile  = os.path.join(BIN_DIR, executable_name)

  shutil.copy2(currfile, newfile)
  os.system('chmod 755 '+currfile)

  os.system('cat '+currfile)
  print('Finished catting file')

def main(event,context):
  import os
  import subprocess

  BIN_DIR = '/tmp/bin'

  _init_bin('my-script')
  cmdline = [os.path.join(BIN_DIR, 'my-script')]
  subprocess.check_output(cmdline, shell=True, stderr=subprocess.STDOUT)
  subprocess.check_output(cmdline, shell=False, stderr=subprocess.STDOUT)
  os.system('/tmp/bin/my-script')
  os.system('cat /tmp/bin/my-script')
  print('done!')
  return 0

def lambda_handler(event,context):
  import json
  try:
    s3_event = json.loads(event['Records'][0]['Sns']['Message'])
  except KeyError:
    s3_event = event
  except json.decoder.JSONDecodeError:
    s3_event = event
  return main(s3_event,context)
