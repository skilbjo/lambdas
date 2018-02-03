from __future__ import print_function   # for code compatible w/ python 2 or 3

def main():
  import os
  import subprocess

  os.system('/var/task/my-script')

  return print('done!')

def lambda_handler(event,context):
  return main()
