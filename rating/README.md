# Rating Service

### How to run

1. Start postgres service

```bash
docker-compose up -d
```

2. Create virtual environment and active

```bash
python -m venv .venv

# linux
source .\.venv\Scripts\activate

# win
.\.venv\Scripts\activate
```

3. Install dependencies

```bash
pip install -r requirements.txt
```

4. Init database

```bash
alembic upgrade head
```

In case want to create new migration

```bash
alembic revision -m "message/commit here"   
```

5. Run

```bash
uvicorn app.main:app --port 9094 --reload    
```

### Refer

refer [this](https://github.com/tiangolo/full-stack-fastapi-postgresql/tree/master/%7B%7Bcookiecutter.project_slug%7D%7D/backend) to setup and code convention

### Remaining work

- [ ] Handle response data
- [ ] Handle Exception
- [ ] Write unit test
- [ ] Handle check user and permission before execute logic code in crud
- [ ] Refactor code if needed
