from fabric.api import *
from fabric.operations import sudo
from fabric.contrib.files import exists
from fabric.contrib.console import confirm

import os
from os.path import abspath

import sys
import socket

from fabrils.shared import *
from fabrils.lift import lift_base
from fabrils.production import *
from fabrils.projects import PROJECTS, delete_old, add_jetty_xml, remove_jetty_xml, prepare_logback, revert_logback, package_project, upload


from fabric.colors import red

env.hosts = ['root@otto.ele-zlo.hr']

def set_ws(host):
    env.hosts = [host]

ws = { 
'kanta' : lambda: set_ws('root@localhost')
,'prod' : lambda: set_ws('root@deedee.instantor.com')
}

## check for hostname
if (socket.gethostname() == 'kanta'):
    if confirm(red("DEPLOY ON PRODUCTION?")):
        ws.get('prod')()
    else:
        ws.get('kanta')()
        print(red('Deploying on LOCALHOST instead....'))

def clean():
    local('./sbt.sh --no-jrebel "clean"')

def prepare_deploy(project):
    delete_old()
    add_jetty_xml(project) 
    prepare_logback(project)
    package_project(project)
    remove_jetty_xml(project)
    revert_logback(project)

    

def deploy(project = "", restart=False):
    ## if no project is specified deploy all of them
    if project == "":
        for p in PROJECTS:
            print(p)
            prepare_deploy(p)
            if confirm(red("Do you wish to proceed?")):
                upload(p)
         
        shared_static_dir = static_dir + 'shared'
        # Restart jetty
        if confirm(red("Do you wish to restart jetty?")):
            sudo('service jetty restart')

    else:
        ## deploy only the specified project
        print(project)
        prepare_deploy(project)
        if confirm(("Do you wish to proceed?")):
            upload(project)


            # Restart jetty
            if confirm(red("Do you wish to restart jetty?")):
                sudo('service jetty restart')

