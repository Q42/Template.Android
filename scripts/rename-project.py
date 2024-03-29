"""
This script renames the project, it's identifiers and references.
Then moves files to the correct locations.
"""

import os
import re
from pathlib import Path

folder = Path(os.path.abspath(os.path.dirname(__file__))).parent.as_posix()

oldPackageName = "template"
oldProjectName = "TemplateAndroid"

print("Enter new project name:")
newProjectName = input()

print("Enter main package name without a prefix, nl.q42.<your-package-name>:")
newPackageName = input()

# ========= Rename package files:

print(
    "\nRenaming all package folders named '%s' to '%s'.\n"
    % (oldPackageName, newProjectName)
)

for root, dirs, files in os.walk(folder, topdown=False):
    for subDir in dirs:
        if subDir.endswith(oldPackageName):
            print("Renaming folder: " + os.path.join(root, subDir))
            os.rename(
                os.path.join(root, subDir),
                os.path.join(root, subDir.replace(oldPackageName, newPackageName)),
            )

# ========= Rename usages in kotlin, resource and gradle files: =========

print(
    "\nReplacing all package references in kotlin, xml and gradle files to: '%s'.\n"
    % (newProjectName)
)


def replace_package_name_occurences_in_file(filename):
    with open(filename, "r") as file:
        filedata = file.read()

        filedata = re.sub(
            re.escape("." + oldPackageName), "." + newPackageName, filedata
        )
        filedata = re.sub(
            re.escape(oldProjectName), newProjectName.capitalize(), filedata
        )

        print("Updating file: " + filename)

        with open(filename, "w") as file:
            file.write(filedata)
            file.close()


for root, dirs, files in os.walk(folder, topdown=False):
    for name in files:
        if name.endswith(".kt") | name.endswith(".xml") | name.endswith(".gradle"):
            file_name = os.path.join(root, name)
            replace_package_name_occurences_in_file(file_name)

print(
    "\nDone renaming project to: '%s' and package to 'nl.q42.%s.\n"
    % (newProjectName, newPackageName)
)
