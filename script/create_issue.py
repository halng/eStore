import os
import requests as req

TOKEN = os.getenv("GITHUB_TOKEN")

HEADER = {
    "Accept": "application/vnd.github+json",
    "Authorization": f"Bearer {TOKEN}",
    "X-GitHub-Api-Version": '2022-11-28',
}

URL = "https://api.github.com/repos/tanhaok/eStore/issues"

if __name__ == "__main__":
    with open("./TODO.txt", "r") as file:
        data = file.readlines()

    for _data in data:
        info = _data.split("\n")[0]
        info_arr = info.split(",")
        issue = {
            'title': info_arr[0],
            'body': info_arr[1],
            'assignee': None,
            'labels': [info_arr[2]]
        }

        res = req.post(url=URL, json=issue, headers=HEADER)

        if res.status_code == 201:
            print("Created issue")
        else:
            print(res.json())

