# Renames the project and puts the project name in all package folders and references =========

import os
import sys
import re
from pathlib import Path

folder = Path(os.getcwd()).parent.as_posix()

print('folder:' + folder)

oldPackageName = "template"
oldProjectTitle = "TemplateAndroid"

print('Enter new project package name:')
newProjectName = input()

newPackageName = newProjectName.lower()

# ========= Rename package files: =========

for root, dirs, files in os.walk(folder, topdown=False):
    for subDir in dirs:
        if subDir.endswith(oldPackageName):
            print("subDir" + subDir)
            os.rename(os.path.join(root, subDir), os.path.join(root, subDir.replace(oldPackageName, newPackageName)))

print('Renamed all package folders named "' + oldPackageName + '" to "' + newProjectName + '"')


# ========= Rename usages in kotlin, resource and gradle files: =========

def replace_package_name_occurences_in_file(filename):
    with open(filename, 'r') as file:
        filedata = file.read()
        print(filedata)

        filedata = re.sub(re.escape("." + oldPackageName + "."), "." + newPackageName + ".", filedata)
        filedata = re.sub(re.escape(oldProjectTitle), newProjectName.capitalize(), filedata)

        print(filedata)

        with open(filename, 'w') as file:
            file.write(filedata)
            file.close()


for root, dirs, files in os.walk(folder, topdown=False):
    for name in files:
        if name.endswith(".kt") | name.endswith(".xml") | name.endswith(".gradle"):
            file_name = os.path.join(root, name)
            replace_package_name_occurences_in_file(file_name)

print('Replaced all package references in files to: " + newProjectName + "."')
