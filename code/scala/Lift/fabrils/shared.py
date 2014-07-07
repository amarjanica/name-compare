import os
from os.path import abspath

from fabric.api import *
from fabric.contrib.files import exists

from production import *

# SHARED STATIC resources
shared_base =  abspath('.') + '/'
shared_static = shared_base + '../../shared/'
shared_static_archive_name = 'shared-static.zip'
shared_static_archive = shared_base + shared_static_archive_name

def deploy_shared_static():
    #zip/put/unzip/clean shared-static
    local('cd  %s && zip -r %s . -x *.git* && cd %s' % (shared_static, shared_static_archive, shared_base))
    put(shared_static_archive, static_dir)
    local('rm ' + shared_static_archive)
    if exists(shared_static_dir):
        run('rm -r ' + shared_static_dir)
    run('unzip %s%s -d %s' % (static_dir, shared_static_archive_name, shared_static_dir))
    run('rm %s%s' % (static_dir,shared_static_archive_name))

