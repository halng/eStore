import subprocess
import os

if not os.path.isfile("./script/google-java-format-1.18.1-all-deps.jar"):
    subprocess.run(
        ["wget", "https://github.com/google/google-java-format/releases/download/v1.18.1/google-java-format-1.18.1-all-deps.jar", "-P", "./script/"])

for dir, _, files in os.walk('./'):
    for file in files:
        if file.endswith('.java'):
            file_path = os.path.join(dir, file)
            subprocess.run(
                ["java", "-jar", "./script/google-java-format-1.18.1-all-deps.jar", "--replace", file_path])
            print(file_path)
