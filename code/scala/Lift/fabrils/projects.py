from fabric.api import *
import os

from lift import *
from production import *

PROJECTS = ['lift',]


oldwar = {
'lift'      : lambda : os.path.exists(lift_war) and local('rm %s' % lift_war)
}

def delete_old():
    for p in PROJECTS:
        oldwar.get(p)()
        


## jetty-web.xml  - for setting run mode to production
jettyxmladd = {
'lift'      : lambda: local('cp %s %s' % (lift_jettyxml, lift_webinf_dir))
}

jettyxmlremove = {
'lift'      : lambda: local('rm %sjetty-web.xml' % lift_webinf_dir)
}

def add_jetty_xml(project):
    jettyxmladd.get(project)()

def remove_jetty_xml(project):
    jettyxmlremove.get(project)()

## END jetty-web.xml

## set logback
logbackset = {
'lift'      : lambda : local('cp %s %s' % (lift_logback, lift_logback_dir))
}

logbackrevert = {
'lift'      : lambda : local('git checkout -- %slogback.xml' % lift_logback_dir)
}

def prepare_logback(project):
    logbackset.get(project)()

def revert_logback(project):
    logbackrevert.get(project)()
## END logback


## package war
package = {
'lift'      : lambda : local('Lift/package-war.sh') 
}

def package_project(project):
    package.get(project)()
## END package war

## Upload
uploadwar = {  
'lift'      : lambda : put(lift_war, webapps_dir)
}

uploadstatic = {
'lift'      : lambda : deploy_nc_static()
}

def upload(project):
    uploadwar.get(project)()
    uploadstatic.get(project)()


## END upload
