from __future__ import print_function   # for code compatible w/ python 2 or 3

def _init_bin(executable_name):
  import os
  import shutil

  lambda_path = os.environ.get('LAMBDA_TASK_ROOT', os.path.dirname(os.path.abspath(__file__)))
  path = '/tmp/bin'

  if not os.path.exists(path): # if warm container, /tmp/bin already created
    os.makedirs(path)          # if new LXC container, create /tmp/bin

  currfile = os.path.join(lambda_path, executable_name)
  newfile  = os.path.join(path, executable_name)

  shutil.copy2(currfile, newfile)

def main():
  import os
  import subprocess

  path = '/tmp/bin'

  # _init_bin('my-script')
  # os.system(path+'/my-script')

  os.system('/var/task/my-script')

  return print('done!')

def lambda_handler(event,context):
  return main()
