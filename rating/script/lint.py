import pycodestyle
import os

checker = pycodestyle.StyleGuide(quit=True)
list_file = []
for root, dirs, files in os.walk("./app/"):
    for filename in files:
        if filename.endswith(".py"):
            list_file.append(os.path.join(root, filename))

result = checker.check_files(list_file)
if result.total_errors != 0:
    raise SyntaxError("Have some file(s) not correct with format")
