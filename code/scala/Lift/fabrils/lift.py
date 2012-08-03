import os
from os.path import abspath

from fabric.api import *
from fabric.contrib.files import exists

from production import *

## IKC Lift conf
lift_base = abspath('.') + '/' 
lift_target = lift_base + 'Lift/target/scala-2.9.1/'
lift_war = lift_target + 'ip-sandbox-name-compare.war'

lift_static = lift_base + 'Lift/src/main/static/'
lift_static_dir = static_dir+'namecompare-static'
lift_static_archive_name = 'ncstatic.zip'
lift_static_archive = lift_base + lift_static_archive_name

lift_webinf_dir = lift_base + "Lift/src/main/webapp/WEB-INF/"
lift_jettyxml = lift_base + "Lift/src/main/resources/jetty/jetty-web.xml"

lift_logback_dir = lift_base + "Lift/src/main/resources/"
lift_logback = lift_base + "Lift/src/main/resources/jetty/logback.xml"

def deploy_nc_static():
    #zip/put/unzip/clean ikc static files
    local('cd  %s && zip -r %s . && cd %s' % (lift_static, lift_static_archive, lift_base))
    put(lift_static_archive, static_dir)
    local('rm ' + lift_static_archive)
    if exists(lift_static_dir):
        run('rm -r ' + lift_static_dir)
    run('unzip %s%s -d %s' % (static_dir, lift_static_archive_name, lift_static_dir))
    run('rm %s%s' % (static_dir,lift_static_archive_name))
